package com.teamtrace.axiseducation.util;

import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.model.AdminUserSession;
import com.teamtrace.axiseducation.model.HistoryAdminUserSession;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;


public class SessionUtil {

    public static synchronized void addAdminUserSession(String sessionId, AdminUser adminUser) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        Query query = em.createNamedQuery("AdminUserSession.findAdminUserSessionsByAdminUserId", AdminUserSession.class);
        query.setParameter("loginName", adminUser.getAdminUserId());

        List<AdminUserSession> userSessions = query.getResultList();

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            if (userSessions != null && !userSessions.isEmpty()) {
                for (AdminUserSession userSession : userSessions) {
                    HistoryAdminUserSession historyAdminUserSession = new HistoryAdminUserSession();
                    historyAdminUserSession.setSessionId(sessionId);
                    historyAdminUserSession.setAdminUser(userSession.getAdminUser());
                    historyAdminUserSession.setSessionTime(userSession.getSessionTime());
                    historyAdminUserSession.setLogoutTime(new Date());

                    em.persist(historyAdminUserSession);

                    Query deleteQuery = em.createNamedQuery("AdminUserSession.deleteAdminUserSession", AdminUserSession.class);
                    deleteQuery.setParameter("sessionId", userSession.getSessionId());

                    deleteQuery.executeUpdate();
                }
            }


            AdminUserSession userSession = new AdminUserSession();
            userSession.setSessionId(sessionId);
            userSession.setAdminUser(adminUser);
            userSession.setLastActiveTime(new Date());
            userSession.setSessionTime(new Date());

            em.persist(userSession);

            txn.commit();
        } catch (Exception e) {
            ExceptionHandler.handleException(txn, em, e);
        }
    }

    public static synchronized AdminUser getAdminUserSession(String sessionId) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUserSession old = em.find(AdminUserSession.class, sessionId);
        if (old == null) {
            return null;
        }

        AdminUser adminUser = old.getAdminUser();

        old.setLastActiveTime(new Date());
        em.merge(old);

        return adminUser;
    }

    public static synchronized boolean removeAdminUserSession(String sessionId) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUserSession old = em.find(AdminUserSession.class, sessionId);
        if (old == null) {
            return false;
        }

        HistoryAdminUserSession historyAdminUserSession = new HistoryAdminUserSession();
        historyAdminUserSession.setSessionId(sessionId);
        historyAdminUserSession.setAdminUser(old.getAdminUser());
        historyAdminUserSession.setSessionTime(old.getSessionTime());
        historyAdminUserSession.setLogoutTime(new Date());

        EntityTransaction txn = em.getTransaction();
        txn.begin();
        try {
            txn.commit();

            em.persist(historyAdminUserSession);

            Query deleteQuery = em.createNamedQuery("AdminUserSession.deleteAdminUserSession", AdminUserSession.class);
            deleteQuery.setParameter("sessionId", old.getSessionId());

            deleteQuery.executeUpdate();

            return true;
        } catch (Exception e) {
            ExceptionHandler.handleException(txn, em, e);
            return false;
        }

    }

    public boolean isValidAdminUserSession(String sessionId) {
        EntityManager em = PersistenceManager.getEntityManagerFactory().createEntityManager();

        AdminUserSession old = em.find(AdminUserSession.class, sessionId);
        if (old == null) {
            return false;
        }

        AdminUser adminUser = old.getAdminUser();
        if (adminUser.getStatus() == Statuses.APPROVED) {
            return true;
        }
        return false;
    }

}
