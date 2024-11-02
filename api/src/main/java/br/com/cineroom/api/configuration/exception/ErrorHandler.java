package br.com.cineroom.api.configuration.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError400(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ErrorDTO::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleBusinessRuleError(ValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        String field = "username or email";
        String message = "Username or email already exists.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(field, message));
    }

    private record ErrorDTO(String field, String message) {
        public ErrorDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
