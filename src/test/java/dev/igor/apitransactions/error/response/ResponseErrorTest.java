package dev.igor.apitransactions.error.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseErrorTest {
    @Test
    void getter_and_setter() {
        final var expectedStatusCode = "400";
        final var expectedError = "err";
        final var expectedCode = "ErrorException";

        ResponseError err = new ResponseError();
        err.setStatusCode(expectedStatusCode);
        err.setError(expectedError);
        err.setCode(expectedCode);

        Assertions.assertEquals(expectedStatusCode, err.getStatusCode());
        Assertions.assertEquals(expectedError, err.getError());
        Assertions.assertEquals(expectedCode, err.getCode());

        ResponseError err2 = new ResponseError(expectedStatusCode, expectedError, expectedCode);

        Assertions.assertEquals(expectedStatusCode, err2.getStatusCode());
        Assertions.assertEquals(expectedError, err2.getError());
        Assertions.assertEquals(expectedCode, err2.getCode());
    }
}
