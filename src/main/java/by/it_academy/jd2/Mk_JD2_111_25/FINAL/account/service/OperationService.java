package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service;


import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.Operation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto.PageOfOperation;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.entity.OperationEntity;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api.IOperationRepository;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.service.api.IOperationService;
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
    private final IOperationRepository or;

    @Override
    @Transactional
    public void add(Operation operation, String uuid) {
        OperationEntity oe = new OperationEntity();
        String ouuid = UUID.randomUUID().toString();
        Long utime = Instant.now().getEpochSecond();
        oe.setUuid(ouuid);
        oe.setDtCreate(utime);
        oe.setDtUpdate(utime);
        oe.setDate(utime);
        oe.setDescription(operation.getDescription());
        oe.setCategory(operation.getCategory());
        oe.setValue(operation.getValue());
        oe.setCurrency(operation.getCurrency());
        oe.setAccount(uuid);
        or.save(oe);
    }

    @Override
    public PageOfOperation<Operation> getAll(Pageable pageable) {
        Page<OperationEntity> page = or.findAll(pageable);

        List<Operation> content = new ArrayList<>();
        for(OperationEntity oe : page.getContent()) {
            Operation operation = new Operation();
            operation.setUuid(oe.getUuid());
            operation.setDtCreate(oe.getDtCreate());
            operation.setDtUpdate(oe.getDtUpdate());
            operation.setDate(oe.getDate());
            operation.setDescription(oe.getDescription());
            operation.setValue(oe.getValue());
            operation.setCurrency(oe.getCurrency());
            content.add(operation);
        }
        Page<Operation> ap = new PageImpl<>(content, pageable, page.getTotalElements());
        return new PageOfOperation<>(ap);
    }

    @Transactional
    public void update(String auuid, String uuid, Long dtUpdate, Operation operation) {
        OperationEntity oe = or.findByUuid(uuid).orElseThrow();
        oe.setDate(operation.getDate());
        oe.setDescription(operation.getDescription());
        oe.setCategory(operation.getCategory());
        oe.setValue(operation.getValue());
        oe.setCurrency(operation.getCurrency());
        or.save(oe);
    }

    @Transactional
    public void delete(String auuid, String uuid, Long dtUpdate, Operation operation) {
        OperationEntity oe = or.findByUuid(uuid).orElseThrow();
        or.delete(oe);
    }
}
