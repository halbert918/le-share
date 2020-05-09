package com.le.share.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.le.share.common.enums.LeResultEnum;
import com.le.share.common.enums.LikeType;
import com.le.share.common.enums.ShareType;
import com.le.share.exception.LeShareException;
import com.le.share.mapper.ArticleMapper;
import com.le.share.mapper.LikeUserMapper;
import com.le.share.mapper.UserInfoMapper;
import com.le.share.mapper.ext.ArticleImageMapper;
import com.le.share.mapper.ext.ExtArticleMapper;
import com.le.share.mapper.ext.ExtFollowMapper;
import com.le.share.mapper.ext.ExtLikeUserMapper;
import com.le.share.model.*;
import com.le.share.security.domain.User;
import com.le.share.service.ArticleService;
import com.le.share.util.IdWorker;
import com.le.share.vo.resp.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yinbohe.
 * Date 2020/5/4
 * Description
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ExtArticleMapper extArticleMapper;

    @Autowired
    private ArticleImageMapper articleImageMapper;

    @Autowired
    private LikeUserMapper likeUserMapper;

    @Autowired
    private ExtLikeUserMapper extLikeUserMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ExtFollowMapper extFollowMapper;

    @Override
    public List<ArticleDetail> getSwiperArticles(int offset, int limit) {
        Article article = new Article();
        article.setStatus(0);
        article.setType(0);      //获取文章类型
        article.setIsSwiper(1);  // 只获取滑动图对应的文章
        List<ArticleDetail> articles = extArticleMapper.selectListDynamic(article, offset, limit);
        // 填充images.
        fillImages(articles);
        return articles;
    }

    @Transactional
    @Override
    public Long addArticle(Article article, List<String> images) {
        article.setId(IdWorker.genNextId());
        article.setCreateTime(new Date());
        articleMapper.insertSelective(article);

        // 保存文章中的涉及的图片，最多保存9张用于首页列表展示
        if (!CollectionUtils.isEmpty(images)) {
            List<ArticleImage> articleImages = images.stream().map(url -> {
                ArticleImage articleImage = new ArticleImage();
                articleImage.setArtId(article.getId());
                articleImage.setImageUrl(url);
                return articleImage;
            }).collect(Collectors.toList());
            articleImageMapper.multiInsert(articleImages);
        }
        return article.getId();
    }

    @Transactional
    @Override
    public void updateArticle(Article article, List<String> images) {
        article.setUpdateTime(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
        // 删除原图片
        articleImageMapper.deleteByArtcleId(article.getId());
        // 保存文章中的涉及的图片，最多保存9张用于首页列表展示
        if (!CollectionUtils.isEmpty(images)) {
            List<ArticleImage> articleImages = images.stream().map(url -> {
                ArticleImage articleImage = new ArticleImage();
                articleImage.setArtId(article.getId());
                articleImage.setImageUrl(url);
                return articleImage;
            }).collect(Collectors.toList());
            articleImageMapper.multiInsert(articleImages);
        }

    }

    @Override
    public void deleteArticle(Long userId, Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return;
        }
        article.setStatus(1);
        article.setUpdator(String.valueOf(userId));
        articleMapper.updateByPrimaryKeySelective(article);
        // 删除图片
        articleImageMapper.deleteByArtcleId(article.getId());
    }

    @Override
    public List<ArticleDetail> getArticles(Integer type, Long prevId, String keyword, int offset, int limit) {
        List<ArticleDetail> articles = extArticleMapper.selectList(type,  prevId,0, keyword, limit);
        // 获取文章对应的图片
        fillImages(articles);
        return articles;
    }

    @Override
    public ArticleDetail getMyArticle(Long id) {
        Optional<ArticleDetail> optional = extArticleMapper.selectById(id);
        optional.ifPresent(article -> {
            if (article.getType() != ShareType.ARTICLE.getType()) {
                List<Long> ids = Lists.newArrayListWithCapacity(1);
                ids.add(article.getId());
                List<ArticleImage> images = articleImageMapper.selectByArtIds(ids);
                article.setImages(images);
            }
        });
        return optional.get();
    }

    @Override
    public ArticleVo getArticle(Long id, User user) {
        Optional<ArticleDetail> optional = extArticleMapper.selectById(id);
        ArticleDetail detail = optional.orElseThrow(
                () -> new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT, "文章信息不存在.")
        );
        if (detail.getStatus() == 1) {
            throw new LeShareException(LeResultEnum.REQUEST_DATA_NOT_EXSIT, "文章已被删除.");
        }

        // 非文章类型需单独查询涉及的图片地址（文章类为富文本，前端自动解析，其他类型context为文本，图片单独存储的）
        if (detail.getType() != ShareType.ARTICLE.getType()) {
            List<Long> ids = Lists.newArrayListWithCapacity(1);
            ids.add(detail.getId());
            List<ArticleImage> images = articleImageMapper.selectByArtIds(ids);
            detail.setImages(images);
        }

        // 浏览数+1
        extArticleMapper.incrViewCount(id);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setArticle(detail);

        if (user != null && user.getUserId() != null) {
            // 判断当前用户是否点赞
            Optional<LikeUser> likeUser = extLikeUserMapper.selectOne(id, user.getUserId(), 0);
            articleVo.setIsLike(likeUser.isPresent());
            // 判断当前登陆用户是否已经关注过作者
            Optional<FollowDetail> followDetail = extFollowMapper.selectOne(detail.getAuthorUserId(), user.getUserId());
            articleVo.setFollowAuthor(followDetail.isPresent());
        }
        return articleVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeArticle(Long id, Long likeUserId) {
        // 判断是否点赞过
        Optional<LikeUser> optional = extLikeUserMapper.selectOne(id, likeUserId, 0);
        optional.ifPresent(e -> {
            throw new LeShareException(LeResultEnum.ARTICLE_LIKE_HAD_POST);
        });
        // 新增点赞记录
        LikeUser likeUser = new LikeUser();
        likeUser.setId(IdWorker.genNextId());
        likeUser.setTargetId(id);
        likeUser.setLikeType(LikeType.ARTICLE.getType());
        likeUser.setLikeUserId(likeUserId);
        likeUserMapper.insert(likeUser);
        // 文章点赞数+1
        extArticleMapper.incrLikeCount(id, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unLikeArticle(Long id, Long unLikeUserId) throws IllegalArgumentException {
        // 判断是否点赞过
        Optional<LikeUser> optional = extLikeUserMapper.selectOne(id, unLikeUserId, 0);
        LikeUser likeUser = optional.orElseThrow(() -> new LeShareException(LeResultEnum.ARTICLE_LIKE_HAD_POST));
        // 取消点赞
        likeUser.setStatus(1);
        likeUserMapper.updateByPrimaryKey(likeUser);
        // 文章点赞数-1
        extArticleMapper.incrLikeCount(id, -1);
    }

    @Override
    public List<UserInfo> getLikeUsers(Long id, int offset, int limit) {
        Page<UserInfo> page = PageHelper.offsetPage(offset, limit, false)
                .doSelectPage(() -> extLikeUserMapper.getLikeUsers(id));
        return page.getResult();
    }

    @Override
    public List<ArticleDetail> getUserArticles(Long userId, int offset, int limit) {
        Article article = new Article();
        article.setAuthorUserId(userId);
        article.setStatus(0);
        article.setType(-1);
        return extArticleMapper.selectListDynamic(article, offset, limit);
    }

    @Override
    public List<ArticleDetail> getLikes(Long userId, int offset, int limit) {
        List<Long> artIds = extLikeUserMapper.getTargetIds(userId, LikeType.ARTICLE.getType(), offset, limit);
        if (CollectionUtils.isEmpty(artIds)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return extArticleMapper.selectListByArtIds(artIds);
    }

    /**
     * 填充images.
     * @param articles
     */
    private void fillImages(List<ArticleDetail> articles) {
        if (CollectionUtils.isEmpty(articles)) {
            return;
        }
        List<Long> artIds = articles.stream()
                .map(e -> e.getId())
                .collect(Collectors.toList());
        // 获取所有文章对应的图片信息
        List<ArticleImage> images = articleImageMapper.selectByArtIds(artIds);
        if (!CollectionUtils.isEmpty(images)) { // 文章中存在图像，首页列表显示部分图片
            // 按照文章ID分组
            Map<Long, List<ArticleImage>> groupImages = images.stream()
                    .collect(Collectors.groupingBy(ArticleImage::getArtId));
            //重新填充images
            articles.stream()
                    .forEach(articleDetail -> {
                        articleDetail.setImages(groupImages.get(articleDetail.getId()));
                    });
        }
    }
}
