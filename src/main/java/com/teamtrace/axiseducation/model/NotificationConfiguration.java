package com.teamtrace.axiseducation.model;

import com.teamtrace.axiseducation.model.converter.JsonConverter;
import org.bson.Document;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.annotations.Mutable;

import static com.teamtrace.axiseducation.model.Model.EXPIRY_NONE;
import static com.teamtrace.axiseducation.model.Model.SIZE_SMALL;

import javax.persistence.*;

@Entity
@Table(name = "notification_configuration")
@Cacheable(value = true)
@org.eclipse.persistence.annotations.Cache(type = CacheType.FULL, size = SIZE_SMALL, expiry = EXPIRY_NONE, coordinationType = CacheCoordinationType.NONE)
public class NotificationConfiguration {
    public static final String PK_TYPE = "SMALLINT(5) UNSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nc_id", updatable = false, nullable = false, columnDefinition = PK_TYPE + " AUTO_INCREMENT")
    private int configId;

    @Column(name = "email_config", columnDefinition = "JSON")
    @Convert(converter = JsonConverter.class)
    @Mutable
    private Document emailConfig;
    @Column(name = "sms_config", columnDefinition = "JSON")
    @Convert(converter = JsonConverter.class)
    @Mutable
    private Document smsConfig;
    @Column(name = "cm_config", columnDefinition = "JSON")
    @Convert(converter = JsonConverter.class)
    @Mutable
    private Document cmConfig;

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public Document getEmailConfig() {
        return emailConfig;
    }

    public void setEmailConfig(Document emailConfig) {
        this.emailConfig = emailConfig;
    }

    public Document getSmsConfig() {
        return smsConfig;
    }

    public void setSmsConfig(Document smsConfig) {
        this.smsConfig = smsConfig;
    }

    public Document getCmConfig() {
        return cmConfig;
    }

    public void setCmConfig(Document cmConfig) {
        this.cmConfig = cmConfig;
    }
}
