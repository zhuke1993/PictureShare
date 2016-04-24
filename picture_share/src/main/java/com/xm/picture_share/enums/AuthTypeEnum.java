package com.xm.picture_share.enums;

public enum AuthTypeEnum {
    NO_AUTH("no_auth"),
    ADMIN_AUTH("admin_auth"),
    NORMAL_AUTH("normal_auth");

    AuthTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
