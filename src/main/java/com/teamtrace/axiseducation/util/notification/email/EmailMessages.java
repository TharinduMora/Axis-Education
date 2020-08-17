package com.teamtrace.axiseducation.util.notification.email;

import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.model.Notification;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.bson.Document;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailMessages {
    static VelocityEngine ve;
    static SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd, YYYY hh:mm aa");
    static DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

    static {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "templates/email/");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    public static String adminRegistration(Notification notification, Document emailConfig) {
        VelocityContext context = new VelocityContext();
        AdminUser admin = notification.getAdminUser();
        context.put("firstName", admin.getFullName());
        context.put("loginName", admin.getLoginName());
        context.put("password", admin.getPwd());
        context.put("type", 1);//registration
        context.put("systemDate", df.format(new Date()));

        StringWriter writer = new StringWriter();
        String templatePath = "";
        if (emailConfig.containsKey("templatePath")) {
            templatePath += emailConfig.getString("templatePath") + "/";
        }
        Template template = ve.getTemplate(templatePath + "admin_registration.vm");
        template.merge(context, writer);

        return writer.toString();
    }

    public static String adminForgotPassword(Notification notification, Document emailConfig) {
        VelocityContext context = new VelocityContext();
        AdminUser admin = notification.getAdminUser();
        context.put("firstName", admin.getFullName());
        context.put("loginName", admin.getLoginName());
        context.put("type", 2);//forgot password
        context.put("systemDate", df.format(new Date()));

        StringWriter writer = new StringWriter();
        String templatePath = "";
        if (emailConfig.containsKey("templatePath")) {
            templatePath += emailConfig.getString("templatePath") + "/";
        }
        Template template = ve.getTemplate(templatePath + "admin_forgot_password.vm");
        template.merge(context, writer);

        return writer.toString();
    }

    public static String adminTemporaryPassword(Notification notification, Document emailConfig) {
        VelocityContext context = new VelocityContext();
        AdminUser admin = notification.getAdminUser();
        context.put("firstName", admin.getFullName());
        context.put("password", admin.getPwd());

        StringWriter writer = new StringWriter();
        String templatePath = "";
        if (emailConfig.containsKey("templatePath")) {
            templatePath += emailConfig.getString("templatePath") + "/";
        }
        Template template = ve.getTemplate(templatePath + "admin_temporary_password.vm");
        template.merge(context, writer);

        return writer.toString();
    }
}
