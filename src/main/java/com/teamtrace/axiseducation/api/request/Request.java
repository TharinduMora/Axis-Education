package com.teamtrace.axiseducation.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtrace.axiseducation.model.AdminUser;

import java.util.ResourceBundle;

public class Request {
    @JsonIgnore
    protected ResourceBundle resourceBundle;
    @JsonIgnore
    protected AdminUser adminUser;

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public ResourceBundle getResourceBundle() {
        if (this.resourceBundle == null) {
            this.resourceBundle = ResourceBundle.getBundle("messages");
        }
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
