package com.teamtrace.axiseducation.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageStore {
    static ClassLoader loader;

    private MessageStore() {
    }

    public static final void initialize() throws MalformedURLException {
        File file = new File("templates/message");
        URL[] urls = new URL[]{file.toURI().toURL()};
        loader = new URLClassLoader(urls);
    }

    public static String get(String code) {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale, loader);
        try {
            return new String(rb.getString(code).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rb.getString(code);
    }

    public static String get(String code, Object... params) {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale, loader);
        try {
            return new String(rb.getString(code).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageFormat format = new MessageFormat(rb.getString(code));

        return format.format(params);

    }

    public static String get(String code, String lang, Object... params) {
        if (lang == null) {
            return get(code);
        }
        Locale locale = Locale.forLanguageTag(lang);
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale, loader);
        try {
            return new String(rb.getString(code).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageFormat format = new MessageFormat(rb.getString(code));

        return format.format(params);
    }
}
