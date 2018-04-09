package com.spring.com.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class UserFallbackProvider implements FallbackProvider {
    private final Logger LOGGER=LoggerFactory.getLogger(UserFallbackProvider.class);
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                LOGGER.info("报错原因:{}",cause.getMessage());
                return new ByteArrayInputStream("用户服务不可用，请稍后再试".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers=new HttpHeaders();
                MediaType mt=new MediaType("application","json",Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }

    @Override
    public String getRoute() {
        return "microservice-provider-user";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
       return null;
    }
}
