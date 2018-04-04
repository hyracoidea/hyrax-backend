package com.hyrax.microservice.email.rest.api.template;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class FreeMarkerTemplateProcessor {

    private static final String FREE_MARKER_TEMPLATE_FILE_SUFFIX = ".ftl";

    private final Configuration freemarkerConfig;

    public String generateHTMLContent(final String templateName, final Map<String, String> model) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(getTemplateFilename(templateName)), model);
    }

    private String getTemplateFilename(final String templateName) {
        return new StringBuilder().append(templateName).append(FREE_MARKER_TEMPLATE_FILE_SUFFIX).toString();
    }
}
