package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.service.api.EmailService;
import com.hyrax.microservice.email.service.api.model.EmailDetails;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(final EmailDetails emailDetails) throws MessagingException {
        final MimeMessageHelper helper = createMimeMessageHelper();

        helper.setTo(emailDetails.getTo());
        helper.setText(emailDetails.getContentAsHtml(), true);
        helper.setSubject(emailDetails.getSubject());
        helper.setFrom(emailDetails.getFrom());

        mailSender.send(helper.getMimeMessage());
    }

    private MimeMessageHelper createMimeMessageHelper() throws MessagingException {
        return new MimeMessageHelper(mailSender.createMimeMessage(),
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());
    }
}
