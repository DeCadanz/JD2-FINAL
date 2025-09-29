package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse.FieldError;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.StructuredErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final IAuditService auditService;
    private final HttpServletRequest request;
    private static final Pattern USERS_GET_PATTERN = Pattern.compile("/users/[a-zA-Z0-9-]+$");
    private static final Pattern USERS_UPDATE_PATTERN = Pattern.compile("/users/[a-zA-Z0-9-]+/dt_update/[a-zA-Z0-9-_:]+$");
    private static final Pattern USERS_ENDING_PATTERN = Pattern.compile(".*\\/users$");
    private static final Pattern ACCOUNT_ENDING_PATTERN = Pattern.compile(".*\\/account$");

    @ExceptionHandler(MethodArgumentNotValidException.class) //400
    public ResponseEntity<StructuredErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> errorDetails = new ArrayList<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(new FieldError(error.getField(), error.getDefaultMessage()));
        }
        StructuredErrorResponse error = new StructuredErrorResponse(errorDetails);
        String requestUri = request.getRequestURI();

        Matcher usersUpdMatcher = USERS_UPDATE_PATTERN.matcher(requestUri);
        Matcher usersEndMatcher = USERS_ENDING_PATTERN.matcher(requestUri);
        Matcher accountEndMatcher = ACCOUNT_ENDING_PATTERN.matcher(requestUri);
        String uuid = getUuidFromContext();
        String text;
        EEssenceType type = EEssenceType.USER;
        if (requestUri.contains("/login")) {
            text = "Неудачная попытка входа (ошибка валидации JSON)";
        } else if (requestUri.contains("/registration")) {
            text = "Неудачная попытка регистрации (ошибка валидации JSON)";
        } else if (usersEndMatcher.matches()) {
            text = "Неудачная попытка создания (ошибка валидации JSON)";
        } else if (usersUpdMatcher.matches()) {
            text = "Неудачная попытка редактирования данных пользователя (ошибка валидации JSON)";
        } else if (requestUri.contains("/currency")) {
            text = "Неудачная попытка добавления валюты (ошибка валидации JSON)";
            type = EEssenceType.CURRENCY;
        } else if (requestUri.contains("/operation/category")) {
            text = "Неудачная попытка добавления категории (ошибка валидации JSON)";
            type = EEssenceType.CATEGORY;
        } else if (accountEndMatcher.matches()) {
            text = "Неудачная попытка добавления счёта (ошибка валидации JSON)";
            type = EEssenceType.ACCOUNT;
        } else {
            text = "Неудачная валидация (ошибка валидации JSON)";
        }

        auditService.add(createAudit(uuid, text, type, null));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse("Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

        String requestUri = request.getRequestURI();
        String text;
        if (requestUri.contains("/users")) {
            text = "Неудачная попытка создания (пользователь уже существует)";
        } else {
            text = "Неудачная попытка (а чего?)";
        }

        EEssenceType type = EEssenceType.USER;
        auditService.add(createAudit(null, text, type, null));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotVerifiedException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserNotVerified(UserNotVerifiedException ex) {
        ErrorResponse error = new ErrorResponse("Учётная запись не верифицирована");

        String text = "Неудачная попытка входа (пользователь не верифицирован)";
        EEssenceType type = EEssenceType.USER;
        auditService.add(createAudit(null, text, type, null));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CodeNotValidException.class) // 400
    public ResponseEntity<ErrorResponse> handleCodeNotValid(CodeNotValidException ex) {
        ErrorResponse error = new ErrorResponse("Неверный код верификации");

        String text = "Неудачная попытка верификации (неверный код)";
        EEssenceType type = EEssenceType.USER;
        auditService.add(createAudit(null, text, type, null));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class) // 400
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
        ErrorResponse error = new ErrorResponse("Неверные учётные данные");

        String text = "Неудачная попытка входа (неверный пароль)";
        EEssenceType type = EEssenceType.USER;
        auditService.add(createAudit(null, text, type, null));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("Неверные учётные данные");
        String requestUri = request.getRequestURI();
        Matcher getMatcher = USERS_GET_PATTERN.matcher(requestUri);
        Matcher updMatcher = USERS_UPDATE_PATTERN.matcher(requestUri);
        String uuid = getUuidFromContext();

        String text;

        EEssenceType type = EEssenceType.USER;
        if (requestUri.contains("/login")) {
            text = "Неудачная попытка входа (пользователь не найден)";
            uuid = null;
        } else if (requestUri.contains("/verification")) {
            text = "Неудачная попытка верификации (пользователь не найден)";
            uuid = null;
        } else if (requestUri.contains("/me")) {
            text = "Неудачная попытка просмотра информации о себе (пользователь не найден)";
        } else if (getMatcher.matches()) {
            text = "Неудачная попытка просмотра информации о пользователе (пользователь не найден)";
        } else if (updMatcher.matches()) {
            text = "Неудачная попытка редактирования данных пользователя (пользователь не найден)";
        } else {
            text = "Неудачная попытка (чего?)";
        }

        auditService.add(createAudit(uuid, text, type, null));

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AccountNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

        String uuid = getUuidFromContext();
        String text = "Неудачная попытка просмотра счёта (счёт не найден)";
        EEssenceType type = EEssenceType.ACCOUNT;
        auditService.add(createAudit(uuid, text, type, null));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(OperationNotFoundException.class) // 400
    public ResponseEntity<ErrorResponse> handleOperationNotFound(OperationNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

        String uuid = getUuidFromContext();
        String text = "Неудачная попытка просмотра операции (операция не найдена)";
        EEssenceType type = EEssenceType.OPERATION;
        auditService.add(createAudit(uuid, text, type, null));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class) // 400
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponse error = new ErrorResponse("Недостаточно средств на счету");

        String uuid = getUuidFromContext();
        String text = "Неудачная попытка (недостаточно средств на счету)";
        EEssenceType type = EEssenceType.ACCOUNT;
        auditService.add(createAudit(uuid, text, type, null));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BadCredentialsException.class) //401
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        String requestUri = request.getRequestURI();
        String text;
        if (requestUri.contains("/me")) {
            text = "Неудачная попытка просмотра страницы пользователя";
        } else {
            text = "Неудачная попытка (неверный токен)";
        }
        EEssenceType type = EEssenceType.USER;
        auditService.add(createAudit(null, text, type, null));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class) //403
    public ResponseEntity<ErrorResponse> handleBadToken(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class) //500
    public ResponseEntity<ErrorResponse> handleInternalErrors(Exception ex) {
        ErrorResponse error = new ErrorResponse("Сервер не смог корректно обработать запрос. Пожалуйста, обратитесь к администратору");
        return ResponseEntity.internalServerError().body(error);
    }

    private Audit createAudit(String userId, String text, EEssenceType type, String id) {
        Audit audit = new Audit();
        audit.setUserId(userId);
        audit.setText(text);
        audit.setType(type);
        audit.setId(id);
        return audit;
    }

    private String getUuidFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("getUUIDFromContext: " + auth.getName());
        return auth != null ? auth.getName() : null;
    }
}
