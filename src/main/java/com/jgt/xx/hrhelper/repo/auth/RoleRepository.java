package com.jgt.xx.hrhelper.repo.auth;

import com.jgt.xx.hrhelper.base.BaseRepository;
import com.jgt.xx.hrhelper.model.auth.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色实体操作
 */
@Repository
public interface RoleRepository
    extends BaseRepository<Role,Long> {

  /**
   * 判断存在相同登录名的数据
   * @param roleName 登录名
   * @return 如果有则返回 true 如果没有则返回false
   */
  boolean existsByRoleName(String roleName);

  /**
   * 查询在无效数据中是否存在该条数据
   * @param id 主键
   * @return 用户实体
   */
  Role findByIdAndValidIsFalse(Long id);
}
