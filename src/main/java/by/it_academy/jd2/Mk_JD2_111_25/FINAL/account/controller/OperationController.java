package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.controller;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Operation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfOperation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class OperationController {

    private final IOperationService operationService;

    @PostMapping("/{uuid}/operation")
    public ResponseEntity<String> createOperation(@PathVariable("uuid") String uuid, @Valid @RequestBody Operation operation) {
        operationService.add(operation, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}/operation")
    public ResponseEntity<PageOfOperation<Operation>> getOperations(@PathVariable("uuid") String uuid, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageOfOperation<Operation> result = operationService.getOperationsPage(pageable, uuid);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    public ResponseEntity<Operation> updateOperation(@PathVariable("uuid") String auuid, @PathVariable("uuid_operation") String uuid, @PathVariable("dt_update") Long dtUpdate, @Valid @RequestBody Operation operation) {
        operationService.update(auuid, uuid, dtUpdate, operation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    public ResponseEntity<Operation> deleteOperation(@PathVariable("uuid") String auuid, @PathVariable("uuid_operation") String uuid, @PathVariable("dt_update") Long dtUpdate, @Valid @RequestBody Operation operation) {
        operationService.delete(auuid, uuid, dtUpdate, operation);
        return ResponseEntity.ok().build();
    }
}
