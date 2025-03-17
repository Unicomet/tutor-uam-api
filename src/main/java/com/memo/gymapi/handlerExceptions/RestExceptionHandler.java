package com.memo.gymapi.handlerExceptions;

import com.memo.gymapi.handlerExceptions.errors.ApiError;
import com.memo.gymapi.handlerExceptions.errors.ApiSubError;
import com.memo.gymapi.handlerExceptions.errors.ApiValidationError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {


    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    //other exception handlers below
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        List<ApiSubError> subErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            subErrors.add(new ApiValidationError(
                    error.getObjectName(),
                    error.getDefaultMessage(),
                    error.getCodes()[0],
                    error.getArguments()[0]
            ));
        });
        apiError.setSubErrors(subErrors);
        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        List<ProblemDetail> details = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> new ProblemDetail(error.getField(), error.getDefaultMessage()))
//                .collect(Collectors.toList());
//        return new ProblemDetail("Validation error", details);
//    }

}