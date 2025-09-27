package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@Getter
public class PageOfCurrency<T> {
    private int number;
    private int size;
    private int total_pages;
    private long total_elements;
    private boolean first;
    private int number_of_elements;
    private boolean last;
    private List<Currency> content;

    public PageOfCurrency(Page<Currency> page) {
        this.number = page.getNumber();
        this.size = page.getSize();
        this.total_pages = page.getTotalPages();
        this.total_elements = page.getTotalElements();
        this.first = page.isFirst();
        this.number_of_elements = page.getNumberOfElements();
        this.last = page.isLast();
        this.content = page.getContent();
    }
}
