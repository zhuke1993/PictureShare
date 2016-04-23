package com.xm.picture_share.service.impl;

import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.exceptions.UsernameExistedException;
import com.xm.picture_share.service.RegisterationService;
import com.xm.picture_share.service.UserInfoService;
import com.xm.picture_share.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegisterationServiceImpl implements RegisterationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public void register(UserInfo userInfo) {
        UserInfo userInfo1 = userInfoService.getUserInfo(userInfo.getUserName());
        if (userInfo1 != null) {
            throw new UsernameExistedException("The username = " + userInfo.getUserName() + " has already existed! please change a new one.");
        }
        userInfoService.addUserInfo(userInfo);
    }

    public boolean login(String name, String password) {
        UserInfo userInfo = userInfoService.getUserInfo(name);
        if (userInfo != null) {
            //密码加盐进行md5运算
            String passwordMD5 = MD5Util.string2MD5(password + userInfo.getGuid());

            if (userInfo.getPassword().equals(passwordMD5)) {
                return true;
            }
        }
        return false;
    }

}
