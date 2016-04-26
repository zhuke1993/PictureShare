package com.xm.picture_share.dto;

public class UserInfoDto {
    private Long userId;
    private String userName;
    private String createdOn;
    private int commentNum;
    private int shareNum;
    private int imagineNum;

    public UserInfoDto(Long userId, String userName, String createdOn, int commentNum, int shareNum, int imagineNum) {
        this.userId = userId;
        this.userName = userName;
        this.createdOn = createdOn;
        this.commentNum = commentNum;
        this.shareNum = shareNum;
        this.imagineNum = imagineNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public int getImagineNum() {
        return imagineNum;
    }

    public void setImagineNum(int imagineNum) {
        this.imagineNum = imagineNum;
    }
}
