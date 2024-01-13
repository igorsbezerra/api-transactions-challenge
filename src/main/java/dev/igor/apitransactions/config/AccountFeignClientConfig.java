package dev.igor.apitransactions.config;

import dev.igor.apitransactions.error.handler.AccountFeignError;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class AccountFeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new AccountFeignError();
    }
}
