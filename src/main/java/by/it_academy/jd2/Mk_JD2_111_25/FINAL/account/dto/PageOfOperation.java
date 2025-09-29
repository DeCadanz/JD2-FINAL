package by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Getter
public class PageOfOperation<T> {
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private List<Operation> content;

    public PageOfOperation(Page<Operation> page) {
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.first = page.isFirst();
        this.numberOfElements = page.getNumberOfElements();
        this.last = page.isLast();
        this.content = page.getContent();
    }
}
