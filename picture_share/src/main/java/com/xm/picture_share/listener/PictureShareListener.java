package com.xm.picture_share.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PictureShareListener extends ContextLoaderListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(this.getClass().getResource("/").getFile() + "pictureshare.properties")));
            File file = new File(properties.get("system.filepath").toString());
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (IOException e) {
            logger.error("未找到系统配置文件", e);
        }
    }
}
