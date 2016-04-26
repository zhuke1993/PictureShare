package com.xm.picture_share.service;

import com.xm.picture_share.dto.PictureShareDetailDto;
import com.xm.picture_share.dto.PictureShareRequest;

import java.util.List;

/**
 * 图片分享
 */
public interface PictureShareService {
    void addPictureShare(PictureShareRequest request);

    List<PictureShareDetailDto> getDetailList(int pageNo, int pageSize);

    PictureShareDetailDto getDetail(Long pictureShareId);

    void deletePictureShare(Long id);
}
