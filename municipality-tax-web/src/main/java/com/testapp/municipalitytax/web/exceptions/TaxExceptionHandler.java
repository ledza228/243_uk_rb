package com.testapp.municipalitytax.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class TaxExceptionHandler {


    private final static Map<String, String> paramNameToError = Map.of(
            "tax", "value must be 0 <= x <= 1"
    );

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex){
        List<String> errorFieldNames = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            errorFieldNames.add(fieldName);
        });

        StringBuilder errorMessage = new StringBuilder();
        for (String error: errorFieldNames){
            String description = paramNameToError.get(error);
            if (description == null){
                errorMessage.append(error).append(" ");
            }
            else {
                errorMessage.append(error).append(": ").append(description).append(" ");
            }
        }

        ApiError apiError = new ApiError(
                Timestamp.from(Instant.now()),
                "B001",
                errorMessage.toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiError);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiError> handleDateTimeParseException(DateTimeParseException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        Timestamp.from(Instant.now()),
                        "B002",
                        "date should be yyyy.MM.dd"
                ));
    }

    @ExceptionHandler(ScheduleParsingException.class)
    public ResponseEntity<ApiError> handleScheduleParsingException(ScheduleParsingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        Timestamp.from(Instant.now()),
                        "B003",
                        ex.getMessage()
                ));
    }

}
