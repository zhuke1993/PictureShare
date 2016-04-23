package com.xm.picture_share.service.impl;

import com.xm.picture_share.entity.FileURL;
import com.xm.picture_share.service.FileUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileUrlServiceImpl implements FileUrlService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void saveFileUrl(FileURL fileURL) {
        hibernateTemplate.save(fileURL);
    }
}
