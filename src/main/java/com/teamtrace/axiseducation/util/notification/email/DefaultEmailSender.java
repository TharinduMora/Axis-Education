package com.teamtrace.axiseducation.util.notification.email;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;

public class DefaultEmailSender implements Sender {
    private static final DefaultEmailSender DEFAULT_EMAIL_SENDER = new DefaultEmailSender();

    private DefaultEmailSender() {
    }

    public static DefaultEmailSender getInstance() {
        return DEFAULT_EMAIL_SENDER;
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
