package com.teamtrace.axiseducation.search.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonMapper implements Mapper {
    static HashMap<String, String> COLUMN_MAP;

    @Override
    public List parseResults(Connection con, ResultSet rs, int limit)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();
        ArrayList<HashMap> apis = new ArrayList<>(limit);
        while (rs.next()) {
            HashMap<String, Object> api = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                api.put(metaData.getColumnLabel(i), rs.getObject(i));
            }
            apis.add(api);
        }

        return apis;
    }

    @Override
    public HashMap<String, String> getColumnMap() {
        return COLUMN_MAP;
    }
}
