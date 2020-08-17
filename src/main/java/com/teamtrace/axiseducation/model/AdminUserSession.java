package com.teamtrace.axiseducation.model;

import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_user_session")
@Cacheable(value = true)
@org.eclipse.persistence.annotations.Cache(type = CacheType.FULL, size = Model.SIZE_MEDIUM, expiry = Model.EXPIRY_IN_DAY, coordinationType = CacheCoordinationType.NONE)
@NamedQueries({
        @NamedQuery(name = "AdminUserSession.findAdminUserSessionsByAdminUserId", query = "SELECT a FROM AdminUserSession a WHERE a.adminUser.adminUserId = :adminUserId"),
        @NamedQuery(name = "AdminUserSession.deleteAdminUserSession", query = "DELETE FROM AdminUserSession a WHERE a.sessionId = :sessionId")
})
public class AdminUserSession {
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
    @Column(name = "last_active_time", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActiveTime;

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

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}
