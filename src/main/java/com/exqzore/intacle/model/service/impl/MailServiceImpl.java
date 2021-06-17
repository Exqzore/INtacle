package com.exqzore.intacle.model.service.impl;

import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.exception.PropertyReaderException;
import com.exqzore.intacle.model.service.FileService;
import com.exqzore.intacle.model.service.MailService;
import com.exqzore.intacle.util.PropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger();

    private static final MailService instance = new MailServiceImpl();

    private static final Properties properties;
    private final static String EMAIL_PROPERTY_PATH = "properties/email.properties";
    private final static String SENDER_KEY = "sender";
    private final static String PASSWORD_KEY = "password";
    private final static String SUBJECT_KEY = "subject";
    private final static String CONTEXT_PATH_KEY = "contextPath";
    private final static String MESSAGE_KEY = "message";
    private final static String SUBJECT;
    private final static String CONTEXT_PATH;
    private final static String MESSAGE;

    static {
        try {
            properties = PropertyReader.read(EMAIL_PROPERTY_PATH);
            SUBJECT = properties.getProperty(SUBJECT_KEY);
            CONTEXT_PATH = properties.getProperty(CONTEXT_PATH_KEY);
            MESSAGE = properties.getProperty(MESSAGE_KEY);
        } catch (PropertyReaderException exception) {
            logger.log(Level.ERROR, "Property read error", exception);
            throw new RuntimeException(exception);
        }
    }

    private final static Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(properties.getProperty(SENDER_KEY), properties.getProperty(PASSWORD_KEY));
        }
    });

    private MailServiceImpl() {
    }

    public static MailService getInstance() {
        return instance;
    }

    @Override
    public void sendMessage(String recipient, String text) {
        Message message = encapsulateMessage(recipient, text);
        try {
            if (message != null) {
                Transport.send(message);
                logger.log(Level.INFO, "Message sent successfully to '{}'", recipient);
            }
        } catch (MessagingException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
    }

    private static Message encapsulateMessage(String recipient, String url) {
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty(SENDER_KEY)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setContent(String.format(MESSAGE, url, url), "text/html");
            message.setSubject(SUBJECT);
            return message;
        } catch (MessagingException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
        return message;
    }

    @Override
    public String prepareUrl(String login, String activationCode) {
        return String.format(CONTEXT_PATH + WebPageRequest.USER_ACTIVATION, login, activationCode);
    }
}
