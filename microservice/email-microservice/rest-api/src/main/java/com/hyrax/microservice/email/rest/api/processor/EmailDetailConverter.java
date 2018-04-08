package com.hyrax.microservice.email.rest.api.processor;

import com.hyrax.microservice.email.rest.api.template.FreeMarkerTemplateProcessor;
import com.hyrax.microservice.email.service.api.model.EmailDetail;
import com.hyrax.microservice.email.service.api.model.EmailNotificationDetail;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class EmailDetailConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDetailConverter.class);

    private final FreeMarkerTemplateProcessor freeMarkerTemplateProcessor;

    public EmailDetail convert(final EmailNotificationDetail emailNotificationDetail) {
        return EmailDetail.builder()
                .senderEmailAddress(emailNotificationDetail.getSenderEmailAddress())
                .recipientEmailAddress(emailNotificationDetail.getRecipientEmailAddress())
                .subject(emailNotificationDetail.getSubject())
                .contentAsHtml(generateHtmlContent(emailNotificationDetail))
                .build();
    }

    private String generateHtmlContent(final EmailNotificationDetail emailNotificationDetail) {
        try {
            return freeMarkerTemplateProcessor.generateHTMLContent(emailNotificationDetail.getBaseEventCategory(),
                    emailNotificationDetail.getSubEventCategory(), emailNotificationDetail.getModel());
        } catch (final IOException | TemplateException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
