package com.exqzore.intacle.model.service;

import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.exception.PropertyReaderException;
import com.exqzore.intacle.util.PropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {
    private static final Logger logger = LogManager.getLogger();

    private static final Properties properties;
    private final static String EMAIL_PROPERTY_PATH = "properties/email.properties";
    private final static String SENDER = "sender";
    private final static String PASSWORD = "password";
    private final static String SUBJECT = "Account activation";
    private final static String CONTEXT_PATH = "http://localhost:8080/intacle/";
    private final static String MESSAGE = """
            <div>
             	<div><b>INtacle</b></div>
             	<div>
             		This mailbox was specified as an account activation mailbox.<br>To activate your account, follow the link below:
             		<a href="%s" class="link">%s</a><br>
             		If you have not created an account with this mailbox, ignore this letter.
             	</div>
            </div>
            """;

    static {
        try {
            properties = PropertyReader.read(EMAIL_PROPERTY_PATH);
        } catch (PropertyReaderException exception) {
            logger.log(Level.ERROR, "Property read error", exception);
            throw new RuntimeException(exception);
        }
    }

    private final static Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(properties.getProperty(SENDER), properties.getProperty(PASSWORD));
        }
    });

    private MailService() {
    }

    public static void sendMessage(String recipient, String text) {
        Message message = encapsulateMessage(recipient, text);
        try {
            if (message != null) {
                Transport.send(message);
                logger.log(Level.INFO, "Message sent successfully to '{}'", properties.getProperty(SENDER));
            }
        } catch (MessagingException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
    }

    private static Message encapsulateMessage(String recipient, String url) {
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty(SENDER)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setContent(String.format(MESSAGE, url, url), "text/html");
            message.setSubject(SUBJECT);
            return message;
        } catch (MessagingException exception) {
            logger.log(Level.ERROR, exception.getMessage());
        }
        return message;
    }

    public static String prepareUrl(String login, String activationCode) {
        return String.format(CONTEXT_PATH + WebPageRequest.USER_ACTIVATION, login, activationCode);
    }
}
