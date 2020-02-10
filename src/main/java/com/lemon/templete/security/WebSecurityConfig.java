package com.lemon.templete.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.templete.constant.ApiConstant;
import com.lemon.templete.enums.ErrorInfoEnum;
import com.lemon.templete.exception.ExceptionResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述：Security配置类
 * @author 汤中流
 * @date 2018/08/05
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService customUserService() {
        return new CustomUserService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    CustomSecurityMetadataSource customSecurityMetadataSource;
    @Resource
    CustomAccessDecisionManager customAccessDecisionManager;

    /**
     * 认证
     * -- userDetailsService（）指定认证方法，通过登录用户名和密码进行认证
     * -- passwordEncoder（）指定密码的加解密方法
     *
     * @param auth
     * @throws Exception
     */
    @Resource
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService())
                .passwordEncoder(passwordEncoder());

//        用以下代码可以配置内存中的用户。适用场景：测试时或者就是想指定下列特定用户可以登录的时候。
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password("123456").roles("ADMIN")
//                .and()
//                .withUser("guest").password("123456").roles("USER");
    }

    /**
     * 配置认证白名单
     * （无需认证的页面或者URL）
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/favicon.ico", "/swagger3/**");
    }

    /**
     * 授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() { // 请求过滤器
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        // 获取权限（获取请求URL所需要的角色）
                        o.setSecurityMetadataSource(customSecurityMetadataSource);
                        // 验证权限（比对【请求URL所需要的角色】和【登录用户所具有的角色】）
                        o.setAccessDecisionManager(customAccessDecisionManager);
                        return o;
                    }
                })
                .and().formLogin().loginProcessingUrl(ApiConstant.SYS_USER_LOGIN)
                .usernameParameter("userName").passwordParameter("userPassword")
                .failureHandler(new AuthenticationFailureHandler() {  // 登录失败处理
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        ExceptionResponse exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_LOGIN_OTHER_ERROR);
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_NAME_OR_PASSWORD_ERROR);
                        } else if (e instanceof DisabledException) {
                            exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_STATUS_OFF);
                        }
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(exceptionResponse));
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {  // 登录成功处理
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl(ApiConstant.SYS_USER_LOGOUT)
                .logoutSuccessHandler(new LogoutSuccessHandler() {  // 注销成功处理
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                    }
                })
                .permitAll()
                .and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {  // 认证异常处理
                    @Override
                    public void commence(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        ExceptionResponse exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_OTHER_AUTHENTICATION_ERROR);
                        if (e instanceof BadCredentialsException) {
                            exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_NOT_LOGIN);
                        }

                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(exceptionResponse));
                        out.flush();
                        out.close();
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {  // 授权异常处理
                    @Override
                    public void handle(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse,
                                       AccessDeniedException e) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        ExceptionResponse exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_OTHER_ACCESS_DENIED);
                        if (e instanceof AccessDeniedException) {
                            exceptionResponse = ExceptionResponse.getInstance(ErrorInfoEnum.USER_ACCESS_DENIED);
                        }

                        PrintWriter out = httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(exceptionResponse));
                        out.flush();
                        out.close();
                    }
                });
    }
}
