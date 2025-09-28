package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse.FieldError;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final IAuditService auditService;

    @ExceptionHandler(MethodArgumentNotValidException.class) //400
    public ResponseEntity<StructuredErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        //saveValidationAudit(ex);
        List<FieldError> errorDetails = new ArrayList<>();

        for (var error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(new FieldError(error.getField(), error.getDefaultMessage()));
        }

        StructuredErrorResponse error = new StructuredErrorResponse(errorDetails);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse("ошыбка стоп юзер существуе");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotVerifiedException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserNotVerified(UserNotVerifiedException ex) {
        ErrorResponse error = new ErrorResponse("ошыбка стоп верифицыруйса слыш");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CodeNotValidException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserNotVerified(CodeNotValidException ex) {
        ErrorResponse error = new ErrorResponse("ошыбка стоп код козёл");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class) // 400
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
        ErrorResponse error = new ErrorResponse("ошыбка стоп");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("ошыбка стоп");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AccountNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("такого счёта здесь нет");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(OperationNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleOperationNotFound(OperationNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("такой операции здесь нет");
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

    private void saveValidationAudit(MethodArgumentNotValidException ex) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth != null ? auth.getName() : "anonymous";

        String actionText = "CATEGORY_ADD - FAILED (Validation error)";

        StringBuilder errorDetails = new StringBuilder();
        List<org.springframework.validation.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            errorDetails.append(fieldErrors.get(0).getField()).append(": ").append(fieldErrors.get(0).getDefaultMessage());
            if (fieldErrors.size() > 1) {
                errorDetails.append("; ").append(fieldErrors.get(1).getField()).append(": ").append(fieldErrors.get(1).getDefaultMessage());
            }
        }
        actionText += " (" + errorDetails + ")";

        EEssenceType type = EEssenceType.CATEGORY;

        Audit audit = new Audit();
        audit.setUuid(UUID.randomUUID().toString());
        audit.setDt_create(Instant.now().getEpochSecond());
        audit.setUserId(userId);
        audit.setText(actionText);
        audit.setType(type);
        audit.setId("N/A");

        try {
            auditService.add(audit);
            System.out.println("Validation audit saved: " + audit);  // Для дебага
        } catch (Exception auditEx) {
            System.err.println("Failed to save validation audit: " + auditEx.getMessage());
            auditEx.printStackTrace();
        }
    }
}
