package com.teamtrace.axiseducation.util.persistence;

import com.teamtrace.axiseducation.util.TeamTraceCrypto;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManager {
    private static EntityManagerFactory emf;

    private PersistenceManager() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void initialize(Map database) {
        Map properties = new HashMap(4);
        properties.put("javax.persistence.jdbc.driver", database.get("driver"));
        properties.put("javax.persistence.jdbc.url", database.get("url"));
        properties.put("javax.persistence.jdbc.user", database.get("rw_user"));
        properties.put("javax.persistence.jdbc.password", TeamTraceCrypto.decrypt(database.get("rw_password").toString()));
        emf = Persistence.createEntityManagerFactory("AXIS_PU", properties);

        emf.createEntityManager();
    }
}
