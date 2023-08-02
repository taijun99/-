package com.example.soleproject.config.auth;

import com.example.soleproject.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/scnu/**", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/scnu/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout() // 로그아웃 설정
                .logoutSuccessUrl("/scnu/main") // 로그아웃 후 이동할 URL
                .invalidateHttpSession(true) // 세션 무효화 설정
                .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/scnu/main")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }
}
