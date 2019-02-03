package com.harmony.kindless.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author wuxii
 */
public class FeignConfig {

    @Bean
    public Feign feign(ObjectMapper objectMapper) {
        return Feign
                .builder()
                .decode404()
                .errorDecoder(new BizErrorDecoder(objectMapper))
                .build();
    }

    private static class BizErrorDecoder implements ErrorDecoder {

        private static final Logger log = LoggerFactory.getLogger(BizErrorDecoder.class);

        private ErrorDecoder delegate = new ErrorDecoder.Default();

        private ObjectMapper objectMapper;

        public BizErrorDecoder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                String responseText = IOUtils.toString(response.body().asReader());
                com.harmony.umbrella.web.Response bizResponse = objectMapper.readValue(responseText, com.harmony.umbrella.web.Response.class);
                if (!bizResponse.isOk()) {
                    log.error("response biz error. {}", bizResponse);
                }
            } catch (IOException e) {
                return e;
            }
            return delegate.decode(methodKey, response);
        }

    }

}
