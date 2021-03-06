package com.iwaneez.stuffer.core.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        LOGGER.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
            message.setTo(to);
//            message.setFrom("ivan.harmady@systemaccess.org");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            LOGGER.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.warn("Email could not be sent to user '{}'", to, e);
            } else {
                LOGGER.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(String recipient, String subject, String templateName, Object mailData, Locale locale) {
        Context context = new Context(locale);
        context.setVariable("mailData", mailData);

        String content = templateEngine.process(templateName, context);
        sendEmail(recipient, subject, content, false, true);
    }

}