package com.teamtrace.axiseducation.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@Cacheable(value = false)
public class Notification {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int notificationId;
    @Column(name = "type", insertable = true, updatable = false, nullable = false)
    private int type;
    @Column(name = "notification_date", insertable = true, updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id", nullable = true)
    private AdminUser adminUser;
    @Column(name = "email_from", insertable = true, updatable = true, nullable = true, length = 255)
    private String emailFrom;
    @Column(name = "email_to", insertable = true, updatable = true, nullable = true, length = 255)
    private String emailTo;
    @Column(name = "email_cc", insertable = true, updatable = true, nullable = true, length = 4096)
    private String emailCc;
    @Column(name = "email_bcc", insertable = true, updatable = true, nullable = true, length = 4096)
    private String emailBcc;
    @Column(name = "e_mail_subject", insertable = true, updatable = true, nullable = true, length = 255)
    private String emailSubject;
    @Column(name = "email_body", insertable = true, updatable = true, nullable = true, length = 4096, columnDefinition = "LONGTEXT")
    private String emailBody;
    @Lob
    @Column(name = "attachment", insertable = true, updatable = true, nullable = true)
    private byte[] attachment;
    @Column(name = "file_name", insertable = true, updatable = true, nullable = true, length = 100)
    private String filename;
    @Column(name = "mobile_no", insertable = true, updatable = true, nullable = true, length = 20)
    private String mobileNo;
    @Column(name = "sms_body", insertable = true, updatable = true, nullable = true, length = 255)
    private String smsBody;
    @Column(name = "email_status", insertable = true, updatable = true, nullable = false)
    private int emailStatus;
    @Column(name = "sms_status", insertable = true, updatable = true, nullable = false)
    private int smsStatus;
    @Column(name = "push_status", insertable = true, updatable = true, nullable = false)
    private int pushStatus;
    @Column(name = "fcm_status", insertable = true, updatable = true, nullable = false)
    private int fcmStatus;
    @Column(name = "fcm_body", insertable = true, updatable = true, nullable = true, length = 255)
    private String fcmBody;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    public String getEmailBcc() {
        return emailBcc;
    }

    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public int getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(int smsStatus) {
        this.smsStatus = smsStatus;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getFcmStatus() {
        return fcmStatus;
    }

    public void setFcmStatus(int fcmStatus) {
        this.fcmStatus = fcmStatus;
    }

    public String getFcmBody() {
        return fcmBody;
    }

    public void setFcmBody(String fcmBody) {
        this.fcmBody = fcmBody;
    }
}
