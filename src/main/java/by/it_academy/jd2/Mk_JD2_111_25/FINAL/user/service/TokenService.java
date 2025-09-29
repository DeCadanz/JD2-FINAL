package by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.User;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.dto.UserLogin;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.user.service.api.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    private final UserService userService;

    @Override
    public String generate(UserLogin login) {
        User user = userService.getByMail(login.getMail());

        return Jwts.builder()
                .subject(user.getUuid())
                .claim("mail", user.getMail())
                .claim("role", user.getRole())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(expirationTime)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Claims extractClaims(String token) {
        JwtParserBuilder parser = Jwts.parser();
        parser.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()));
        return parser.build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public User get(String token) {
        Claims claims = extractClaims(token);
        return userService.getByMail(claims.getSubject());
    }
}
