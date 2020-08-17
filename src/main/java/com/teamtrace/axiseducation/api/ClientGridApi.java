package com.teamtrace.axiseducation.api;

import java.util.Date;

public class ClientGridApi {
    private Integer clientId;
    private Integer channelId;
    private String reference;
    private String fullName;
    private String loginName;
    private String email;
    private String mobile;
    private String address;
    private boolean isNotificationEnable;
    private short status;
    private short authType;
    private Date createdDate;
    private Date subscriptionExpiryDate;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public short getAuthType() {
        return authType;
    }

    public void setAuthType(short authType) {
        this.authType = authType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSubscriptionExpiryDate() {
        return subscriptionExpiryDate;
    }

    public void setSubscriptionExpiryDate(Date subscriptionExpiryDate) {
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }
}
