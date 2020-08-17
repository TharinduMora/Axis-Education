package com.teamtrace.axiseducation.util.notification.sms;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.util.notification.Sender;
import org.bson.Document;

import javax.ws.rs.client.Client;

public class SMSSenderFactory {
    private SMSSenderFactory() {
    }

    public static final void initialize(AxisEducationConfiguration configuration, Client client) {
        DefaultSMSSender.initialize(configuration, client);
    }

    public static Sender getSMSSender(Document smsConfig) {
        if (smsConfig == null) {
            return DefaultSMSSender.getInstance();
        }

        return DefaultSMSSender.getInstance();
    }
}
