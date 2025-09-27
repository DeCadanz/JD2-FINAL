package by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.enums.EEssenceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit", schema = "fin_app")
@Getter
@Setter
@NoArgsConstructor
public class AuditEntity {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "dt_create")
    private Long dtCreate;

    @Column(name = "user_id")
    private String uuuid;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EEssenceType type;


}
