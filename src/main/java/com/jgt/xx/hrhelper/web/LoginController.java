package com.jgt.xx.hrhelper.web;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义无权限跳转返回形式
 * 适配Restful API 接口
 *
 * web端调用，返回login.html
 * api端调用，返回错误信息
 */
@Slf4j
@RestController
public class LoginController {

  // 原请求信息的缓存及恢复
  private RequestCache requestCache = new HttpSessionRequestCache();

  // 用于重定向
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  /**
   * 当需要身份认证的时候，跳转过来
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/authentication/require")
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public String requireAuthenication(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest != null) {
      String targetUrl = savedRequest.getRedirectUrl();
      log.info("引发跳转的请求是:" + targetUrl);
      // 判断不是访问 /api/接口
      // TODO： 全项目非API接口访问，禁止使用 \api  作为访问路径
      if (0 == StringUtils.countOccurrencesOf(targetUrl, "/api/")) {
        redirectStrategy.sendRedirect(request, response, "/login_init");
      }
    }

    //TODO: 此处后续统一封装返回类型

    return "访问的服务需要身份认证，请引导用户到登录页";
  }

  @GetMapping("/login_init")
  public ModelAndView loginPageInit(
      @RequestParam(value = "fail",required = false,defaultValue = "true") Boolean valid,
      Model model) {
    //  登录验证失败
    if (!valid) {
      model.addAttribute("error", "登录失败！！！");
    }
    return new ModelAndView("/page/login");
  }
}
