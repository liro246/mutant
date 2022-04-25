package co.com.meli.api.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;

@Log
@ControllerAdvice
public class CustomExceptionHandler {

    ErrorResponse result;

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> webExchangeBindException(WebExchangeBindException e) {
        log.severe("WebExchangeBindException: " + e.getMessage());
        result = new ErrorResponse(LocalDateTime.now(), "Exception: Invalid Request", e.getStatus().value(), e.getStatus().name());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.severe("Exception: " + e.getMessage());
        result = new ErrorResponse(LocalDateTime.now(), "Exception: An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorResponse {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime localDateTime;
        private String data;
        private int responseCode;
        private String status;
    }
}