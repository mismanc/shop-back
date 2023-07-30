package com.simple.webshop.services;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmailServiceIT {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"))
            .withPerMethodLifecycle(false);

    @Autowired
    EmailService emailService;

    @Test
    void shouldSendAdminOrderEmail() throws MessagingException {
        Map<String, String> result = emailService.adminOrderMail();
        assertEquals("true", result.get("success"));
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertEquals(1, receivedMessage.getAllRecipients().length);
        assertEquals("New Order", receivedMessage.getSubject());
    }

}
