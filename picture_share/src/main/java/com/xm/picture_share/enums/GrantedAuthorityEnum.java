package com.xm.picture_share.enums;

/**
 * 用户权限
 */
public enum GrantedAuthorityEnum {
    NORMAL("normal_user"),
    ADMIN("administrator");

    private String value;

    GrantedAuthorityEnum(String value) {
        this.value = value;
    }
}
