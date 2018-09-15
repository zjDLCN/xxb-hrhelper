package com.jgt.xx.hrhelper.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

/**
 * 用户不具有该资源的访问/操作权限
 */
@Slf4j
@Service
public class MyAccessDeniedHandler implements AccessDeniedHandler {
  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest httpServletRequest,
      HttpServletResponse response,
      AccessDeniedException e) throws IOException, ServletException {
    log.info("没有权限");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
//    response.sendError(HttpServletResponse.SC_FORBIDDEN,
//              e.getMessage());
  }
}
