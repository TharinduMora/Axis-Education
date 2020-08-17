package com.teamtrace.axiseducation.util.notification.cm;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;

public class DefaultCMSender implements Sender {
    private static final DefaultCMSender DEFAULT_CM_SENDER = new DefaultCMSender();

    private DefaultCMSender() {
    }

    public static DefaultCMSender getInstance() {
        return DEFAULT_CM_SENDER;
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
