package com.teamtrace.axiseducation.api.request;

import java.util.List;

public class RoleGroupCreateRequest extends Request {
    private String name;
    private List<Integer> roles;

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
