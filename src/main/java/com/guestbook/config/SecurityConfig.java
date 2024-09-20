package com.guestbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 패스워드를 암호화하기 위한 PasswordEncoder 빈을 생성. BCryptPasswordEncoder 사용.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 설정을 위한 SecurityFilterChain 빈을 생성.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 요청에 대한 인증 및 권한 설정
                .authorizeRequests()
                // 지정된 경로는 모든 사용자에게 접근 허용 (인증 없이 접근 가능)
                .mvcMatchers("/", "/member/**", "/guestbook/**", "/css/**", "/js/**", "/image/**", "/images/**").permitAll()
                // 그 외 모든 요청은 인증을 요구
                .anyRequest().authenticated()
                .and()
                // 로그인 설정
                .formLogin()
                // 커스텀 로그인 페이지 URL 설정
                .loginPage("/member/login")
                // 로그인 성공 시 리디렉트할 기본 URL
                .defaultSuccessUrl("/")
                // 로그인 폼에서 사용자 ID를 받을 파라미터 이름 설정
                .usernameParameter("userId")
                // 로그인 폼에서 비밀번호를 받을 파라미터 이름 설정
                .passwordParameter("password")
                // 로그인 실패 시 이동할 URL
                .failureUrl("/member/login/error")
                // 로그인 페이지에 누구나 접근할 수 있도록 허용
                .permitAll()
                .and()
                // 로그아웃 설정
                .logout()
                // 로그아웃 요청을 처리할 URL
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                // 로그아웃 성공 시 리디렉트할 URL
                .logoutSuccessUrl("/")
                // 로그아웃 요청에 누구나 접근할 수 있도록 허용
                .permitAll()
                .and()
                // CSRF 보호 비활성화 (CSRF 공격에 대한 보호 기능을 사용하지 않음)
                .csrf().disable()
                // 요청 캐시 기능 비활성화 (특정 요청에 대해 캐시하지 않음)
                .requestCache().disable()
                // 권한 부족(403) 시 접근 거부 페이지 설정
                .exceptionHandling().accessDeniedPage("/403");

        // 설정된 SecurityFilterChain을 반환
        return http.build();
    }
}
