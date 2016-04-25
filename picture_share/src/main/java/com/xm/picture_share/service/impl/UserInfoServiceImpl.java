package com.xm.picture_share.service.impl;


import com.xm.picture_share.config.LoginUserContainer;
import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.exceptions.LoginTimeOutException;
import com.xm.picture_share.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ZHUKE on 2016/3/27.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

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

}
