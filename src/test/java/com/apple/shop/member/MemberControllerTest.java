package com.apple.shop.member;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class,
        excludeAutoConfiguration = { ThymeleafAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberRepository memberRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private MemberService memberService;

    @MockitoBean
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @Test
    void getRegisterPage_whenNotAuthenticated_thenShowRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register.html"));
    }

    @Test
    void getLoginPage_whenAuthenticated_thenRedirectToList() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user1", "", List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        mockMvc.perform(get("/login").principal(auth))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"));
    }

    @Test
    void getLoginPage_whenNotAuthenticated_thenShowLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }

    @Test
    void postLoginJwt_whenCredentialsValid_returnsJwtAndCookie() throws Exception {
        // Prepare a dummy authentication and JWT
        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user1", "", List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        // Stub AuthenticationManagerBuilder to return our mock AuthenticationManager
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        // Stub JwtUtil.createToken
        try (var jwtMock = mockStatic(JwtUtil.class)) {
            jwtMock.when(() -> JwtUtil.createToken(any(Authentication.class)))
                    .thenReturn("dummy-jwt-token");

            mockMvc.perform(post("/login/jwt")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\":\"user1\",\"password\":\"pass1234\"}"))
                    .andExpect(status().isOk())
                    .andExpect(cookie().value("jwt", "dummy-jwt-token"))
                    .andExpect(content().string("dummy-jwt-token"));
        }
    }
}
