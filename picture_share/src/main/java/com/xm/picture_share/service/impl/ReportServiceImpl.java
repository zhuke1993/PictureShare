package com.xm.picture_share.service.impl;

import com.xm.picture_share.dto.SystemReportDto;
import com.xm.picture_share.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SystemReportDto getSystemReport() {
        final int[] userNum = new int[1];
        jdbcTemplate.query("select count(1) as userNum from user_info u where u.granted_authority = 'normal_user';", new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                userNum[0] = resultSet.getInt("userNum");
            }
        });

        final int[] imageNum = new int[1];
        jdbcTemplate.query("select count(1) as pictureNum from picture_file f; ", new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                imageNum[0] = resultSet.getInt("pictureNum");
            }
        });

        final int[] commentNum = new int[1];
        jdbcTemplate.query("select count(1) as commentNum from comment c;", new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                commentNum[0] = resultSet.getInt("commentNum");
            }
        });

        SystemReportDto systemReportDto = new SystemReportDto(imageNum[0], commentNum[0], userNum[0]);

        return systemReportDto;
    }
}
