package com.teamtrace.axiseducation.api;

import com.teamtrace.axiseducation.model.Role;

import java.util.List;

public class RoleGroupRoleApi {
    private List<Role> unassignedRoles;
    private List<Role> assignedRoles;

    public List<Role> getUnassignedRoles() {
        return unassignedRoles;
    }

    public void setUnassignedRoles(List<Role> unassignedRoles) {
        this.unassignedRoles = unassignedRoles;
    }

    public List<Role> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<Role> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }
}
