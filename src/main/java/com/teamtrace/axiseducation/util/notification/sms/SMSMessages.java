package com.teamtrace.axiseducation.util.notification.sms;

import com.teamtrace.axiseducation.model.Notification;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.bson.Document;

import java.io.StringWriter;
import java.text.DecimalFormat;

public class SMSMessages {
    static VelocityEngine ve;
    static DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

    static {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "templates/sms/");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    private SMSMessages() {

    }
}
