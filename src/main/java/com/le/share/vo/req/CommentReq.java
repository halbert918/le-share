package com.le.share.vo.req;

import javax.validation.constraints.NotEmpty;

public class CommentReq {
    @NotEmpty(message = "待评论文章ID不能为空")
    private Long artId;

    @NotEmpty(message = "评论内容")
    private String content;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
