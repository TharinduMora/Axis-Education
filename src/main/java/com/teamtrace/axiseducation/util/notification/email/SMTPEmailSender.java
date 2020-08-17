package com.teamtrace.axiseducation.util.notification.email;

import com.sun.mail.util.MailConnectException;
import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.model.Notification;
import com.teamtrace.axiseducation.model.NotificationConfiguration;
import com.teamtrace.axiseducation.util.constant.Statuses;
import com.teamtrace.axiseducation.util.notification.Sender;
import org.bson.Document;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class SMTPEmailSender implements Sender {
    private static SMTPEmailSender SMTP_EMAIL_SENDER = new SMTPEmailSender();

    private SMTPEmailSender() {
    }

    public static Sender getInstance() {
        return SMTP_EMAIL_SENDER;
    }

    public static void initialize(AxisEducationConfiguration config, Client clientRef) {
    }

    @Override
    public boolean adminForgotPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        Document emailConfig = notificationConfig.getEmailConfig();
        if (emailConfig == null) {
            return false;
        }
        notification.setEmailFrom(emailConfig.getString("smtpFrom"));
        notification.setEmailTo(notification.getAdminUser().getEmail());
        notification.setEmailSubject("Forgot Password.");
        notification.setEmailBody(EmailMessages.adminForgotPassword(notification, emailConfig));

        return sendEmail(notification, emailConfig);
    }

    @Override
    public boolean adminTemporaryPassword(Notification notification, NotificationConfiguration notificationConfig, EntityManager em) {
        Document emailConfig = notificationConfig.getEmailConfig();
        if (emailConfig == null) {
            return false;
        }
        notification.setEmailFrom(emailConfig.getString("smtpFrom"));
        notification.setEmailTo(notification.getAdminUser().getEmail());
        notification.setEmailSubject("Temporary Password.");
        notification.setEmailBody(EmailMessages.adminTemporaryPassword(notification, emailConfig));

        return sendEmail(notification, emailConfig);
    }


    private boolean sendEmail(Notification notification, Document emailConfig) {
        if (notification.getEmailTo() == null) {
            return false;
        }
        Properties properties;
        String username, password;

        properties = new Properties();
        properties.put("mail.smtp.host", emailConfig.getString("smtpHost"));
        properties.put("mail.smtp.port", emailConfig.getInteger("smtpPort"));
        properties.put("mail.from", emailConfig.getString("smtpFrom"));
        properties.put("mail.smtp.auth", emailConfig.getBoolean("smtpAuth"));
        properties.put("mail.smtp.connectiontimeout", 5000);
        properties.put("mail.smtp.timeout", 5000);

        if (emailConfig.getBoolean("startTLSEnabled")) {
            properties.put("mail.smtp.starttls.enable", true);
        } else {
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.socketFactory.port", emailConfig.getInteger("smtpPort"));
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
        }
        username = emailConfig.getString("smtpUsername");
        password = emailConfig.getString("smtpPassword");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        notification.setDate(new Date());

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(notification.getEmailFrom(),
                    emailConfig.getString("shopName")));
            String[] emailAddress = notification.getEmailTo().split(",");
            InternetAddress[] address = new InternetAddress[emailAddress.length];
            for (int i = 0; i < emailAddress.length; i++) {
                address[i] = new InternetAddress(emailAddress[i]);
            }
            message.setRecipients(Message.RecipientType.TO, address);
            //cc
            if (notification.getEmailCc() != null && notification.getEmailCc().length() > 5) {
                emailAddress = notification.getEmailCc().split(",");
                address = new InternetAddress[emailAddress.length];
                for (int i = 0; i < emailAddress.length; i++) {
                    address[i] = new InternetAddress(emailAddress[i]);
                }
                message.setRecipients(Message.RecipientType.CC, address);
            }
            //bcc
            if (notification.getEmailBcc() != null && notification.getEmailBcc().length() > 5) {
                emailAddress = notification.getEmailBcc().split(",");
                address = new InternetAddress[emailAddress.length];
                for (int i = 0; i < emailAddress.length; i++) {
                    address[i] = new InternetAddress(emailAddress[i]);
                }
                message.setRecipients(Message.RecipientType.BCC, address);
            }

            message.setSubject(notification.getEmailSubject());
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(notification.getEmailBody(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (notification.getAttachment() != null) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(notification.getAttachment(),
                        "application/octet-stream");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(notification.getFilename());
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            //Transport.send(message);
            boolean success = sendWithRetry(message, 3);
            if (success) notification.setEmailStatus(Statuses.APPROVED);

            return success;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean sendWithRetry(MimeMessage message, int count) throws MessagingException {
        if (count < 1) {
            return false;
        }
        try {
            Transport.send(message);
            return true;
        } catch (MailConnectException e) {
            count--;
            return sendWithRetry(message, count);
        }
    }

}
