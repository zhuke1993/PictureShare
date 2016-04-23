package com.xm.picture_share.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信息
 */
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "user_name")
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    /**
     * 地址-省
     */
    @Column(name = "add_province")
    private String addProvince;
    /**
     * 地址-市
     */
    @Column(name = "add_city")
    private String addCity;
    /**
     * 地址-详细
     */
    @Column(name = "add_detail")
    private String addDetail;
    @Column(name = "school_name")
    private String schoolName;
    /**
     * 0-待通过资质审核
     * 1-通过资质审核
     */
    @Column(name = "is_available", columnDefinition = "TINYINT default 0")
    private short isAvailable;

    @Column
    private String realname;

    @Column(name = "idcard_no")
    private String idcardNo;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "photo_url")
    private String photoURL;
    /**
     * 信用评分
     */
    @Column(name = "credit_score", columnDefinition = "int default 0")
    private int creditScore;
    @Column
    private String guid;

    @Column(name = "granted_authority")
    private String grantedAuthority;

    @Column(name = "id_certification")
    private Long idCertification;//身份证照片

    @Column(name = "school_certification")
    private Long schoolCertification;//学生证照片

    public Long getIdCertification() {
        return idCertification;
    }

    public void setIdCertification(Long idCertification) {
        this.idCertification = idCertification;
    }

    public Long getSchoolCertification() {
        return schoolCertification;
    }

    public void setSchoolCertification(Long schoolCertification) {
        this.schoolCertification = schoolCertification;
    }

    public String getGrantedAuthority() {
        return grantedAuthority;
    }

    public void setGrantedAuthority(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddProvince() {
        return addProvince;
    }

    public void setAddProvince(String addProvince) {
        this.addProvince = addProvince;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddDetail() {
        return addDetail;
    }

    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail;
    }

    public short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(short isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "phoneNo='" + phoneNo + '\'' +
                ", id=" + id +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", addProvince='" + addProvince + '\'' +
                ", addCity='" + addCity + '\'' +
                ", addDetail='" + addDetail + '\'' +
                ", schoolName=" + schoolName +
                ", isAvailable=" + isAvailable +
                ", realname='" + realname + '\'' +
                ", idcardNo='" + idcardNo + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", creditScore=" + creditScore +
                ", guid='" + guid + '\'' +
                '}';
    }
}
