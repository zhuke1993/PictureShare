package com.xm.picture_share.service;

import com.xm.picture_share.entity.UserInfo;

public interface RegisterLoginService {
    void register(UserInfo userInfo);

    boolean login(String name, String password);
}
