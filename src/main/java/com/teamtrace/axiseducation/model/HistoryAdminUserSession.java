package com.teamtrace.axiseducation.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history_admin_user_session")
@Cacheable
public class HistoryAdminUserSession {
    @Id
    @Column(name = "session_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(400)")
    private String sessionId;
    @Column(name = "channel", updatable = false, nullable = false, columnDefinition = "SMALLINT(2) UNSIGNED")
    private int channel;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id", updatable = false, nullable = false, columnDefinition = AdminUser.PK_TYPE)
    private AdminUser adminUser;
    @Column(name = "session_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionTime;
    @Column(name = "logout_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
}
