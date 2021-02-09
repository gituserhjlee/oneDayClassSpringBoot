package com.example.security.config;

import com.example.security.AuthFailureHandler;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security를 활성화
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // Spring Security의 설정파일로서의 역할을 하기 위해 상속

    private final UserService userService; // 유저 정보를 가져올 클래스
    private final AuthFailureHandler authFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호를 암호화할 때 사용할 인코더를 미리 빈으로 등록
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) { // 인증을 무시할 경로, static 하위 파일 목록(css, js, img) 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/h2-console/**"); // h2-console 추가
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 관련 인증 설정
        http
                .authorizeRequests() // 접근에 대한 인증 설정이 가능합니다.
                .antMatchers("/login", "/signup","/user").permitAll() // 누구나 접근 허용
                .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/admin/makeItem","/admin/order","/admin/admin","/items/new","/items/{itemId}/edit","/items/{itemId}").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin()
                .loginPage("/login") // 로그인 페이지 링크
                .loginProcessingUrl("/login_proc")// th:action="@{/login_proc}"
                .failureHandler(authFailureHandler)
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 로그아웃에 관한 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 로그아웃 이후 세션 날리기
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 로그인할 때 필요한 정보를 가져오는 곳
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // 해당 서비스(userService)에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함 (서비스 참고)
    }

}