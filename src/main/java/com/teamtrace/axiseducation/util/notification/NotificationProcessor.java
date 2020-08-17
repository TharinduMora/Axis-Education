package com.teamtrace.axiseducation.util.notification;

import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.NotificationType;
import com.teamtrace.axiseducation.util.notification.cm.CMSenderFactory;
import com.teamtrace.axiseducation.util.notification.email.EmailSenderFactory;
import com.teamtrace.axiseducation.util.notification.push.PushMessageSender;
import com.teamtrace.axiseducation.util.notification.sms.SMSSenderFactory;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationProcessor implements Runnable {
    static Logger logger = Logger.getLogger(NotificationProcessor.class.getName());
    private ArrayBlockingQueue<Notification> queue;

    public NotificationProcessor(ArrayBlockingQueue<Notification> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Notification notification = queue.take();// block until a request arrives
                EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

                NotificationConfiguration notificationConfig = em.find(NotificationConfiguration.class,
                        NotificationConfiguration.class);

                switch (notification.getType()) {
                    case NotificationType.ADMIN_FORGOT_PASSWORD: {
                        adminForgotPassword(notification, notificationConfig, em);
                        break;
                    }
                    case NotificationType.ADMIN_TEMPORARY_PASSWORD: {
                        adminTemporaryPassword(notification, notificationConfig, em);
                        break;
                    }
                    default: {
                        logger.log(Level.WARNING, "Notification type : " + notification.getType());
                    }
                }
                EntityTransaction txn = em.getTransaction();
                txn.begin();
                try {
                    em.merge(notification);
                    txn.commit();
                    logger.log(Level.INFO, "Notification sent successfully. Id : " + notification.getNotificationId());
                } catch (Exception e) {
                    if (txn.isActive()) {
                        txn.rollback();
                    }
                    em.clear();
                    e.printStackTrace();

                    logger.log(Level.WARNING, "Notification not sent. error : " + e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        Sender pushSender = PushMessageSender.getInstance();
        boolean notificationSent = pushSender.adminForgotPassword(notification, notificationConfig, em);

        Sender emailSender = EmailSenderFactory.getEmailSender(notificationConfig.getEmailConfig());
        notificationSent = notificationSent && emailSender.adminForgotPassword(notification, notificationConfig, em);

        Sender cmSender = CMSenderFactory.getCMSender(notificationConfig.getCmConfig());
        notificationSent = notificationSent && cmSender.adminForgotPassword(notification, notificationConfig, em);

        Sender smsSender = SMSSenderFactory.getSMSSender(notificationConfig.getSmsConfig());
        notificationSent = notificationSent && smsSender.adminForgotPassword(notification, notificationConfig, em);

        return notificationSent;
    }

    private boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig,
                                           EntityManager em) {
        Sender pushSender = PushMessageSender.getInstance();
        boolean notificationSent = pushSender.adminTemporaryPassword(notification, notificationConfig, em);

        Sender emailSender = EmailSenderFactory.getEmailSender(notificationConfig.getEmailConfig());
        notificationSent = notificationSent && emailSender.adminTemporaryPassword(notification, notificationConfig, em);

        Sender cmSender = CMSenderFactory.getCMSender(notificationConfig.getCmConfig());
        notificationSent = notificationSent && cmSender.adminTemporaryPassword(notification, notificationConfig, em);

        Sender smsSender = SMSSenderFactory.getSMSSender(notificationConfig.getSmsConfig());
        notificationSent = notificationSent && smsSender.adminTemporaryPassword(notification, notificationConfig, em);

        return notificationSent;
    }

}
