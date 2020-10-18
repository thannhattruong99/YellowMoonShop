/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author truongtn
 */
public class MyToys {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String HOST_EMAIL = "funny1demo@gmail.com";
    public static final String PASSWORD = "Funny_Demo";
//    public static final String HOST = "localhost";

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        try {
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
        } catch (Exception e) {
            return null;
        }
        return hexString.toString();
    }

    public static Date getCurrentDate(String format) throws ParseException {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        date = sdf.parse(dateStr);

        return date;

    }

    public static String getCurrentDateStr(String format) throws ParseException {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean isNullOrEmpty(String input) {
        return (input == null || input.trim().isEmpty());
    }

    public List<String> getResourceBundleList(String path) {
        List<String> list = new ArrayList<>();
        ResourceBundle rb = ResourceBundle.getBundle(path);
        Enumeration<String> key = rb.getKeys();
        while (key.hasMoreElements()) {
            list.add(key.nextElement());
        }
        return list;
    }

    public HashMap readResourceBundleHashMap(String path) throws MissingResourceException, NullPointerException {
        HashMap<String, String> hashMap = null;
        ResourceBundle rb = ResourceBundle.getBundle(path);
        Enumeration<String> key = rb.getKeys();
        if (hashMap == null) {
            hashMap = new HashMap<>();
            while (key.hasMoreElements()) {
                String servletKey = key.nextElement();
                String servletVaue = rb.getString(servletKey);
                hashMap.put(servletKey, servletVaue);
            }
        }
        return hashMap;
    }

    public static boolean checkStringLength(String input, int minLength, int maxLength) {
        return (input.trim().length() >= minLength && input.trim().length() <= maxLength);
    }

    public static boolean sendEmail(String to, String messageStr) throws MessagingException {

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HOST_EMAIL, PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(HOST_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("ping");
        message.setText(messageStr);

        Transport.send(message);
        return true;

    }

    public static String getRandomUID() {
        String uid = UUID.randomUUID().toString();
        return uid;
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    public static void main(String[] args) {
//
//    }

}
