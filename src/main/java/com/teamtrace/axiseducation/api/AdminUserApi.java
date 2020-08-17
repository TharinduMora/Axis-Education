package com.teamtrace.axiseducation.api;

import java.util.ArrayList;
import java.util.List;

public class AdminUserApi extends AdminUserGridApi {
    private String password;
    private List<Integer> roleGroups = new ArrayList<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(List<Integer> roleGroups) {
        this.roleGroups = roleGroups;
    }
}
