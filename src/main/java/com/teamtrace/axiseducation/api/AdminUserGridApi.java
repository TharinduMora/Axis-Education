package com.teamtrace.axiseducation.api;

import java.util.Date;

public class AdminUserGridApi {
    protected Integer adminUserId;
    protected String email;
    protected String mobile;
    protected String loginName;
    protected String fullName;
    protected String profileImageUrl;
    protected boolean isPasswordChangeRequired;
    protected boolean isNotificationEnable;
    protected short status;
    protected Date createdDate;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isPasswordChangeRequired() {
        return isPasswordChangeRequired;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        isPasswordChangeRequired = passwordChangeRequired;
    }

    public boolean isNotificationEnable() {
        return isNotificationEnable;
    }

    public void setNotificationEnable(boolean notificationEnable) {
        isNotificationEnable = notificationEnable;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
