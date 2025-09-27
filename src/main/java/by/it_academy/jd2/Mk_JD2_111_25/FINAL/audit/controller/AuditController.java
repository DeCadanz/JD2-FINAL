package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.Audit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.dto.PageOfAudit;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final IAuditService as;

    @GetMapping
    public ResponseEntity<PageOfAudit<Audit>> getAudit(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageOfAudit<Audit> result = as.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Audit> getAuditById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(as.getByUuid(uuid));
    }
}
