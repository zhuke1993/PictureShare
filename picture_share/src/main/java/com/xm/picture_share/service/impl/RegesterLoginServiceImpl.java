package com.xm.picture_share.service.impl;

import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.exceptions.UsernameExistedException;
import com.xm.picture_share.service.RegisterLoginService;
import com.xm.picture_share.service.UserInfoService;
import com.xm.picture_share.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
public class RegesterLoginServiceImpl implements RegisterLoginService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;

    @Transactional
    public void register(UserInfo userInfo) {
        Assert.notNull(userInfo.getUserName());
        Assert.notNull(userInfo.getPassword());
        UserInfo userInfo1 = userInfoService.getUserInfo(userInfo.getUserName());
        if (userInfo1 != null) {
            throw new UsernameExistedException("用户名： " + userInfo.getUserName() + " 已存在，请更换用户名注册。");
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
