package by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency", schema = "fin_app")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyEntity {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "dt_create")
    private Long dtCreate;

    @Column(name = "dt_update")
    private Long dtUpdate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
}
