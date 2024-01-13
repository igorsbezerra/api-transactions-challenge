package dev.igor.apitransactions.error.handler;

import dev.igor.apitransactions.error.AccountNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class AccountFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is4xxClientError()) {
            throw new AccountNotFoundException();
        }
        return null;
    }
}
