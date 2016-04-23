package com.xm.picture_share.service;

import com.xm.picture_share.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZHUKE on 2016/3/27.
 */
public interface UserInfoService {


    UserInfo getUserInfo(String userName, String password);

    UserInfo getUserInfo(String userName);

    void addUserInfo(UserInfo userInfo);

    void modifyUserInfo(UserInfo userInfo);

    void delUserInfo(UserInfo userInfo);

    boolean isContainUserInfo(String userName);

    UserInfo getLoginUser(HttpServletRequest request);

    UserInfo getLoginUser(String token);

    void auditUser(String userName);
}
