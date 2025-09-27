package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codes", schema = "fin_app")
@Getter
@Setter
@NoArgsConstructor
public class CodeEntity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "code")
    private String code;
}
