package com.xm.picture_share.listener;

import com.xm.picture_share.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class PictureShareListener extends ContextLoaderListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        try {
            initSystemConfig();
        } catch (IOException e) {
            logger.error("未找到系统配置文件", e);
        }
    }

    private void initSystemConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(URLDecoder.decode(this.getClass().getResource("/").getFile() + "pictureshare.properties", "UTF-8"))));
        File file = new File(properties.get("system.filepath").toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        SystemConfig.setUploadFilePath(properties.get("system.filepath").toString());

        SystemConfig.setFileUrlPrefix(properties.get("system.fileurlprefix").toString());

        List<String> noauthurlList = new ArrayList<String>();
        String noauthurl = properties.get("system.noauthurl").toString();
        StringTokenizer st = new StringTokenizer(noauthurl, ",");
        while (st.hasMoreTokens()) {
            noauthurlList.add(st.nextToken());
        }
        SystemConfig.setNoAuthUrl(noauthurlList);

        List<String> adminurlList = new ArrayList<String>();
        String adminurl = properties.get("system.adminurl").toString();
        st = new StringTokenizer(adminurl, ",");
        while (st.hasMoreTokens()) {
            adminurlList.add(st.nextToken());
        }
        SystemConfig.setAdminUrl(adminurlList);

        logger.info("初始化系统变量成功");
    }
}
