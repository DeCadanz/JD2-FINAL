package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.annotations.LogUserAction;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class UserActionLogger {

    private final IAuditService as;

    @Around("@annotation(logUserAction)")
    public Object logAction(ProceedingJoinPoint joinPoint, LogUserAction logUserAction) throws Throwable {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth != null ? auth.getName() : "anonymous";

        Object result = null;
        Exception exception = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            exception = e;
        }

        String status = (exception != null) ? "FAILED" : "SUCCESS";
        String actionText = logUserAction.actionText() + " - " + status;
        if (exception != null) {
            actionText += " (Error: " + exception.getMessage() + ")";
        }
        EEssenceType type = logUserAction.type();

        Audit audit = new Audit();
        audit.setUuid(UUID.randomUUID().toString());
        audit.setDt_create(Instant.now().getEpochSecond());
        audit.setUserId(userId);
        audit.setText(actionText);
        audit.setType(type);
        String entityId = (result != null) ? getEntityDescription(result) : "N/A";
        audit.setId(entityId);
        try {
            as.add(audit);
            System.out.println("Audit saved successfully: " + audit);
        } catch (Exception auditEx) {
            System.err.println("Audit save failed: " + auditEx.getMessage());
            auditEx.printStackTrace();
        }

        if (exception != null) {
            throw exception;
        }
        return result;
    }

    private String getEntityDescription(Object entity) {
        if (entity == null) return "null";
        if (entity instanceof String) return (String) entity;

        try {
            var getUuidMethod = entity.getClass().getMethod("getUuid");
            Object uuidObj = getUuidMethod.invoke(entity);
            if (uuidObj instanceof String && uuidObj != null) {
                return (String) uuidObj;
            } else if (uuidObj instanceof UUID && uuidObj != null) {
                return ((UUID) uuidObj).toString();
            }
        } catch (Exception ignored) {

        }

        return entity.toString();
    }
}
