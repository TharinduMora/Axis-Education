package com.teamtrace.axiseducation.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ExceptionHandler {
    private ExceptionHandler() {
    }

    public static void handleException(final EntityTransaction txn, final EntityManager em,
                                       final Exception e) {
        if (txn != null && txn.isActive()) {
            txn.rollback();
        }
        em.clear();
        e.printStackTrace();
    }
}
