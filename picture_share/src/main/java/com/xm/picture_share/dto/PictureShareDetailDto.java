package com.xm.picture_share.dto;


import java.util.List;

public class PictureShareDetailDto {
    private Long id;
    private String remark;
    private Long userId;
    private String userName;
    private String createdOn;
    private List<String> fileURLList;
    private List<CommentDto> commentDtoList;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getFileURLList() {
        return fileURLList;
    }

    public void setFileURLList(List<String> fileURLList) {
        this.fileURLList = fileURLList;
    }

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
