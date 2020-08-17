package com.teamtrace.axiseducation.api.request;

import java.util.List;

public class AdminUsersRoleUpdateRequest extends Request {
    private int roleGroupId;
    private List<Integer> adminIds;

    public int getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(int roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public List<Integer> getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(List<Integer> adminIds) {
        this.adminIds = adminIds;
    }
}
