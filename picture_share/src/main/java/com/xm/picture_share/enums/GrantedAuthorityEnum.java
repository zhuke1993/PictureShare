package com.xm.picture_share.enums;

/**
 * 用户权限
 */
public enum GrantedAuthorityEnum {
    NORMAL("normal_user"),
    ADMIN("administrator");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    GrantedAuthorityEnum(String value) {
        this.value = value;
    }
}
