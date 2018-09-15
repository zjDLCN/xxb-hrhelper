package com.jgt.xx.hrhelper.web.auth;

import com.jgt.xx.hrhelper.base.PageDataVO;
import com.jgt.xx.hrhelper.base.ResponseVO;
import com.jgt.xx.hrhelper.model.auth.Role;
import com.jgt.xx.hrhelper.servcie.auth.RoleServcie;
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

@Slf4j
@RestController
@RequestMapping("/auth/roles")
public class RoleController {

  @Autowired
  private RoleServcie roleServcie;

  /**
   * 页面初始化加载
   * @return 跳转
   */
  @GetMapping("/init")
  public ModelAndView initPage(Model model) {
    return new ModelAndView("/auth/role");
  }

  /**
   * 根据条件查询信息
   * @param page 查询第几页
   * @param rows 每页条目数
   * @param roleName 角色名
   * @param state 用户状态
   * @param valid 数据状态
   * @return 符合条件的用户列表
   */
  @GetMapping
  public PageDataVO findDataList(int page,int rows,String roleName,short state,boolean valid) {
    Role queryCondition = new Role();
    queryCondition.setRoleName(roleName);
    queryCondition.setState(state);
    queryCondition.setValid(valid);

    Page<Role> queryResults = roleServcie.findDataList(page,rows,queryCondition);

    return new PageDataVO(queryResults.getTotalElements(),queryResults.getContent());
  }

  /**
   * 保存数据
   * @param data 需要保存的数据
   * @return 插入数据后的返回值
   */
  @PostMapping
  public ResponseVO insertData(@Validated(CreateGroup.class) Role data) {
    log.info("插入数据程序启动=================>");
    log.info(data.toString());
    Role result = roleServcie.insertData(data);
    log.info("数据【{}：{}】保存完成========>",result.getId(),result.getRoleName());
    return ResponseVO.success(result);
  }

  /**
   * 修改数据
   * @param data 需要修改的数据
   * @return 插入数据后的返回值
   */
  @PutMapping
  public ResponseVO updateData(Role data) {
    log.info("修改数据程序启动=================>");
    log.info(data.toString());
    Role result = roleServcie.updateData(data);
    log.info("数据【{}：{}】修改完成========>",result.getId(),result.getRoleName());
    return ResponseVO.success(result);
  }

  /**
   * 删除数据
   * @param id 删除数据主键
   * @return
   */
  @DeleteMapping("/{id}")
  public ResponseVO deleteData(@PathVariable("id") Long id) {
    log.info("删除数据程序启动=================>");
    Boolean result = roleServcie.deleteData(id);
    log.info("数据【{}】删除完成========>",id);
    return ResponseVO.success(result);
  }

  /**
   * 数据恢复
   * @param data 需要修改的数据
   * @return 插入数据后的返回值
   */
  @PutMapping("/reset")
  public ResponseVO resetData(Role data) {
    log.info("恢复数据程序启动=================>");
    log.info(data.toString());
    Boolean result = roleServcie.resetValid(data.getId());
    log.info("数据【{}：{}】恢复完成========>",data.getId(),data.getRoleName());
    return ResponseVO.success(result);
  }
}
