package com.le.share.service;

import com.github.pagehelper.Page;
import com.le.share.model.Article;
import com.le.share.model.ArticleDetail;
import com.le.share.model.UserInfo;
import com.le.share.security.domain.User;
import com.le.share.vo.resp.ArticleVo;

import java.util.List;

/**
 * Created by yinbohe.
 * Date 2020/5/3
 * Description
 */
public interface ArticleService {
    /**
     * 获取滑动图片的文章
     * @param offset
     * @param limit
     * @return
     */
    List<ArticleDetail> getSwiperArticles(int offset, int limit);
    /**
     * 新增文章.
     * @param article
     * @return
     */
    Long addArticle(Article article, List<String> images);

    /**
     * 更新文章.
     * @param article
     */
    void updateArticle(Article article, List<String> images);

    /**
     * 删除文章.
     * @param id
     */
    void deleteArticle(Long userId, Long id);

    /**
     * 获取文章列表.
     * @param type
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    List<ArticleDetail> getArticles(Integer type, Long prevId, String keyword, int offset, int limit);

    /**
     * 获取我的文章
     * @param id
     * @return
     */
    ArticleDetail getMyArticle(Long id);
    /**
     * 获取文章.
     * @param id
     * @param user
     * @return
     */
    ArticleVo getArticle(Long id, User user);

    /**
     * 点赞.
     * @param id
     * @param likeUserId
     */
    void likeArticle(Long id, Long likeUserId);

    /**
     * 取消点赞.
     * @param id
     * @param unLikeUserId
     */
    void unLikeArticle(Long id, Long unLikeUserId);

    /**
     * 获取当前文章的点赞用户.
     * @param id
     * @param offset
     * @param limit
     * @return
     */
    List<UserInfo> getLikeUsers(Long id, int offset, int limit);

    /**
     * 获取用户发布的文章.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<ArticleDetail> getUserArticles(Long userId, int offset, int limit);

    /**
     * 获取点赞列表.
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<ArticleDetail> getLikes(Long userId, int offset, int limit);

}
