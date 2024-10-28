package com.sparta.chatting_test.common.config;

import com.sparta.chatting_test.common.security.CustomUserDetailsService;
import com.sparta.chatting_test.common.security.JwtAuthenticationFilter;
import com.sparta.chatting_test.common.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil,customUserDetailsService));
        registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴을 지정합니다.
        return registrationBean;
    }
}