package com.xm.picture_share.service;

import com.xm.picture_share.dto.UserInfoDto;
import com.xm.picture_share.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    List<UserInfoDto> getUserinfoDtoList(int pageNo, int pageSize);

    UserInfoDto getUserInfoDto(Long userId);

    void deleteUser(Long userId);

}
