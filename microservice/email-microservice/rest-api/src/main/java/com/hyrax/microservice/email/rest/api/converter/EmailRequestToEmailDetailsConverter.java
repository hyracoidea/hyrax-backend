package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.microservice.email.rest.api.domain.request.EmailRequest;
import com.hyrax.microservice.email.rest.api.template.FreeMarkerTemplateProcessor;
import com.hyrax.microservice.email.service.api.model.EmailDetails;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class EmailRequestToEmailDetailsConverter {

    private final FreeMarkerTemplateProcessor freeMarkerTemplateProcessor;

    public EmailDetails convert(final EmailRequest emailRequest) throws IOException, TemplateException {
        return EmailDetails.builder()
                .from(emailRequest.getFrom())
                .to(emailRequest.getTo())
                .subject(emailRequest.getSubject())
                .contentAsHtml(freeMarkerTemplateProcessor.generateHTMLContent(emailRequest.getTemplateName(), emailRequest.getModel()))
                .build();
    }
}
