package dev.igor.apitransactions.error.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorList {
    private String statusCode;
    private String code;
    private List<Error> errors;
}
