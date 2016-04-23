package com.xm.picture_share.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HTTPResponseUtil {
    private String code;
    private String msg;

    public static HTTPResponseUtil _401Error = new HTTPResponseUtil("401", "登陆失效");
    public static HTTPResponseUtil _403Error = new HTTPResponseUtil("403", "未登陆");
    public static HTTPResponseUtil _ServerError = new HTTPResponseUtil("FAILED", "服务器错误");

    public HTTPResponseUtil(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HTTPResponseUtil() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void write(HttpServletResponse response) throws IOException {
        response.getWriter().print(this.toString());
        response.getWriter().flush();
    }

    public HTTPResponseUtil(Object object) {
        if (object instanceof Exception) {
            this.setCode("FAILED");
            this.setMsg(((Exception) object).getMessage());
        } else {
            this.setCode("OK");
            this.setMsg(new Gson().toJson(object));
        }
    }
}
