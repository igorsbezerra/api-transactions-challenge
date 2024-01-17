package dev.igor.apitransactions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiTransactionsApplicationTests {

	@Autowired private ApiTransactionsApplication app;

	@Test
	void contextLoads() {
	}

	@Test
	public void applicationContextTest() {
    	Assertions.assertDoesNotThrow(() -> app.main(new String[] {}));
	}
}
