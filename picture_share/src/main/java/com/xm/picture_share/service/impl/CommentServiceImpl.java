package com.xm.picture_share.service.impl;

import com.xm.picture_share.entity.Comment;
import com.xm.picture_share.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void addComment(Comment comment) {
        hibernateTemplate.save(comment);
    }
}
