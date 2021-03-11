package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Configuration
 * 스프링의 @Configuration 어노테이션은 어노테이션기반 환경구성을 돕는다.
 * 이 어노테이션을 구현함으로써 클래스가 하나 이상의 @Bean 메소드를 제공하고
 * 스프링 컨테이가 Bean정의를 생성하고 런타임시 그 Bean들이 요청들을 처리할 것을 선언하게 된다.
 *
 * @EnableWebSecurity 애너테이션은 웹 보안을 활성화 한다.
 * 하지만 그자체로는 유용하지 않고, 스프링 시큐리티가 WebSecurityConfigurer를 구현하거나
 * 컨텍스트의 WebSebSecurityConfigurerAdapter를 확장한 빈으로 설정되어 있어야 한다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    // WebSecurityConfigurerAdapter 가 없을 때 기본 보안 설정을 한다. 하나 이상 발견되면 설정된 보안 설정을 사용한다.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("indexdd");
        registry.addViewController("/loginddd.html").setViewName("logindd"); // 매핑
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.securityContext()
                .and().exceptionHandling()
                // 예외 핸들링
                .and().servletApi()
                // 서블릿 API 통합
                .and().httpBasic()
                // http 기본인증
                .and().logout().logoutSuccessUrl("/d")
        .and().headers()
                // 로그아웃후 브라우저의 뒤로 가기 버튼을 사용하면 로그아웃이 성공하더라도 이전 페이지를 계속볼수 있다.
                // 이는 브라우적 ㅏ페이지를 캐시한다는 사실과 관련이 있다.
                // 그러므로 header() 구성 메소드로 보안 헤더를 활성화하면 브라우저가 페이지를 캐시하지 않도록 지시한다.
                // no-cache 헤더 옆에서 콘텐츠 스니핑이 비활성화되고 x-frame 보호가 활성화된다.
        .and().csrf()
        .and().anonymous().principal("guest").authorities("ROLE_GUEST")
        .and().rememberMe()
                // 기본적으로 사용자 이름, 암호, remember-me 만료 시간 및 개인 키를 토큰으로 인코딩하고 쿠키로 사용자의 브라우저에 저장한다.
                // 다음에 사용자가 동일한 웹 애플리케이션에 액세스하면 이 토큰이 감지돼 사용자가 잗종으로 로그인할 수 있따.
                // 정적 Remeber-Me 토큰은 해커가 캡쳐할 수 있기 때문에 보안문제가 있다. 그러므로 롤링토큰을 지원하지만,
                // 토큰을 유지하려면 데이터베이스가 필요하다. 자세한것은 다음게시물에 작성예정이다.
        .and().formLogin().loginPage("/logidn.html").defaultSuccessUrl("/boodks.html").failureUrl("/ldogin.html?error=true").permitAll()
                // 폼기반 로그인
                .and().authorizeRequests()
                .mvcMatchers("/d").permitAll()
                // "/"들어오는 요청은 모두허용
                .anyRequest().authenticated();
                // 어떤요청이든 인증확인
    }

    // 인메모리 기반 사용자 인증
    /*
    스프링 보안에서 인증은 체인으로 연결된 하나 이상의 AuthenticationProvider에 의해 수행된다.
    이중 하나가 사용자를 성공적으로 인증하면 해당 사용자는 애플리케이션에 로그인할 수 있다.
    어느 인증 제공자라도 사용자가 비활성화됐거나 잠겼거나 자격 증명이 잘못됐다고 기록되거나 인증 제공자가
    사용자를 인증할 수 없는 경우에는 사용자는 애플리케이션에 로그인할 수 없다.
    스프링 보안은 암호(BCrypt 와 SCrypt 포함)를 암호화하는 여러 가지 알고리즘을 지원하며 이러한 알고리즘을 위한 기본 암호 인코더를 제공
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	 //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ADMIN","USER").build();

        UserDetails normalUser = User.builder()
                .username("normaluser")
                .password(passwordEncoder.encode("user"))
                .authorities("USER").build();

        UserDetails disabledUser = User.builder()
                .username("disableduser")
                .password(passwordEncoder.encode("user"))
                .disabled(true)
                .authorities("USER").build();

        auth.inMemoryAuthentication()
                .withUser(adminUser)
                .withUser(normalUser)
                .withUser(disabledUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}