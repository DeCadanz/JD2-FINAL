package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse.FieldError;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //400
    public ResponseEntity<StructuredErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> errorDetails = new ArrayList<>();

        for (var error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(new FieldError(error.getField(), error.getDefaultMessage()));
        }

        StructuredErrorResponse error = new StructuredErrorResponse(errorDetails);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BadCredentialsException.class) //401
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class) //403
    public ResponseEntity<ErrorResponse> handleBadToken(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class) //500
    public ResponseEntity<ErrorResponse> handleInternalErrors(Exception ex) {
        ErrorResponse error = new ErrorResponse("бог в помощь, штош");
        return ResponseEntity.internalServerError().body(error);
    }
}
