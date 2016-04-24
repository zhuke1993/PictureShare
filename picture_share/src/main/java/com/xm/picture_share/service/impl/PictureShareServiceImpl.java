package com.xm.picture_share.service.impl;

import com.xm.picture_share.dto.PictureShareRequest;
import com.xm.picture_share.service.PictureShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureShareServiceImpl implements PictureShareService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void addPictureShare(PictureShareRequest request) {

        for (int i = 0; i < request.getPictureFileList().size(); i++) {
            hibernateTemplate.save(request.getPictureFileList().get(i));
        }
        hibernateTemplate.save(request.getPictureShare());
    }
}
