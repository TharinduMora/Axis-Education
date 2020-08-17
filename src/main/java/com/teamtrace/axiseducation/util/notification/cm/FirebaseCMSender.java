package com.teamtrace.axiseducation.util.notification.cm;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseCMSender implements Sender {
    private static final FirebaseCMSender FIREBASE_CM_SENDER = new FirebaseCMSender();
    private static Logger logger = Logger.getLogger(FirebaseCMSender.class.getName());
    private static Client client;
    private static int MAX_SESSIONS = 1000;

    private FirebaseCMSender() {
    }

    public static FirebaseCMSender getInstance() {
        return FIREBASE_CM_SENDER;
    }

    public static void initialize(AxisEducationConfiguration config, Client clientRef) {
        client = clientRef;
    }

    public static synchronized void removeAdminSession(AdminUser adminUser) {
    }

    @Override
    public boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }

    @Override
    public boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }

    private boolean sendFCMToDevice(String deviceId, HashMap data, String firebaseUrl, String firebaseKey) {
        try {
            HashMap<String, Object> body = new HashMap<>(5);
            body.put("to", deviceId);
            body.put("priority", "high");
            body.put("notification", data);//for iOS
            body.put("data", data);//for Android
            Response fcmResponse = client.target(firebaseUrl).request()
                    .header("Authorization", "key=" + firebaseKey)
                    .post(Entity.json(body));
            if (fcmResponse.getStatus() == 200) {
                logger.log(Level.INFO, "FCM message sent.");
                return true;
            } else {//failed
                logger.log(Level.SEVERE, "Fail to send to FCM. status : " + fcmResponse.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean sendFCMToApp(HashMap data, String firebaseUrl, String firebaseKey) {
        try {
            HashMap<String, Object> body = new HashMap<>(5);
            body.put("priority", "high");
            body.put("notification", data);//for iOS
            body.put("data", data);//for Android
            Response fcmResponse = client.target(firebaseUrl).request()
                    .header("Authorization", "key=" + firebaseKey)
                    .post(Entity.json(body));
            if (fcmResponse.getStatus() == 200) {
                logger.log(Level.INFO, "FCM message sent.");
                return true;
            } else {//failed
                logger.log(Level.SEVERE, "Fail to send to FCM. status : " + fcmResponse.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
