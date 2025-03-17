package com.memo.gymapi.handlerExceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
    private final String object;
    private final String message;
    private String field;
    private Object rejectedValue;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
