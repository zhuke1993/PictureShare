package com.xm.picture_share.dto;

/**
 * 系统统计报表
 */
public class SystemReportDto {
    private int imageNum;
    private int commentNum;
    private int userNum;

    public SystemReportDto(int imageNum, int commentNum, int userNum) {
        this.imageNum = imageNum;
        this.commentNum = commentNum;
        this.userNum = userNum;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }
}
