package by.it_academy.jd2.Mk_JD2_111_25.FINAL.util;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class CodeGenerator {
    public String getCode(){
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }
}
