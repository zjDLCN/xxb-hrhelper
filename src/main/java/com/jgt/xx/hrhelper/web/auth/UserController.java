package com.jgt.xx.hrhelper.web.auth;

import com.jgt.xx.hrhelper.base.PageDataVO;
import com.jgt.xx.hrhelper.base.ResponseVO;
import com.jgt.xx.hrhelper.model.auth.User;
import com.jgt.xx.hrhelper.servcie.auth.UserService;
import com.jgt.xx.hrhelper.valid.CreateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户实体控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth/users")
public class UserController {

  @Autowired
  private UserService userService;


  /**
   * 用户页面初始化加载
   * @return 跳转 /auth/user.html
   */
  @GetMapping("/init")
  public ModelAndView initPage(Model model) {
    return new ModelAndView("/auth/user");
  }

  /**
   * 根据条件查询用户信息
   * @param page 查询第几页
   * @param rows 每页条目数
   * @param loginName 登录名
   * @param state 用户状态
   * @param valid 数据状态
   * @return 符合条件的用户列表
   */
  @GetMapping
  public PageDataVO findDataList(int page,int rows,String loginName,short state,boolean valid) {
    User queryCondition = new User();
    queryCondition.setLoginName(loginName);
    queryCondition.setState(state);
    queryCondition.setValid(valid);

    Page<User> queryResults = userService.findDataList(page,rows,queryCondition);

    return new PageDataVO(queryResults.getTotalElements(),queryResults.getContent());
  }

  /**
   * 保存数据
   * @param user 需要保存的数据
   * @return 插入数据后的返回值
   */
  @PostMapping
  public ResponseVO insertData(@Validated(CreateGroup.class) User user) {
    log.info("插入数据程序启动=================>");
    log.info(user.toString());
    User userResult = userService.insertData(user);
    log.info("用户数据【{}：{}】保存完成========>",userResult.getId(),userResult.getLoginName());
    return ResponseVO.success(userResult);
  }

  /**
   * 修改数据
   * @param user 需要修改的数据
   * @return 插入数据后的返回值
   */
  @PutMapping
  public ResponseVO updateData(User user) {
    log.info("修改数据程序启动=================>");
    log.info(user.toString());
    User userResult = userService.updateData(user);
    log.info("用户数据【{}：{}】修改完成========>",userResult.getId(),userResult.getLoginName());
    return ResponseVO.success(userResult);
  }

  /**
   * 删除数据
   * @param user
   * @return
   */
  @DeleteMapping("/{id}")
  public ResponseVO deleteData(@PathVariable("id") Long id,User user) {
    log.info("删除数据程序启动=================>");
    Boolean result = userService.deleteData(id);
    log.info("用户数据【{}：{}】删除完成========>",id,user.getLoginName());
    return ResponseVO.success(result);
  }

  /**
   * 密码重置
   */
  @PutMapping("/{id}")
  public ResponseVO resetPwd(@PathVariable("id") Long id,User user) {
    log.info("重置密码程序启动=================>");
    Boolean result = userService.resetUserPwd(id);
    log.info("用户密码【{}：{}】重置完成========>",id,user.getLoginName());
    return ResponseVO.success(result);
  }

  /**
   * 数据恢复
   * @param user 需要修改的数据
   * @return 插入数据后的返回值
   */
  @PutMapping("/reset")
  public ResponseVO resetData(User user) {
    log.info("恢复数据程序启动=================>");
    log.info(user.toString());
    Boolean result = userService.resetValid(user.getId());
    log.info("用户数据【{}：{}】恢复完成========>",user.getId(),user.getLoginName());
    return ResponseVO.success(result);
  }

}
