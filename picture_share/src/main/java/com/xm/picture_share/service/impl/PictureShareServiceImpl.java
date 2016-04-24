package com.xm.picture_share.service.impl;

import com.xm.picture_share.dto.CommentDto;
import com.xm.picture_share.dto.PictureShareDetailDto;
import com.xm.picture_share.dto.PictureShareRequest;
import com.xm.picture_share.entity.Comment;
import com.xm.picture_share.entity.PictureFile;
import com.xm.picture_share.entity.PictureShare;
import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.service.PictureShareService;
import com.xm.picture_share.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureShareServiceImpl implements PictureShareService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void addPictureShare(PictureShareRequest request) {

        hibernateTemplate.save(request.getPictureShare());

        for (int i = 0; i < request.getPictureFileList().size(); i++) {
            request.getPictureFileList().get(i).setPictureShareId(request.getPictureShare().getId());
            hibernateTemplate.save(request.getPictureFileList().get(i));
        }
    }

    public List<PictureShareDetailDto> getDetailList(int pageNo, int pageSize) {
        List<PictureShareDetailDto> list = new ArrayList<PictureShareDetailDto>();

        List<PictureShare> pictureShareList = (List<PictureShare>) hibernateTemplate.find("from PictureShare p limit ?, ?", (pageNo - 1) * pageSize, pageNo * pageSize - 1);

        for (int i = 0; i < pictureShareList.size(); i++) {
            PictureShareDetailDto pictureShareDetailDto = new PictureShareDetailDto();
            List<String> fileURLList = new ArrayList<String>();
            List<CommentDto> commentDtoList = new ArrayList<CommentDto>();

            PictureShare pictureShare = pictureShareList.get(i);
            pictureShareDetailDto.setId(pictureShare.getId());
            pictureShareDetailDto.setRemark(pictureShare.getRemark());

            List<PictureFile> pictureFileList = (List<PictureFile>) hibernateTemplate.find("from PictureFile f where f.pictureShareId = ?", pictureShare.getId());
            for (int j = 0; j < pictureFileList.size(); j++) {
                fileURLList.add(pictureFileList.get(j).getFileURL());
            }
            pictureShareDetailDto.setFileURLList(fileURLList);

            List<Comment> commentList = (List<Comment>) hibernateTemplate.find("from Comment c where c.pictureShareId = ?", pictureShare.getId());
            for (int k = 0; k < commentList.size(); k++) {
                Comment comment = commentList.get(k);
                CommentDto commentDto = new CommentDto(DateFormatUtil.formatDate(comment.getCreatedOn()), comment.getComment(), comment.getCommentUserId(), hibernateTemplate.get(UserInfo.class, comment.getCommentUserId()).getUserName());
                commentDtoList.add(commentDto);
            }
            pictureShareDetailDto.setCommentDtoList(commentDtoList);

            list.add(pictureShareDetailDto);
        }

        return list;
    }

    public PictureShareDetailDto getDetail(Long pictureShareId) {

        PictureShare pictureShare = hibernateTemplate.get(PictureShare.class, pictureShareId);

        PictureShareDetailDto pictureShareDetailDto = new PictureShareDetailDto();
        List<String> fileURLList = new ArrayList<String>();
        List<CommentDto> commentDtoList = new ArrayList<CommentDto>();

        pictureShareDetailDto.setId(pictureShare.getId());
        pictureShareDetailDto.setRemark(pictureShare.getRemark());

        List<PictureFile> pictureFileList = (List<PictureFile>) hibernateTemplate.find("from PictureFile f where f.pictureShareId = ?", pictureShare.getId());
        for (int j = 0; j < pictureFileList.size(); j++) {
            fileURLList.add(pictureFileList.get(j).getFileURL());
        }
        pictureShareDetailDto.setFileURLList(fileURLList);

        List<Comment> commentList = (List<Comment>) hibernateTemplate.find("from Comment c where c.pictureShareId = ?", pictureShare.getId());
        for (int k = 0; k < commentList.size(); k++) {
            Comment comment = commentList.get(k);
            CommentDto commentDto = new CommentDto(DateFormatUtil.formatDate(comment.getCreatedOn()), comment.getComment(), comment.getCommentUserId(), hibernateTemplate.get(UserInfo.class, comment.getCommentUserId()).getUserName());
            commentDtoList.add(commentDto);
        }
        pictureShareDetailDto.setCommentDtoList(commentDtoList);


        return pictureShareDetailDto;
    }
}
