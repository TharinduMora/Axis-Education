package com.teamtrace.axiseducation.util.notification;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.util.notification.cm.CMSenderFactory;
import com.teamtrace.axiseducation.util.notification.email.EmailSenderFactory;
import com.teamtrace.axiseducation.util.notification.sms.SMSSenderFactory;

import javax.ws.rs.client.Client;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationSender {
    private static final ArrayBlockingQueue<Notification> QUEUE = new ArrayBlockingQueue<>(100);
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void initialize(AxisEducationConfiguration configuration, Client client) {

        CMSenderFactory.initialize(configuration, client);
        EmailSenderFactory.initialize(configuration, client);
        SMSSenderFactory.initialize(configuration, client);

        start(configuration.notificationThreadCount);
    }

    public static void send(Notification notification) {
        try {
            QUEUE.put(notification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void start(int threadCount) {
        executor = Executors.newFixedThreadPool(threadCount);
        NotificationProcessor processor;
        for (int i = 1; i <= threadCount; i++) {
            processor = new NotificationProcessor(QUEUE);
            executor.execute(processor);
        }
    }

}
