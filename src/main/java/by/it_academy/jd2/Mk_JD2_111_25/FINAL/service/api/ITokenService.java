package by.it_academy.jd2.Mk_JD2_111_25.FINAL.service.api;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.dto.UserLogin;
import io.jsonwebtoken.Claims;

public interface ITokenService {
    public String generate(UserLogin login);
    public boolean validate(String token);
    public Claims extractClaims(String token);
    public User get(String token);
}
