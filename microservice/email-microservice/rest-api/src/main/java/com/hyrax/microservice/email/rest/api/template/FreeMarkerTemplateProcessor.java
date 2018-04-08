package com.hyrax.microservice.email.rest.api.template;

import com.hyrax.microservice.email.service.api.checker.BaseEventCategory;
import com.hyrax.microservice.email.service.api.checker.SubEventCategory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class FreeMarkerTemplateProcessor {

    private static final String FREE_MARKER_TEMPLATE_FILE_PREFIX = "email";

    private static final String FREE_MARKER_TEMPLATE_FILE_SUFFIX = ".ftl";

    private static final String HYPHEN = "-";

    private final Configuration freemarkerConfig;

    public String generateHTMLContent(final BaseEventCategory baseEventCategory, final SubEventCategory subEventCategory, final Map<String, String> model)
            throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(getTemplateFilename(baseEventCategory, subEventCategory)), model);
    }

    private String getTemplateFilename(final BaseEventCategory baseEventCategory, final SubEventCategory subEventCategory) {
        return new StringBuilder()
                .append(baseEventCategory.getBaseTemplateName())
                .append(File.separator)
                .append(FREE_MARKER_TEMPLATE_FILE_PREFIX)
                .append(HYPHEN)
                .append(baseEventCategory.getBaseTemplateName())
                .append(HYPHEN)
                .append(subEventCategory.getSubTemplateName())
                .append(FREE_MARKER_TEMPLATE_FILE_SUFFIX)
                .toString();
    }
}
