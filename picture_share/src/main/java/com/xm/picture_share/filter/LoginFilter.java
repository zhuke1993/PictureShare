package com.xm.picture_share.filter;

import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.exceptions.LoginTimeOutException;
import com.xm.picture_share.exceptions.NoPermitionException;
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
        HTTPResponseUtil HTTPResponseUtil = new HTTPResponseUtil();
        try {
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            logger.info("A new http connect, client host:{}, and the request url:{}", request.getRemoteAddr(), ((HttpServletRequest) request).getRequestURL());

            response.setContentType("json");

            String url = ((HttpServletRequest) request).getRequestURL().toString();

            if (url.contains("/login") || url.contains("/register") || url.contains("/validation") || url.contains(".jpg") || url.contains(".css") || url.contains(".js")) {
                chain.doFilter(request, response);
                return;
            } else {

                String token = null;
                token = request.getParameter("accessToken");

                if (StringUtils.isEmpty(token)) {
                    HTTPResponseUtil = HTTPResponseUtil._403Error;
                    response.getWriter().print(HTTPResponseUtil.toString());
                    response.getWriter().flush();
                    return;
                } else {
                    UserInfo loginUser = loginUserService.getLoginUser(token);
                    if (loginUser != null) {
                        chain.doFilter(request, response);
                        return;
                    } else {
                        HTTPResponseUtil = HTTPResponseUtil._403Error;
                        response.getWriter().flush();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (LoginTimeOutException e) {
            e.printStackTrace();
            HTTPResponseUtil = HTTPResponseUtil._401Error;
            response.getWriter().print(HTTPResponseUtil.toString());
            response.getWriter().flush();
        } catch (NoPermitionException e) {
            e.printStackTrace();
            HTTPResponseUtil = HTTPResponseUtil._403Error;
            response.getWriter().print(HTTPResponseUtil.toString());
            response.getWriter().flush();
        }
    }

    public void destroy() {

    }

}
