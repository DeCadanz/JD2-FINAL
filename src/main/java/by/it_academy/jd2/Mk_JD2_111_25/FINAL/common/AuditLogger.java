package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.ITokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class AuditLogger {

    private final IAuditService auditService;
    private final ITokenService tokenService;

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller.CabinetController.login(..))", returning = "result")
    public void logSuccessfulLogin(JoinPoint joinPoint, Object result) {
        String userId = extractUuidFromResponse(result);
        String text = "Успешный вход пользователя";
        EEssenceType type = EEssenceType.USER;
        String id = userId;
        Audit audit = createAudit(userId, text, type, id);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.CabinetService.verify" +
                    "(String, String))", returning = "result")
    public void logSuccessfulVerification(JoinPoint joinPoint, Object result) {
            String text = "Успешная верификация пользователя";
            Audit audit = createAudit(null, text, EEssenceType.USER, null);
            auditService.add(audit);
            }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller.CabinetController.me(..))", returning = "result")
    public void logGetUserPage(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        String text = "Успешный запрос информации о себе";
        EEssenceType type = EEssenceType.USER;
        Audit audit = createAudit(userId, text, type, userId);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(public String by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.UserService.add" +
                    "(by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister, boolean))", returning = "result")
    public void logSuccessfulAdd(JoinPoint joinPoint, String result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String text = "Успешное создание пользователя";
        if ((boolean) args[1]) {
            text = "Успешная самостоятельная регистрация пользователя";
        }
        Audit audit = createAudit(userId, text, EEssenceType.USER, result);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller.UsersController.get(..))", returning = "result")
    public void logGetUserInfo(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String id = (String) args[0];
        String text = "Успешный запрос информации о пользователе";
        EEssenceType type = EEssenceType.USER;
        Audit audit = createAudit(userId, text, type, id);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.controller.UsersController.getPage(..))", returning = "result")
    public void logGetUsersPage(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String text = "Успешный запрос страницы пользователей";
        EEssenceType type = EEssenceType.USER;
        Audit audit = createAudit(userId, text, type, null);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(public String by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.UserService.update" +
                    "(String, Long, by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserRegister))", returning = "result")
    public void logSuccessfulEditUser(JoinPoint joinPoint, String result) {
        String userId = getUuidFromContext();
        String text = "Успешное обновление данных пользователя";
        Audit audit = createAudit(userId, text, EEssenceType.USER, result);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(public String by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.CurrencyService.add" +
                    "(by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.Currency))", returning = "result")
    public void logSuccessfulAddCurrency(JoinPoint joinPoint, String result) {
        String userId = getUuidFromContext();
        String text = "Успешное добавление валюты";
        Audit audit = createAudit(userId, text, EEssenceType.CURRENCY, result);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(public String by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.CategoryService.add" +
                    "(by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.OperationCategory))", returning = "result")
    public void logSuccessfulAddCategory(JoinPoint joinPoint, String result) {
        String userId = getUuidFromContext();
        String text = "Успешное добавление категории операций";
        System.out.println(text);
        Audit audit = createAudit(userId, text, EEssenceType.CATEGORY, result);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.controller.ClassifierController.getAllCurrency(..))", returning = "result")
    public void logGetCurrencyPage(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String text = "Успешный запрос страницы валют";
        EEssenceType type = EEssenceType.CURRENCY;
        Audit audit = createAudit(userId, text, type, null);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.controller.ClassifierController.getAllCategory(..))", returning = "result")
    public void logGetCategoryPage(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String text = "Успешный запрос страницы категорий";
        EEssenceType type = EEssenceType.CURRENCY;
        Audit audit = createAudit(userId, text, type, null);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(public String by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.AccountService.add" +
                    "(by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account, String))", returning = "result")
    public void logSuccessfulAddAccount(JoinPoint joinPoint, String result) {
        String userId = getUuidFromContext();
        String text = "Успешное добавление счёта";
        Audit audit = createAudit(userId, text, EEssenceType.ACCOUNT, result);
        auditService.add(audit);
    }

    @AfterReturning(pointcut =
            "execution(* by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.controller.AccountController.getAccounts(..))", returning = "result")
    public void logGetAccountsPage(JoinPoint joinPoint, Object result) {
        String userId = getUuidFromContext();
        String text = "Успешный запрос страницы счетов пользователя";
        EEssenceType type = EEssenceType.ACCOUNT;
        Audit audit = createAudit(userId, text, type, null);
        auditService.add(audit);
    }

    @AfterReturning(pointcut = "execution(public by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Account " +
            "by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.AccountService.getByUuid(String))",
    returning = "result")
    public void logSuccessfulGetAccount(JoinPoint joinPoint, Account result) {
        String userId = getUuidFromContext();
        Object[] args = joinPoint.getArgs();
        String uuid = (String) args[0];
        String text = "Успешный запрос страницы счёта";
        Audit audit = createAudit(userId, text, EEssenceType.ACCOUNT, uuid);
        auditService.add(audit);
    }

    private Audit createAudit(String userId, String text, EEssenceType type, String id) {
        Audit audit = new Audit();
        audit.setUserId(userId);
        audit.setText(text);
        audit.setType(type);
        audit.setEntityId(id);
        return audit;
    }

    private String extractUuidFromResponse(Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> response = (ResponseEntity<?>) result;
            String authHeader = response.getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = tokenService.extractClaims(token);
                return claims.getSubject();
            }
        }
        return null;
    }

    private String getUuidFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }
}

