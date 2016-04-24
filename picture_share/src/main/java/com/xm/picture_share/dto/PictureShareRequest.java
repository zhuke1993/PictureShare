package com.xm.picture_share.dto;

import com.xm.picture_share.entity.PictureFile;
import com.xm.picture_share.entity.PictureShare;

import java.util.List;

/**
 * 新增一条图片分享应用消息的请求
 */
public class PictureShareRequest {

    private PictureShare pictureShare;
    private List<PictureFile> pictureFileList;

    public PictureShare getPictureShare() {
        return pictureShare;
    }

    public void setPictureShare(PictureShare pictureShare) {
        this.pictureShare = pictureShare;
    }

    public List<PictureFile> getPictureFileList() {
        return pictureFileList;
    }

    public void setPictureFileList(List<PictureFile> pictureFileList) {
        this.pictureFileList = pictureFileList;
    }
}
