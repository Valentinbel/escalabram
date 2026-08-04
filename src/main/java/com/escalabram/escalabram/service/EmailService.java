package com.escalabram.escalabram.service;

import com.escalabram.escalabram.email.model.EmailMessage;

public interface EmailService {

    void send(EmailMessage emailMessage);
}
