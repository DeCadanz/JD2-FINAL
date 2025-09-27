package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.controller;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.Currency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.OperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfCurrency;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto.PageOfOperationCategory;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api.ICategoryService;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.service.api.ICurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classifier")
@RequiredArgsConstructor
public class ClassifierController {

    private final ICurrencyService cs;
    private final ICategoryService os;

    @PostMapping("/currency")
    public ResponseEntity<String> addCurrency(@RequestBody Currency currency) {
        cs.add(currency);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/currency")
    public ResponseEntity<PageOfCurrency<Currency>> getAllCurrency(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageOfCurrency<Currency> result = cs.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/operation/category")
    public ResponseEntity<String> addCategory(@RequestBody OperationCategory category) {
        os.add(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/operation/category", produces = "application/json")
    public ResponseEntity<PageOfOperationCategory<OperationCategory>> getAllCategory(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageOfOperationCategory<OperationCategory> result = os.getAll(pageable);
        return ResponseEntity.ok(result);
    }
}