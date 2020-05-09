package com.le.share.vo.resp;

import com.le.share.model.ArticleDetail;

public class ArticleVo {
    /**
     * 文章信息.
     */
    private ArticleDetail article;
    /**
     * 是否关注作者.
     */
    private boolean isFollowAuthor;
    /**
     * 是否点赞.
     */
    private boolean isLike;

    public ArticleVo() {}

    public ArticleDetail getArticle() {
        return article;
    }

    public void setArticle(ArticleDetail article) {
        this.article = article;
    }

    public boolean isFollowAuthor() {
        return isFollowAuthor;
    }

    public void setFollowAuthor(boolean followAuthor) {
        isFollowAuthor = followAuthor;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }
}
