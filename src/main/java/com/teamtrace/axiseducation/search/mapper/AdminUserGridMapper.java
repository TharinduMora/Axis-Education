package com.teamtrace.axiseducation.search.mapper;

import com.teamtrace.axiseducation.api.AdminUserGridApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminUserGridMapper implements Mapper {
    static HashMap<String, String> columnMap;

    static {
        columnMap = new HashMap<>(11);
        columnMap.put("adminUserId", "A.admin_user_id");
        columnMap.put("email", "A.email");
        columnMap.put("mobile", "A.mobile");
        columnMap.put("loginName", "A.login_name");
        columnMap.put("fullName", "A.full_name");
        columnMap.put("profileImageUrl", "A.profile_image_url");
        columnMap.put("isPasswordChangeRequired", "A.is_password_change_required");
        columnMap.put("isNotificationEnable", "A.is_notification_enable");
        columnMap.put("status", "A.status");
        columnMap.put("createdDate", "A.created_date");
    }

    public List parseResults(Connection con, ResultSet rs, int limit) throws SQLException {
        ArrayList<AdminUserGridApi> apis = new ArrayList<>(limit);
        while (rs.next()) {
            AdminUserGridApi api = new AdminUserGridApi();
            api.setAdminUserId(rs.getInt("A.admin_user_id"));
            api.setEmail(rs.getString("A.email"));
            api.setMobile(rs.getString("A.mobile"));
            api.setLoginName(rs.getString("A.login_name"));
            api.setFullName(rs.getString("A.full_name"));
            api.setProfileImageUrl(rs.getString("A.profile_image_url"));
            api.setPasswordChangeRequired(rs.getBoolean("is_password_change_required"));
            api.setNotificationEnable(rs.getBoolean("A.is_notification_enable"));
            api.setStatus(rs.getShort("A.status"));
            api.setCreatedDate(rs.getTimestamp("A.created_date"));

            apis.add(api);
        }
        return apis;
    }

    public HashMap<String, String> getColumnMap() {
        return columnMap;
    }
}
