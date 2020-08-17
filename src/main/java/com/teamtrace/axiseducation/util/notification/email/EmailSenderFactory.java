package com.teamtrace.axiseducation.util.notification.email;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;
import org.bson.Document;

import javax.ws.rs.client.Client;

public class EmailSenderFactory {

    private EmailSenderFactory() {
    }

    public static final void initialize(AxisEducationConfiguration configuration, Client client) {
        DefaultEmailSender.initialize(configuration, client);
        SMTPEmailSender.initialize(configuration, client);
    }

    public static final Sender getEmailSender(Document emailConfig) {
        if (emailConfig == null) {
            return DefaultEmailSender.getInstance();
        }
        if ("SMTP".equalsIgnoreCase(emailConfig.getString("senderType"))) {
            return SMTPEmailSender.getInstance();
        }

        return DefaultEmailSender.getInstance();
    }

}
