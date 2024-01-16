package dev.igor.apitransactions.error.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ErrorTest {
    @Test
    @DisplayName("Getter and Setter - [Error]")
    void test_getter_and_setter() {
        final var expectedError = "error";

        Error err = new Error();
        err.setError(expectedError);

        Assertions.assertEquals(expectedError, err.getError());
    }
}
