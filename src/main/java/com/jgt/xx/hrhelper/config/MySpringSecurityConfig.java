package com.jgt.xx.hrhelper.config;

import com.jgt.xx.hrhelper.security.LoginSuccessHandler;
import com.jgt.xx.hrhelper.security.MyAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * spring security 相关配置
 */
@Slf4j
@Configuration
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AuthenticationSuccessHandler loginSuccessHandler;

  @Autowired
  AuthenticationFailureHandler loginFailureHandler;

  @Autowired
  MyAccessDeniedHandler myAccessDeniedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()   //  定义当需要用户登录时候，转到的登录页面
          .loginPage("/authentication/require")   //设置登陆拦截
          .failureUrl("/page/login_init?faile=true")
          .loginProcessingUrl("/login")  //登录提交时执行程序,调用security拦截
          .successHandler(loginSuccessHandler)
          .failureHandler(loginFailureHandler)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login_init")
            .permitAll()
        .and()
        //权限不足的处理程序
        .exceptionHandling()
          .accessDeniedHandler(myAccessDeniedHandler)       // 权限不足 403
          //.authenticationEntryPoint() // 登陆失败
        .and()
        .authorizeRequests()
          .antMatchers("/authentication/require",
                       "/login_init")  //登录页面都可访问
          .permitAll()
        .anyRequest()
          .access("@validatePermission.valid(request,authentication)")
        .and()
        .cors()
          .disable()
        .csrf()
          .disable();
    // 避免 Refused to display in a frame because it set 'X-Frame-Options' to 'DENY' 错误
    http.headers().frameOptions().disable();
  }


}
