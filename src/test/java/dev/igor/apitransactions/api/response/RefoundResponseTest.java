package dev.igor.apitransactions.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefoundResponseTest {
    @Test
    void getter_and_setter() {
        final var expectedRefound = "true";
        RefoundResponse response = new RefoundResponse();
        response.setRefound(expectedRefound);

        Assertions.assertEquals(expectedRefound, response.getRefound());

        RefoundResponse response2 = new RefoundResponse(expectedRefound);
        Assertions.assertEquals(expectedRefound, response2.getRefound());
    }
}
