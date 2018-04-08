package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.service.api.EmailService;
import com.hyrax.microservice.email.service.api.model.EmailDetail;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(final EmailDetail emailDetail) {
        try {
            final MimeMessageHelper helper = createMimeMessageHelper();

            helper.setTo(emailDetail.getRecipientEmailAddress());
            helper.setText(emailDetail.getContentAsHtml(), true);
            helper.setSubject(emailDetail.getSubject());
            helper.setFrom(emailDetail.getSenderEmailAddress());

            mailSender.send(helper.getMimeMessage());
        } catch (final MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private MimeMessageHelper createMimeMessageHelper() throws MessagingException {
        return new MimeMessageHelper(mailSender.createMimeMessage(),
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());
    }
}
