package com.teamtrace.axiseducation.util.push;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/push/{sessionId}")
public class PushResource {
    static Logger logger = Logger.getLogger(PushResource.class.getName());

    @OnOpen
    public void onOpen(@PathParam("sessionId") String sessionId, Session session) throws IOException {
        /*if (SessionStore.isValidAdminSession(sessionId)) {
            AdminUser adminUser = SessionStore.getAdminSession(sessionId);
            if (EntitlementValidator.isEntitled(Function.ADMIN_PUSH_NOTIFICATIONS, admin, null)) {
                SessionStore.getWsSessionIdMap().put(session.getId(), sessionId);
                PushMessageSender.getInstance().addAdminSession(admin, session);
            } else {
                session.getAsyncRemote().sendText("{\"notificationType\" : " + NotificationType.UNAUTHORIZED + " }");
                session.close();
            }

        } else {
            session.getAsyncRemote().sendText("{\"notificationType\" : " + NotificationType.ERROR + " }");
            session.close();
        }*/
    }

    @OnMessage
    public void message(String message, Session session) {
        logger.info("Message received : " + session.getId() + " - " + message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        /*String sessionId = SessionStore.getWsSessionIdMap().get(session.getId());
        SessionStore.getWsSessionIdMap().remove(session.getId());

        AdminUser admin = SessionStore.getAdminSession(sessionId);

        if (admin != null) {
            PushMessageSender.getInstance().removeAdminSession(admin);
        }*/
    }

}
