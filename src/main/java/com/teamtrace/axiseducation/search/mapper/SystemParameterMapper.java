package com.teamtrace.axiseducation.search.mapper;

import com.teamtrace.axiseducation.api.SystemParameterApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemParameterMapper implements Mapper {
    static HashMap<String, String> columnMap;

    static {
        columnMap = new HashMap<>(6);
        columnMap.put("parameterId", "A.parameter_id");
        columnMap.put("dataType", "A.data_type");
        columnMap.put("key", "A.sys_key");
        columnMap.put("value", "A.sys_value");
    }

    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        ArrayList<SystemParameterApi> apis = new ArrayList<>(limit);
        while (rs.next()) {
            SystemParameterApi api = new SystemParameterApi();
            api.setParameterId(rs.getInt("A.parameter_id"));
            api.setDataType(rs.getShort("A.data_type"));
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
