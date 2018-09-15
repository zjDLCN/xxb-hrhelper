package com.jgt.xx.hrhelper.security;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 验证是否有访问/操作权限
 */
@Slf4j
@Component("validatePermission")
public class ValidatePermission {

  public AntPathMatcher antPathMatcher = new AntPathMatcher();

  public boolean valid(HttpServletRequest request,Authentication authentication) {
    boolean valid = false;
    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
      String userName = ((UserDetails)principal).getUsername();
      // 根据用户名查询权限URL
      Set<String> urls = new HashSet<>();
      // TODO: 此处从数据库取得可以访问的URL信息
      urls.add("/**");

      for (String url:urls) {
        // 判断当前请求的URL是否在权限列表内
        if (antPathMatcher.match(url, request.getRequestURI())) {
          valid = true;
          break;
        }
      }
    }
    return valid;
  }
}
