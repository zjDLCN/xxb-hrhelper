package com.jgt.xx.hrhelper.servcie.auth;

import com.jgt.xx.hrhelper.base.BaseServcie;
import com.jgt.xx.hrhelper.model.auth.User;

/**
 * {权限}用户相关业务封装
 */
public interface UserService extends BaseServcie<User,Long> {

  /**
   * 通过登录用户名查询用户信息
   * @param loginName 登录用户名
   * @return 用户信息
   */
  User findUserByLoginName(String loginName);

  /**
   * 重置用户密码
   * @param id 需要重置用户的主键
   * @return 是否重置成功
   */
  Boolean resetUserPwd(Long id);


}
