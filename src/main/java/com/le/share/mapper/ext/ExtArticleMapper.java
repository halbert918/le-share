package com.le.share.mapper.ext;

import com.le.share.model.Article;
import com.le.share.model.ArticleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ExtArticleMapper {
    /**
     * 获取文章列表.
     * @param type      文章类型
     * @param status    状态
     * @param prevId    前一个文章ID，文章ID由雪花算法生成，总体递增
     * @param keyword   搜索关键字
     * @return
     */
    List<ArticleDetail> selectList(@Param("type") Integer type,
                                   @Param("prevId") Long prevId, @Param("status") int status,
                                   @Param("keyword") String keyword, @Param("limit") int limit);

    /**
     * 获取文章列表.
     * @param artIds
     * @return
     */
    List<ArticleDetail> selectListByArtIds(@Param("artIds") List<Long> artIds);
    /**
     * 获取文章详情，包括作者基础信息.
     * @param id
     * @return
     */
    Optional<ArticleDetail> selectById(Long id);
    /**
     * 点赞数.
     * @param id
     * @param incr
     * @return
     */
    int incrLikeCount(@Param("id") long id, @Param("incr") int incr);

    /**
     * 评论数.
     * @param id
     * @param incr
     * @return
     */
    int incrCommentCount(@Param("id") long id, @Param("incr") int incr);

    /**
     * 页面浏览数.
     * @param id
     * @return
     */
    int incrViewCount(@Param("id") long id);

    /**
     * .
     * @param article
     * @param offset
     * @param limit
     * @return
     */
    List<ArticleDetail> selectListDynamic(@Param("article") Article article, @Param("offset") int offset, @Param("limit") int limit);

}