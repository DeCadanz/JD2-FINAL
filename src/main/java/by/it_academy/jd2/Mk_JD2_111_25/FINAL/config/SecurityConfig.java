package by.it_academy.jd2.Mk_JD2_111_25.FINAL.config;

import by.it_academy.jd2.Mk_JD2_111_25.FINAL.filter.CustomAccessDeniedHandler;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.filter.CustomAuthenticationEntryPoint;
import by.it_academy.jd2.Mk_JD2_111_25.FINAL.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(ex -> ex
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/cabinet/registration",
                        "/cabinet/verification",
                        "/cabinet/login",
                        "/error"
                ).permitAll()
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/classifier/currency").permitAll()
                .requestMatchers(HttpMethod.POST,"/classifier/currency").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/classifier/operation/category").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/account/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                .requestMatchers("/audit").hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
