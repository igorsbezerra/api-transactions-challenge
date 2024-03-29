package dev.igor.apitransactions.integration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.igor.apitransactions.MockServerAPIContainer;
import dev.igor.apitransactions.RabbitMQContainer;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.repository.TransactionItemRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith({ RabbitMQContainer.class, MockServerAPIContainer.class })
public class TransactionIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private TransactionItemRepository repository;
    
    @Test
    void test() throws Exception {
        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withQueryStringParameters(List.of(Parameter.param("accountCode", "12345"), Parameter.param("amount", "100")))
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(createAvailableAccount())
        );

        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withPathParameter("accountCode", "12345")
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(responseJson())
        );

        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withPathParameter("id", "9769bf3a-b0b6-477a-9ff5-91f63010c9d3")
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(createNotification())
        );

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request())
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        MockServerAPIContainer.mockServerClient.reset();
    }

    @Test
    void test2() throws JsonProcessingException, Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestInvalid())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void test3() throws JsonProcessingException, Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestInvalidMethodArgument())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void test4() throws JsonProcessingException, Exception {

        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withQueryStringParameters(List.of(Parameter.param("accountCode", "12345"), Parameter.param("amount", "100")))
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(createUnavailableAccount())
        );

        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withPathParameter("accountCode", "12345")
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(responseJson())
        );

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        MockServerAPIContainer.mockServerClient.reset();
    }

    @Test
    void test5() throws JsonProcessingException, Exception {

        MockServerAPIContainer.mockServerClient.when(
            HttpRequest.request().withMethod("GET").withQueryStringParameters(List.of(Parameter.param("accountCode", "12345"), Parameter.param("amount", "100")))
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(404)
        );

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request())
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        MockServerAPIContainer.mockServerClient.reset();
    }

    @Test
    void test6() throws JsonProcessingException, Exception {
        TransactionItem transaction = repository.save(transactionItem());

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transactions/" + transaction.getTransaction().getId() + "/devolution")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request())
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        MockServerAPIContainer.mockServerClient.reset();
    }

    private String request() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("sourceAccount", "12345");
        objectNode.put("targetAccount", "54321");
        objectNode.put("amount", "100");
        return mapper.writeValueAsString(objectNode);
    }

    private String responseJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", "1");
        objectNode.put("accountCode", "12345");
        objectNode.put("userId", "1");
        return mapper.writeValueAsString(objectNode);
    }

    private String createNotification() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("messageSent", "true");
        return mapper.writeValueAsString(objectNode);
    }

    private String createAvailableAccount() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("available", "true");
        return mapper.writeValueAsString(objectNode);
    }

    private String createUnavailableAccount() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("available", "false");
        return mapper.writeValueAsString(objectNode);
    }

    private String requestInvalid() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("sourceAccount", "12345");
        objectNode.put("targetAccount", "12345");
        objectNode.put("amount", "100");
        return mapper.writeValueAsString(objectNode);
    }

    private String requestInvalidMethodArgument() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("sourceAccount", "1");
        objectNode.put("targetAccount", "1");
        objectNode.put("amount", "100");
        return mapper.writeValueAsString(objectNode);
    }

    private Transaction createTransaction() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDevolution = false;
        final var expectedCreatedAt = LocalDateTime.now().toString();
        return  new Transaction(expectedId, expectedDevolution, expectedCreatedAt);
    }

    private TransactionItem transactionItem() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "santander";
        final var expectedAmount = new BigDecimal("100");
        final var expectedTransactionType = TypeTransaction.INCOME;
        final var expectedTransaction = createTransaction();
        TransactionItem item = new TransactionItem();
        item.setId(expectedId);
        item.setSourceAccount(expectedSourceAccount);
        item.setTargetAccount(expectedTargetAccount);
        item.setAmount(expectedAmount);
        item.setType(expectedTransactionType);
        item.setTransaction(expectedTransaction);
        return item;
    }
}
