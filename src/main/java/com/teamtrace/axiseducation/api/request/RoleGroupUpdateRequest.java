package com.teamtrace.axiseducation.api.request;

import java.util.List;

public class RoleGroupUpdateRequest extends Request {
    private int roleGroupId;
    private String name;
    private List<Integer> roles;

    public int getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(int roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
