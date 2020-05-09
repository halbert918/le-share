package com.le.share.vo.req;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

public class ArticleReq implements Serializable {

    private String title;

    private int type;

    @NotEmpty(message = "文章内容不能为空")
    private String content;
    /**
     * 文章中涉及的图片地址，用于首页列表展示.
     */
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ArticleReq{" +
                "title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}