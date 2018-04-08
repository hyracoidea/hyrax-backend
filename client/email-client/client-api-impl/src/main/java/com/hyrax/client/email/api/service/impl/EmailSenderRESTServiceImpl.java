package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.request.EmailNotificationRequest;
import com.hyrax.client.email.api.service.EmailSenderRESTService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.Objects;

@AllArgsConstructor
public class EmailSenderRESTServiceImpl implements EmailSenderRESTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderRESTServiceImpl.class);

    private static final String ERROR_MESSAGE_REST_CALL = "Unexpected exception happened under the REST call";

    private final EmailSenderRESTClient emailSenderRESTClient;

    @Override
    public void sendEmail(EmailNotificationRequest emailNotificationRequest) {
        Response response = null;
        try {
            response = emailSenderRESTClient.callEmailSenderRESTEndpoint(emailNotificationRequest);
        } catch (final Exception e) {
            LOGGER.error(ERROR_MESSAGE_REST_CALL, e);
        } finally {
            if (Objects.nonNull(response)) {
                response.readEntity(String.class);
                response.close();
            }
        }
    }
}
