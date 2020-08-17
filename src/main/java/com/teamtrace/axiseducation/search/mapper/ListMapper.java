package com.teamtrace.axiseducation.search.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMapper implements Mapper {
    static HashMap<String, String> columnMap;

    static {
        columnMap = new HashMap<>(1);
    }

    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        ArrayList<HashMap> apis = new ArrayList<>(limit);
        while (rs.next()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            HashMap<String, Object> api = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                if (rs.getObject(i) == null)
                    api.put(metaData.getColumnLabel(i), "");
                else
                    api.put(metaData.getColumnLabel(i), rs.getObject(i));
            }

            apis.add(api);
        }

        return apis;
    }

    public HashMap<String, String> getColumnMap() {
        return columnMap;
    }
}
