package com.teamtrace.axiseducation.util.notification.push;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;

import javax.persistence.EntityManager;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class PushMessageSender implements Sender {
    private static final PushMessageSender PUSH_MESSAGE_SENDER = new PushMessageSender();
    private static int MAX_SESSIONS = 1000;
    private static HashMap<Integer, HashMap> BRANCH_PUSH_SESSIONS = new HashMap<>(MAX_SESSIONS);
    private static HashMap<Integer, HashMap> SHOP_PUSH_SESSIONS = new HashMap<>(MAX_SESSIONS);
    private static ObjectMapper json = new ObjectMapper();

    private PushMessageSender() {
    }

    public static PushMessageSender getInstance() {
        return PUSH_MESSAGE_SENDER;
    }

    /*public synchronized void addAdminSession(Admin admin, Session session) {
        HashMap<Integer, Session> adminSessions = null;
        if (admin.isSuperAdmin()) {
            adminSessions = SHOP_PUSH_SESSIONS.computeIfAbsent(admin.getShopId(), k -> new HashMap<>(10, 5));
        } else {
            adminSessions = BRANCH_PUSH_SESSIONS.computeIfAbsent(admin.getBranchId(), k -> new HashMap<>(10, 5));
        }
        Session old = adminSessions.get(admin.getAdminId());
        if (old != null) {
            closeSession(old);
        }
        adminSessions.put(admin.getAdminId(), session);
    }

    public synchronized void removeAdminSession(Admin admin) {
        HashMap<Integer, Session> adminSessions = null;
        if (admin.isSuperAdmin()) {
            adminSessions =
                    SHOP_PUSH_SESSIONS.computeIfAbsent(admin.getShopId(), k -> new HashMap<>(10));
        } else {
            adminSessions =
                    BRANCH_PUSH_SESSIONS.computeIfAbsent(admin.getBranchId(), k -> new HashMap<>(10));
        }
        Session old = adminSessions.get(admin.getAdminId());
        if (old != null) {
            if (old.isOpen())
                old.getAsyncRemote().sendText("{\"notificationType\" : " + NotificationType.ERROR + " }");
            closeSession(old);
        }
        adminSessions.remove(admin.getAdminId());
    }*/

    private void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }

    @Override
    public boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        return false;
    }


    private boolean pushToAdmin(Notification notification, String message) {
        /*try {
            HashMap<Integer, Session> adminSessions =
                    SHOP_PUSH_SESSIONS.get(notification.getShopId());
            if (adminSessions != null && adminSessions.size() > 0) {
                for (Session session : adminSessions.values()) {
                    if (session.isOpen()) {//todo remove if not connected
                        session.getAsyncRemote().sendText(message);
                    }
                }
            }
            if (notification.getBranchId() > 0) {
                adminSessions = BRANCH_PUSH_SESSIONS.get(notification.getBranchId());
                if (adminSessions != null && adminSessions.size() > 0) {
                    for (Session session : adminSessions.values()) {
                        if (session.isOpen()) {
                            session.getAsyncRemote().sendText(message);
                        }
                    }
                }
            }
            notification.setPushStatus(Status.APPROVED);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return false;
    }
}
