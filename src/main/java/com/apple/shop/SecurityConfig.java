package com.apple.shop;

import com.apple.shop.member.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf기능 켤때 코드 비활성화
//        http.csrf((csrf) -> csrf.disable());

//        로그인·회원가입·JWT 로그인 엔드포인트는 인증 없이 접근 허용
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers(
                        "/**"
                ).permitAll()
        );

        // jwt 로그인시 비활성화
//        http.formLogin((formLogin)
//                -> formLogin.loginPage("/login")
//                .defaultSuccessUrl("/")
//        );
        http.logout(logout -> logout.logoutUrl("/logout") );

        // jwt 로그인시
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // jwt 로그인시
        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

//        csrf 기능 켤때 코드 활성화
        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
                .ignoringRequestMatchers(
                        "/login", "/register","/login/jwt","/savemember")
        );

        return http.build();
    }
    //        csrf 기능 켤때 코드 활성화
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


}
