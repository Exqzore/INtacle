package com.exqzore.intacle.model.service;

public interface MailService {
    void sendMessage(String recipient, String text);

    String prepareUrl(String login, String activationCode);
}
