package com.teamtrace.axiseducation.util.notification.cm;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;
import org.bson.Document;

import javax.ws.rs.client.Client;

public class CMSenderFactory {
    private CMSenderFactory() {
    }

    public static final void initialize(AxisEducationConfiguration configuration, Client client) {
        //initialize each sender here
        DefaultCMSender.initialize(configuration, client);
        FirebaseCMSender.initialize(configuration, client);
    }

    public static Sender getCMSender(Document fcmConfig) {
        if (fcmConfig == null) {
            return DefaultCMSender.getInstance();
        }
        if ("FIREBASE".equalsIgnoreCase(fcmConfig.getString("senderType"))) {
            return FirebaseCMSender.getInstance();
        }

        return DefaultCMSender.getInstance();
    }
}
