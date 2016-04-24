package com.xm.picture_share.filter;

import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.service.UserInfoService;
import com.xm.picture_share.util.HTTPResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private UserInfoService loginUserService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HTTPResponseUtil responseUtil = new HTTPResponseUtil();
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("json");

        logger.info("A new http connect, client host:{}, and the request url:{}", request.getRemoteAddr(), ((HttpServletRequest) request).getRequestURL());

        String url = ((HttpServletRequest) request).getRequestURL().toString();
        if (url.contains("/login") || url.contains("/register") || url.contains("/validation") || url.contains(".jpg") || url.contains(".css") || url.contains(".js")) {
            chain.doFilter(request, response);
            return;
        } else {
            String token = request.getParameter("accessToken");
            if (StringUtils.isEmpty(token)) {
                responseUtil = responseUtil._403Error;
                responseUtil.write((HttpServletResponse) response);
                return;
            } else {
                UserInfo loginUser = loginUserService.getLoginUser((HttpServletRequest) request);
                if (loginUser != null) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    responseUtil = responseUtil._401Error;
                    responseUtil.write((HttpServletResponse) response);
                    return;
                }
            }
        }
    }

    public void destroy() {

    }

}
