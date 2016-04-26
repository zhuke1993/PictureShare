package com.xm.picture_share.controller;

import com.xm.picture_share.config.LoginUserContainer;
import com.xm.picture_share.config.SystemConfig;
import com.xm.picture_share.dto.PictureShareDetailDto;
import com.xm.picture_share.dto.PictureShareRequest;
import com.xm.picture_share.dto.SystemReportDto;
import com.xm.picture_share.dto.UserInfoDto;
import com.xm.picture_share.entity.Comment;
import com.xm.picture_share.entity.PictureFile;
import com.xm.picture_share.entity.PictureShare;
import com.xm.picture_share.entity.UserInfo;
import com.xm.picture_share.enums.GrantedAuthorityEnum;
import com.xm.picture_share.enums.ResponseCodeEnum;
import com.xm.picture_share.exceptions.UsernameExistedException;
import com.xm.picture_share.service.*;
import com.xm.picture_share.util.HTTPResponseUtil;
import com.xm.picture_share.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

    @Autowired
    private PictureShareService pictureShareService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

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
                responseUtil = new HTTPResponseUtil(ResponseCodeEnum.OK.getCode(), session.getId());
            } else {
                logger.info("一个用户尝试登陆失败，username={}", userName);
                responseUtil = new HTTPResponseUtil(ResponseCodeEnum.FAILED.getCode(), "登陆失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil = HTTPResponseUtil._ServerError;
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil = new HTTPResponseUtil();
        try {
            String password = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "password"));
            String email = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "email"));
            String userName = email;
            String guid = UUID.randomUUID().toString();
            String grantedAuthority = ServletRequestUtils.getStringParameter(request, "grantedAuthority");

            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(email);
            userInfo.setGuid(guid);
            userInfo.setPassword(MD5Util.string2MD5(password + guid));
            userInfo.setEmail(email);
            userInfo.setCreatedOn(new Date());
            userInfo.setGrantedAuthority(grantedAuthority);

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
        } catch (IllegalArgumentException e) {
            responseUtil.setCode(ResponseCodeEnum.SERVER_ERROR.getCode());
            responseUtil.setMsg("参数错误。");
        }
        responseUtil.write(response);
    }

    /**
     * 新增一条图片分享消息
     */
    @RequestMapping(value = "/picture_share", method = RequestMethod.POST)
    public String uploadMessage(HttpServletRequest request, HttpServletResponse response, @RequestParam("pictures") MultipartFile[] pictures) throws IOException {
        HTTPResponseUtil responseUtil;
        response.setCharacterEncoding("UTF-8");
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);

            List<PictureFile> pictureFileList = new ArrayList<PictureFile>();

            for (int i = 0; i < pictures.length; i++) {
                if (isImagineFileType(pictures[i])) {
                    PictureFile pictureFile = new PictureFile();
                    String pictureName = pictureFileNameFactory(loginUser.getId());
                    pictures[i].transferTo(new File(SystemConfig.getUploadFilePath() + "/" + pictureName));
                    pictureFile.setFileName(pictureName);
                    pictureFile.setFileSize(pictures[i].getSize());
                    pictureFile.setFileType(pictures[i].getContentType());
                    pictureFile.setFileURL("/" + pictureFile.getFileName());
                    pictureFile.setUserId(loginUser.getId());
                    pictureFileList.add(pictureFile);
                }
            }

            PictureShare pictureShare = new PictureShare();
            pictureShare.setCreatedOn(new Date());
            pictureShare.setRemark(request.getParameter("remark"));
            pictureShare.setUserId(loginUser.getId());

            PictureShareRequest pictureShareRequest = new PictureShareRequest(pictureShare, pictureFileList);
            pictureShareService.addPictureShare(pictureShareRequest);

            responseUtil = HTTPResponseUtil._OK;
            return "success.html";
            //responseUtil.write(response);

        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error(e.getMessage());
            //responseUtil.write(response);
            return "failed.html";
        }
    }

    @RequestMapping(value = "/comment")
    public void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);

            Long pictureShareId = Long.parseLong(request.getParameter("pictureShareId"));
            String commentStr = URLDecoder.decode(request.getParameter("comment"), "UTF-8");

            Comment comment = new Comment();
            comment.setPictureShareId(pictureShareId);
            comment.setCreatedOn(new Date());
            comment.setComment(commentStr);
            comment.setCommentUserId(loginUser.getId());

            commentService.addComment(comment);

            responseUtil = HTTPResponseUtil._OK;
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error("服务器错误", e);
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "/picture_share_list")
    public void getPictrureShareList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            Assert.notNull(request.getParameter("pageNo"));
            Assert.notNull(request.getParameter("pageSize"));
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));


            List<PictureShareDetailDto> detailList = pictureShareService.getDetailList(pageNo, pageSize);
            responseUtil = new HTTPResponseUtil(detailList);
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error("服务器错误", e);
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "/picture_share_detail")
    public void getPictrureShareDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            Long pictureShareId = Long.parseLong(request.getParameter("pictureShareId"));
            PictureShareDetailDto detail = pictureShareService.getDetail(pictureShareId);
            responseUtil = new HTTPResponseUtil(detail);
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error(e.getMessage());
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "/admin/sys_report")
    public void getSysReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            SystemReportDto systemReport = reportService.getSystemReport();
            responseUtil = new HTTPResponseUtil(systemReport);
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error(e.getMessage());
        }
        responseUtil.write(response);
    }


    @RequestMapping(value = "/user_info_list")
    public void getUserInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            List<UserInfoDto> userinfoDtoList = userInfoService.getUserinfoDtoList(pageNo, pageSize);

            responseUtil = new HTTPResponseUtil(userinfoDtoList);
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error("服务器错误", e);
        }
        responseUtil.write(response);
    }


    @RequestMapping(value = "/delete_user")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);

            if (!loginUser.getGrantedAuthority().equals(GrantedAuthorityEnum.ADMIN.getValue())) {
                responseUtil = HTTPResponseUtil._403Error;
            } else {
                userInfoService.deleteUser(Long.parseLong(request.getParameter("userId")));
                responseUtil = HTTPResponseUtil._OK;
            }
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error("服务器错误", e);
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "/delete_Share")
    public void deleteShare(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTTPResponseUtil responseUtil;
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);
            PictureShare pictureShare = hibernateTemplate.get(PictureShare.class, Long.parseLong(request.getParameter("pictureShareId")));

            if (!loginUser.getGrantedAuthority().equals(GrantedAuthorityEnum.ADMIN.getValue()) || pictureShare.getUserId().intValue() != loginUser.getId().intValue()) {
                responseUtil = HTTPResponseUtil._403Error;
            } else {
                pictureShareService.deletePictureShare(pictureShare.getId());
                responseUtil = HTTPResponseUtil._OK;
            }
        } catch (Exception e) {
            responseUtil = HTTPResponseUtil._ServerError;
            logger.error("服务器错误", e);
        }
        responseUtil.write(response);
    }


    private String pictureFileNameFactory(Long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(userId);
        sb.append("_");
        sb.append(new Date().getTime());
        sb.append(".jpg");
        return sb.toString();
    }

    private boolean isImagineFileType(MultipartFile file) {
        return true;
    }
}
