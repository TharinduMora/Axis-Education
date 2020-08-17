package com.teamtrace.axiseducation.search.mapper;

import com.teamtrace.axiseducation.api.RoleGroupApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoleGroupMapper implements Mapper {
    static HashMap<String, String> columnMap;

    static {
        columnMap = new HashMap<>(4);
        columnMap.put("roleGroupId", "A.role_group_id");
        columnMap.put("name", "A.name");
        columnMap.put("status", "A.status");
        columnMap.put("createdDate", "A.created_date");
        columnMap.put("createdBy", "B.full_name");
    }

    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        ArrayList<RoleGroupApi> apis = new ArrayList<>(limit);
        while (rs.next()) {
            RoleGroupApi api = new RoleGroupApi();
            api.setRoleGroupId(rs.getInt("A.role_group_id"));
            api.setName(rs.getString("A.name"));
            api.setStatus(rs.getShort("A.status"));
            api.setCreatedDate(rs.getTimestamp("A.created_date"));
            api.setCreatedBy(rs.getString("B.full_name"));

            apis.add(api);
        }
        return apis;
    }

    public HashMap<String, String> getColumnMap() {
        return columnMap;
    }
}
