package com.teamtrace.axiseducation.model;

import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.teamtrace.axiseducation.model.Model.EXPIRY_IN_DAY;
import static com.teamtrace.axiseducation.model.Model.SIZE_MEDIUM;

@Entity
@Table(name = "admin_user")
@Cacheable(value = true)
@org.eclipse.persistence.annotations.Cache(type = CacheType.FULL, size = SIZE_MEDIUM, expiry = EXPIRY_IN_DAY, coordinationType = CacheCoordinationType.NONE)
@NamedQueries({
        @NamedQuery(name = "AdminUser.findByLoginName", query = " SELECT o FROM AdminUser o WHERE o.loginName = :loginName ")
})
public class AdminUser {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_user_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private Integer adminUserId;
    @Column(name = "type_id", nullable = false, columnDefinition = "TINYINT(3) UNSIGNED")
    private short typeId;
    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(45)")
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(400)")
    private String password;
    @Column(name = "mobile", columnDefinition = "VARCHAR(20)  DEFAULT NULL")
    private String mobile;
    @Column(name = "login_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String loginName;
    @Column(name = "full_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String fullName;
    @Column(name = "profile_image_url", columnDefinition = "VARCHAR(100) DEFAULT NULL")
    private String profileImageUrl;
    @Column(name = "is_password_change_required", nullable = false, columnDefinition = "TINYINT(1) UNSIGNED DEFAULT '0'")
    private boolean isPasswordChangeRequired;
    @Column(name = "is_notification_enable", nullable = false, columnDefinition = "TINYINT(1) UNSIGNED DEFAULT '0'")
    private boolean isNotificationEnable;
    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(2) UNSIGNED DEFAULT '0'")
    private short status;
    @Column(name = "created_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_user_role_group",
            joinColumns = {@JoinColumn(name = "admin_user_id", referencedColumnName = "admin_user_id", columnDefinition = PK_TYPE)},
            inverseJoinColumns = {@JoinColumn(name = "role_group_id", referencedColumnName = "role_group_id", columnDefinition = RoleGroup.PK_TYPE)})
    private List<RoleGroup> roleGroups = new ArrayList<>();

    @Transient
    private String pwd;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public short getTypeId() {
        return typeId;
    }

    public void setTypeId(short typeId) {
        this.typeId = typeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.password = "e10adc3949ba59abbe56e057f20f883e"; //todo remove
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

    public List<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(List<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
