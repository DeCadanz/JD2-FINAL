package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByMail(String email);
    Optional<UserEntity> findByUuid(String uuid);
    boolean existsByMail(String mail);

    Page<UserEntity> findAll(Pageable pageable);
}
