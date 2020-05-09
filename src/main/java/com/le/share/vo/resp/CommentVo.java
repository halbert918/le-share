package com.le.share.vo.resp;

import com.le.share.model.ArticleDetail;
import com.le.share.model.CommentDetail;

public class CommentVo {
    /**
     * 评论.
     */
    private CommentDetail comment;
    /**
     * 评论对应的文章.
     */
    private ArticleDetail article;

    public CommentVo(CommentDetail comment, ArticleDetail article) {
        this.comment = comment;
        this.article = article;
    }

    public CommentDetail getComment() {
        return comment;
    }

    public void setComment(CommentDetail comment) {
        this.comment = comment;
    }

    public ArticleDetail getArticle() {
        return article;
    }

    public void setArticle(ArticleDetail article) {
        this.article = article;
    }
}
