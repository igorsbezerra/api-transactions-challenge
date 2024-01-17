package dev.igor.apitransactions.error.response;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResponseErrorListTest {
    @Test
    void getter_and_setter() {
        final var expectedStatusCode = "400";
        final var expectedCode = "ErrorException";
        final var expectedErrors = List.of(new Error("err"));

        ResponseErrorList response = new ResponseErrorList(expectedStatusCode, expectedCode, expectedErrors);

        Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
        Assertions.assertEquals(expectedCode, response.getCode());
        Assertions.assertEquals(expectedErrors, response.getErrors());

        ResponseErrorList response2 = new ResponseErrorList();
        response2.setStatusCode(expectedStatusCode);
        response2.setCode(expectedCode);
        response2.setErrors(expectedErrors);

        Assertions.assertEquals(expectedStatusCode, response2.getStatusCode());
        Assertions.assertEquals(expectedCode, response2.getCode());
        Assertions.assertEquals(expectedErrors, response2.getErrors());
    }
}
