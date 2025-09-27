package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.ERole;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", schema = "fin_app")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "dt_create")
    private Long dtCreate;

    @Column(name = "dt_update")
    private Long dtUpdate;

    @Column(name = "mail")
    private String mail;

    @Column(name = "fio")
    private String fio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role = ERole.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status = EStatus.WAITING_ACTIVATION;

    @Column(name = "password")
    private String password;
}
