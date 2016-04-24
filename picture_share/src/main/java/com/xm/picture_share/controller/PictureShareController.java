package com.xm.picture_share.controller;

import com.xm.picture_share.config.LoginUserContainer;
import com.xm.picture_share.entity.PictureFile;
import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.enums.ResponseCodeEnum;
import com.xm.picture_share.exceptions.UsernameExistedException;
import com.xm.picture_share.service.RegisterLoginService;
import com.xm.picture_share.service.UserInfoService;
import com.xm.picture_share.util.HTTPResponseUtil;
import com.xm.picture_share.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class PictureShareController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisterLoginService registerLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {

        HTTPResponseUtil responseUtil;
        try {
            String userName = ServletRequestUtils.getStringParameter(request, "username");
            String password = ServletRequestUtils.getStringParameter(request, "password");
            boolean isLogin = registerLoginService.login(userName, password);
            HttpSession session = request.getSession();
            if (isLogin) {
                logger.info("一个新用户登陆成功，username={}", userName);
                LoginUserContainer.addLoginUser(session.getId(), userInfoService.getUserInfo(userName).getId());

            } else {
                logger.info("一个用户尝试登陆失败，username={}", userName);
                responseUtil = new HTTPResponseUtil(ResponseCodeEnum.OK.getValue(), "登陆成功");
                responseUtil.write(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = HTTPResponseUtil._ServerError;
            responseUtil.write(response);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil = new HTTPResponseUtil();
        try {
            String password = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "password"));
            String email = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "email"));
            String userName = email;
            String guid = UUID.randomUUID().toString();

            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(email);
            userInfo.setGuid(guid);
            userInfo.setPassword(MD5Util.string2MD5(password + guid));
            userInfo.setEmail(email);
            userInfo.setCreatedOn(new Date());

            registerLoginService.register(userInfo);
            logger.info("一个新用户注册成功，userName = {}", userName);

            responseUtil.setCode(ResponseCodeEnum.OK.getCode());
            responseUtil.setMsg("注册成功");

        } catch (UsernameExistedException e) {
            responseUtil.setCode(ResponseCodeEnum.FAILED.getCode());
            responseUtil.setMsg("用户名已被占用，请更换");
            e.printStackTrace();
        } catch (ServletRequestBindingException e) {
            responseUtil.setCode(ResponseCodeEnum.SERVER_ERROR.getCode());
            responseUtil.setMsg(ResponseCodeEnum.SERVER_ERROR.getValue());
            e.printStackTrace();
        }
        responseUtil.write(response);
    }

    /**
     * 新增一条图片分享消息
     */
    @RequestMapping(value = "/picture_share", method = RequestMethod.POST)
    public void uploadMessage(HttpServletRequest request, HttpServletResponse response, @RequestParam("pictures") List<MultipartFile> pictures) {
        for (int i = 0; i < pictures.size(); i++) {
            PictureFile pictureFile = new PictureFile();


        }
    }
}
