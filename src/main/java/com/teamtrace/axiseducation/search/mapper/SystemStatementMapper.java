package com.teamtrace.axiseducation.search.mapper;

import com.teamtrace.axiseducation.api.SystemStatementApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemStatementMapper implements Mapper {
    static HashMap<String, String> columnMap;

    static {
        columnMap = new HashMap<>(3);
        columnMap.put("statementId", "A.statement_id");
        columnMap.put("key", "A.sys_key");
        columnMap.put("value", "A.sys_value");
    }

    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        ArrayList<SystemStatementApi> apis = new ArrayList<>(limit);
        while (rs.next()) {
            SystemStatementApi api = new SystemStatementApi();
            api.setStatementId(rs.getInt("A.statement_id"));
            api.setKey(rs.getString("A.sys_key"));
            api.setValue(rs.getString("A.sys_value"));

            apis.add(api);
        }
        return apis;
    }

    public HashMap<String, String> getColumnMap() {
        return columnMap;
    }
}
