package br.com.concrete.advice;

import br.com.concrete.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> catchException(Exception e) {
        return ResponseEntity.badRequest()
                .body(new Message(e.getMessage()));
    }
}
