package com.xm.picture_share.config;

import java.util.List;

/**
 * 系统设置值.
 */
public class SystemConfig {

    private static String UPLOAD_FILE_PATH;

    private static List<String> NO_AUTH_URL;

    private static List<String> ADMIN_URL;

    public static List<String> getAdminUrl() {
        return ADMIN_URL;
    }

    public static void setAdminUrl(List<String> adminUrl) {
        ADMIN_URL = adminUrl;
    }

    public static String getUploadFilePath() {
        return UPLOAD_FILE_PATH;
    }

    public static void setUploadFilePath(String uploadFilePath) {
        UPLOAD_FILE_PATH = uploadFilePath;
    }

    public static List<String> getNoAuthUrl() {
        return NO_AUTH_URL;
    }

    public static void setNoAuthUrl(List<String> noAuthUrl) {
        NO_AUTH_URL = noAuthUrl;
    }
}
