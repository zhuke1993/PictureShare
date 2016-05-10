package com.xm.picture_share.service.impl;


import com.xm.picture_share.config.LoginUserContainer;
import com.xm.picture_share.dto.CommentDto;
import com.xm.picture_share.dto.PictureShareDetailDto;
import com.xm.picture_share.dto.UserInfoDto;
import com.xm.picture_share.entity.Comment;
import com.xm.picture_share.entity.PictureFile;
import com.xm.picture_share.entity.PictureShare;
import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.exceptions.LoginTimeOutException;
import com.xm.picture_share.service.PictureShareService;
import com.xm.picture_share.service.UserInfoService;
import com.xm.picture_share.util.DateFormatUtil;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private PictureShareService pictureShareService;

    public UserInfo getUserInfo(String userName, String password) {
        List<UserInfo> userInfo = (List<UserInfo>) hibernateTemplate.find("from UserInfo u where u.userName = ? and u.password = ? ", userName, password);
        if (userInfo.size() > 0) {
            return (UserInfo) userInfo.get(0);
        }
        return null;
    }

    public UserInfo getUserInfo(String userName) {
        List<UserInfo> userInfo = (List<UserInfo>) hibernateTemplate.find("from UserInfo u where u.email = ?", userName);
        if (userInfo.size() > 0) {
            return (UserInfo) userInfo.get(0);
        }
        return null;
    }

    @Transactional
    public void addUserInfo(UserInfo userInfo) {
        hibernateTemplate.save(userInfo);
    }

    @Transactional
    public void modifyUserInfo(UserInfo userInfo) {
        hibernateTemplate.update(userInfo);
    }

    @Transactional
    public void delUserInfo(UserInfo userInfo) {
        hibernateTemplate.delete(userInfo);
    }

    public boolean isContainUserInfo(String userName) {
        return false;
    }

    public UserInfo getLoginUser(HttpServletRequest request) {
        String token = request.getParameter("accessToken");
        Long loginUserId = LoginUserContainer.getLoginUser(token);
        if (loginUserId == null) {
            return null;
        } else {
            return hibernateTemplate.get(UserInfo.class, loginUserId);
        }
    }

    public List<UserInfoDto> getUserinfoDtoList(int pageNo, int pageSize) {
        List<UserInfoDto> list = new ArrayList<UserInfoDto>();
        String hqlString = "from UserInfo u where u.grantedAuthority = 'normal_user'";
        Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hqlString);
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<UserInfo> userInfoList = query.list();
        for (int i = 0; i < userInfoList.size(); i++) {
            list.add(getUserInfoDto(userInfoList.get(i).getId()));
        }
        return list;
    }

    public UserInfoDto getUserInfoDto(Long userId) {
        Assert.notNull(userId);
        int commentNum = hibernateTemplate.find("from Comment  c where c.commentUserId = ?", userId).size();
        int shareNum = hibernateTemplate.find("from PictureShare  p where p.userId= ?", userId).size();
        int imagineNum = hibernateTemplate.find("from PictureFile  p where p.userId= ?", userId).size();

        UserInfo userInfo = hibernateTemplate.load(UserInfo.class, userId);

        return new UserInfoDto(userId, userInfo.getUserName(), DateFormatUtil.formatDate(userInfo.getCreatedOn()), commentNum, shareNum, imagineNum);
    }

    @Transactional
    public void deleteUser(Long userId) {
        hibernateTemplate.delete(hibernateTemplate.get(UserInfo.class, userId));
        List<PictureShare> list = (List<PictureShare>) hibernateTemplate.find("from PictureShare p where userId = ?", userId);
        for (int i = 0; i < list.size(); i++) {
            pictureShareService.deletePictureShare(list.get(i).getId());
        }
    }


    public List<UserInfo> findUser(String userName) {
        return (List<UserInfo>) hibernateTemplate.find("from UserInfo u where u.userName like '%" + userName + "%'");
    }

}
