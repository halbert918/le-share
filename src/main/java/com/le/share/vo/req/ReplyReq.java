package com.le.share.vo.req;

/**
 * 回复评论/回复.
 */
public class ReplyReq {

    private Long artId;

    private Long commentId;
    /**
     * 回复类型 0 回复评论 1 回复某条回复.
     */
    private int replyType;

    private Long replyId;

    private String content;
    /**
     * 回复某条回复（某人）.
     */
    private Long toUserId;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public int getReplyType() {
        return replyType;
    }

    public void setReplyType(int replyType) {
        this.replyType = replyType;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
