package com.le.share.model;

import java.util.List;

public class ArticleDetail extends Article {
    /**
     * 作者名称.
     */
    private String authorNickName;
    /**
     * 作者头像地址.
     */
    private String authorAvatarUrl;
    /**
     * 文章中涉及的部分图片地址.
     * 可用于前端首页列表中部分图片展示.
     */
    private List<ArticleImage> images;

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public List<ArticleImage> getImages() {
        return images;
    }

    public void setImages(List<ArticleImage> images) {
        this.images = images;
    }
}
