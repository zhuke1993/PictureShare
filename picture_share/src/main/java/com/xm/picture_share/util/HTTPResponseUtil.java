package com.xm.picture_share.util;

import com.google.gson.Gson;
import com.xm.picture_share.enums.ResponseCodeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HTTPResponseUtil {
    private String code;
    private String msg;

    public static HTTPResponseUtil _401Error = new HTTPResponseUtil(ResponseCodeEnum.UNAUTHORIZED.getCode(), ResponseCodeEnum.UNAUTHORIZED.getValue());
    public static HTTPResponseUtil _403Error = new HTTPResponseUtil(ResponseCodeEnum.FORBIDDEN.getCode(), ResponseCodeEnum.FORBIDDEN.getValue());
    public static HTTPResponseUtil _ServerError = new HTTPResponseUtil(ResponseCodeEnum.SERVER_ERROR.getCode(), ResponseCodeEnum.SERVER_ERROR.getValue());

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
            this.setCode(ResponseCodeEnum.SERVER_ERROR.getCode());
            this.setMsg(ResponseCodeEnum.SERVER_ERROR.getValue());
        } else {
            this.setCode(ResponseCodeEnum.OK.getCode());
            this.setMsg(new Gson().toJson(object));
        }
    }
}
