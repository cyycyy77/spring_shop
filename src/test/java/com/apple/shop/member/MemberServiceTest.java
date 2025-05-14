package com.apple.shop.member;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

// Unit tests for MemberService
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void saveMember_nullUsername_throwsIllegalArgument() {
        assertThatThrownBy(() -> memberService.saveMember(null, "password123", "Tester"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("아이디를 입력하세요");
    }

    @Test
    void saveMember_nullPassword_throwsException() {
        assertThatThrownBy(() -> memberService.saveMember("username", null, "Tester"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호를 입력하세요");
    }

    @Test
    void saveMember_longUsername_throwsException() {
        assertThatThrownBy(() -> memberService.saveMember("username".repeat(256/8), "short", "Tester"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("너무 깁니다.");
    }

    @Test
    void saveMember_shortUsername_throwsException() {
        assertThatThrownBy(() -> memberService.saveMember("short", "password", "Tester"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("너무짧음");
    }

    @Test
    void saveMember_shortPassword_throwsException() {
        assertThatThrownBy(() -> memberService.saveMember("username", "short", "Tester"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("너무짧음");
    }

    @Test
    void saveMember_validInput_savesMember() throws Exception {
        when(passwordEncoder.encode("securePass")).thenReturn("hashed");

        memberService.saveMember("newuser123", "securePass", "New User");

        verify(memberRepository).save(argThat(member ->
                member.getUsername().equals("newuser123") &&
                        member.getPassword().equals("hashed") &&
                        member.getDisplayName().equals("New User")
        ));
    }
}