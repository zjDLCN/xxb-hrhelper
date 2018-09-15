package com.jgt.xx.hrhelper.security;

import com.jgt.xx.hrhelper.servcie.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义用户认证逻辑业务
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  private UserService userService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException {
    log.info("用户[{}]尝试登陆，启动认证程序.....",userName);
    //TODO： 根据用户名，查找对应的密码与权限

    //TODO： 构建符合spring security 的权限信息
    User user = new User(userName,
        "$2a$10$Kolje6ogVwN9Rfv1k9q5RuZg/19iMbDCUKU9qUKagn7VECk40qIRO", //jgt8888
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    return user;
  }
}
