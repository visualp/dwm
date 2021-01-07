package com.visualp.common.security;

import com.visualp.common.security.impl.AuthenticationSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * 1. 프로젝트명 : dwm
 * 2. 패키지명   : com.dwm.common.security
 * 3. 작성일     : 2020. 07. 14. 오전 8:45
 * 4. 작성자     : 고병만
 * 5. 이메일     : scormrte@gmail.com
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;


    public SecurityConfig(@Qualifier(value = "customUserDetailsService") UserDetailsService userDetailsService,
                          @Qualifier("customLogoutSuccessHandler") LogoutSuccessHandler logoutSuccessHandler,
                          @Qualifier(value = "customAuthenticationFailureHandler") AuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    /**
     * 예외 url 설정
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web
                .ignoring()
                .antMatchers("/web/**","/robots.txt","/favicon.ico")
                .antMatchers("/dockerhub/whook/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         * 관리자 : ROLE_ADMIN 모든 페이지 접근 허용
         */

        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated();

        /**
         * 로그인 페이지 세팅
         */
        http
                .formLogin()
                .usernameParameter("mbId")
                .passwordParameter("mbPassword")
                .loginPage("/login/login.html")
                .loginProcessingUrl("/login/sec_loginAction.html")
                .failureUrl("/login/login.html")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(this.authenticationFailureHandler)
                .permitAll();

        /**
         * 로그아웃 페이지 세팅
         */
        // 로그아웃
        http
                .logout()
                .logoutUrl("/login/logoutAction.html")
                .logoutSuccessHandler(this.logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll();
        /**
         * 세션관리
         */
        //최대 session 수:2
        http.sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login/login.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(mySqlPasswordEncoder());
    }

    @Bean
    public MySqlPasswordEncoder mySqlPasswordEncoder() {
        MySqlPasswordEncoder encoder = new MySqlPasswordEncoder();
        return encoder;
    }

    @Bean
    public AuthenticationSuccessHandlerImpl authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

}
