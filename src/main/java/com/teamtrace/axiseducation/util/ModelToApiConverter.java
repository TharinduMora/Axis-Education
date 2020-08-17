package com.teamtrace.axiseducation.util;

import com.teamtrace.axiseducation.api.AdminUserApi;
import com.teamtrace.axiseducation.api.RoleApi;
import com.teamtrace.axiseducation.api.RoleGroupApi;
import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.model.Role;
import com.teamtrace.axiseducation.model.RoleGroup;

import java.util.ArrayList;
import java.util.List;

public class ModelToApiConverter {
    private ModelToApiConverter() {
    }

    public static AdminUserApi convert(AdminUser adminUser) {
        AdminUserApi api = new AdminUserApi();
        api.setAdminUserId(adminUser.getAdminUserId());
        api.setStatus(adminUser.getStatus());
        api.setCreatedDate(adminUser.getCreatedDate());
        api.setEmail(adminUser.getEmail());
        api.setLoginName(adminUser.getLoginName());
        api.setFullName(adminUser.getFullName());
        api.setMobile(adminUser.getMobile());
        api.setNotificationEnable(adminUser.isNotificationEnable());
        api.setPasswordChangeRequired(adminUser.isPasswordChangeRequired());
        api.setProfileImageUrl(adminUser.getProfileImageUrl());
        if (adminUser.getRoleGroups() != null && !adminUser.getRoleGroups().isEmpty()) {
            for (RoleGroup roleGroup : adminUser.getRoleGroups()) {
                api.getRoleGroups().add(roleGroup.getRoleGroupId());
            }
        }

        return api;
    }

    public static RoleGroupApi convert(RoleGroup roleGroup) {
        RoleGroupApi api = new RoleGroupApi();
        api.setRoleGroupId(roleGroup.getRoleGroupId());
        api.setName(roleGroup.getName());
        api.setStatus(roleGroup.getStatus());
        api.setCreatedDate(roleGroup.getCreatedDate());

        return api;
    }

    public static RoleApi convert(Role role) {
        RoleApi api = new RoleApi();
        api.setRoleId(role.getRoleId());
        api.setName(role.getName());

        return api;
    }

    public static List<RoleApi> convertRoleList(List<Role> roles) {
        List list = new ArrayList();
        for (Role role : roles) {
            list.add(convert(role));
        }

        return list;
    }
}
