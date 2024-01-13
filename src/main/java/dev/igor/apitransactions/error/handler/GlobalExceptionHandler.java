package dev.igor.apitransactions.error.handler;

import dev.igor.apitransactions.error.ActionsYourSelfException;
import dev.igor.apitransactions.error.response.ResponseError;
import dev.igor.apitransactions.error.response.ResponseErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ActionsYourSelfException.class)
    public ResponseEntity<ResponseError> handleActionsYourSelfException(ActionsYourSelfException ex) {
        return ResponseEntity.badRequest().body(
                new ResponseError(
                        HttpStatus.BAD_REQUEST.toString(),
                        ex.getMessage(),
                        ActionsYourSelfException.class.getSimpleName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorList> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(item -> {
            String fieldName = item.getField();
            String errorMessage = item.getDefaultMessage();
            errors.add(new Error(String.format("'%s' - %s", fieldName, errorMessage)));
        });
        return ResponseEntity.badRequest().body(new ResponseErrorList(
                HttpStatus.BAD_REQUEST.toString(),
                MethodArgumentNotValidException.class.getSimpleName(),
                errors
        ));
    }
}
