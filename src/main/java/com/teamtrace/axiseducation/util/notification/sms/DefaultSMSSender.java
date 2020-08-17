package com.teamtrace.axiseducation.util.notification.sms;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;

public class DefaultSMSSender implements Sender {
    private static final DefaultSMSSender DEFAULT_SMS_SENDER = new DefaultSMSSender();

    private DefaultSMSSender() {
    }

    public static DefaultSMSSender getInstance() {
        return DEFAULT_SMS_SENDER;
    }

    public static void initialize(AxisEducationConfiguration config, Client clientRef) {
    }

    @Override
    public boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }

    @Override
    public boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }
}
