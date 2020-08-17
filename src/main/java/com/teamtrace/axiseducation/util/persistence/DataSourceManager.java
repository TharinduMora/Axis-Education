package com.teamtrace.axiseducation.util.persistence;

import com.teamtrace.axiseducation.util.TeamTraceCrypto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceManager {
    private static DataSource datasource;

    public static DataSource getDataSource() {
        return datasource;
    }

    public static void initialize(Map database) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(String.valueOf(database.get("driver")));

        config.setJdbcUrl(String.valueOf(database.get("url")));
        config.setUsername(String.valueOf(database.get("ro_user")));
        config.setPassword(String.valueOf(TeamTraceCrypto.decrypt(database.get("ro_password").toString())));

        config.setPoolName("GBeetle_DB");

        config.setMaximumPoolSize(20);
        //config.setMinimumIdle(2);
        //config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", true);

        datasource = new HikariDataSource(config);
    }
}
