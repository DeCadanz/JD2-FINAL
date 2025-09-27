package by.it_academy.jd2.Mk_JD2_111_25.FINAL;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {
        "by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.repository.api",
        "by.it_academy.jd2.Mk_JD2_111_25.FINAL.classifier.repository.api",
        "by.it_academy.jd2.Mk_JD2_111_25.FINAL.account.repository.api",
        "by.it_academy.jd2.Mk_JD2_111_25.FINAL.audit.repository.api"
    }
)

@SpringBootApplication
public class FinApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();  // Загружает .env
        System.setProperty("SMTP_PASSWORD", dotenv.get("SMTP_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
        SpringApplication.run(FinApplication.class, args);
    }
}
