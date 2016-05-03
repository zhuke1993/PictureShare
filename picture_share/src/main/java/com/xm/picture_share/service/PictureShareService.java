package com.xm.picture_share.service;

import com.xm.picture_share.dto.PictureShareDetailDto;
import com.xm.picture_share.dto.PictureShareRequest;
import com.xm.picture_share.entity.PictureShare;

import java.util.List;

/**
 * 图片分享
 */
public interface PictureShareService {
    void addPictureShare(PictureShareRequest request);

    List<PictureShareDetailDto> getDetailList(int pageNo, int pageSize);

    List<PictureShareDetailDto> getDetailList(Long useId);

    PictureShareDetailDto getDetail(Long pictureShareId);

    void deletePictureShare(Long id);

    List<PictureShareDetailDto> findPictureShare(String str);
}
