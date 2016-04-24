package com.xm.picture_share.dto;

public class CommentDto {
    private String createdOn;
    private String comment;
    private Long commentUserId;
    private String userName;

    public CommentDto(String createdOn, String comment, Long userId, String userName) {
        this.createdOn = createdOn;
        this.comment = comment;
        this.commentUserId = userId;
        this.userName = userName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Long commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
