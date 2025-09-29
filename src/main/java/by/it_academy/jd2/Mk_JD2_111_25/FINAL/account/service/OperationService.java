package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Operation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfOperation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.OperationEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api.IOperationRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IAccountService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IOperationService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.exceptions.OperationNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationService implements IOperationService {
    private final IOperationRepository operationRepository;
    private final IAccountService accountService;

    @Override
    @Transactional
    public void add(Operation operation, String uuid) {
        accountService.checkByUuid(uuid);
        OperationEntity operationEntity = new OperationEntity();
        String oUuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        operationEntity.setUuid(oUuid);
        operationEntity.setDtCreate(utime);
        operationEntity.setDtUpdate(utime);
        operationEntity.setDate(utime);
        operationEntity.setDescription(operation.getDescription());
        operationEntity.setCategory(operation.getCategory());
        operationEntity.setValue(operation.getValue());
        operationEntity.setCurrency(operation.getCurrency());
        operationEntity.setAccount(uuid);
        accountService.updateBalance(uuid, operation.getValue());
        operationRepository.save(operationEntity);
    }

    @Override
    public PageOfOperation<Operation> getOperationsPage(Pageable pageable, String uuid) {
        accountService.checkByUuid(uuid);
        Page<OperationEntity> page = operationRepository.findByAccount(pageable, uuid);
        List<Operation> content = new ArrayList<>();
        for(OperationEntity operationEntity : page.getContent()) {
            Operation operation = new Operation();
            operation.setUuid(operationEntity.getUuid());
            operation.setDtCreate(operationEntity.getDtCreate());
            operation.setDtUpdate(operationEntity.getDtUpdate());
            operation.setDate(operationEntity.getDate());
            operation.setDescription(operationEntity.getDescription());
            operation.setValue(operationEntity.getValue());
            operation.setCurrency(operationEntity.getCurrency());
            content.add(operation);
        }
        Page<Operation> pageOfOperation = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfOperation<>(pageOfOperation);
    }

    @Transactional
    public void update(String aUuid, String uuid, Long dtUpdate, Operation operation) {
        accountService.checkByUuid(aUuid);
        OperationEntity operationEntity = operationRepository.findById(uuid)
                .orElseThrow(() -> new OperationNotFoundException());
        operationEntity.setDate(operation.getDate());
        operationEntity.setDescription(operation.getDescription());
        operationEntity.setCategory(operation.getCategory());
        operationEntity.setValue(operation.getValue());
        operationEntity.setCurrency(operation.getCurrency());
        operationRepository.save(operationEntity);
    }

    @Transactional
    public void delete(String aUuid, String uuid, Long dtUpdate, Operation operation) {
        accountService.checkByUuid(aUuid);
        OperationEntity operationEntity = operationRepository.findById(uuid)
                .orElseThrow(() -> new OperationNotFoundException());
        operationRepository.delete(operationEntity);
    }
}
