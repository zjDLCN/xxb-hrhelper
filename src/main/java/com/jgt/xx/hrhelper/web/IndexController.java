package com.jgt.xx.hrhelper.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
  @GetMapping(value = {"/","/index"})
  public ModelAndView initIndex(Model model) {
    // 顶部区域
    // 显示的用户信息
    model.addAttribute("roleName", "普通用户");
    // 快捷图标
    model.addAttribute("showDialog", false);
    model.addAttribute("showEnvelope", true);
    model.addAttribute("showBell", false);

    return new ModelAndView("index");
  }

  @GetMapping("/base")
  public ModelAndView initBase(Model model) {
    model.addAttribute("menuList", getMenuUrl());
    return new ModelAndView("base");
  }

  private Map<String, String> getMenuUrl() {
    Map<String,String> menuList = new HashMap<String,String>();
    menuList.put("/auth/init","权限-用户信息管理");
    menuList.put("1","权限-角色信息管理");
    menuList.put("2","权限-资源信息管理");
    menuList.put("3","权限-权限管理");

    return menuList;
  }
}
