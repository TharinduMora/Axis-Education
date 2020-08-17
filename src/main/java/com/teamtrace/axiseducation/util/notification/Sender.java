package com.teamtrace.axiseducation.util.notification;

import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;

import javax.persistence.EntityManager;

public interface Sender {
    boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em);

    boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em);
}
