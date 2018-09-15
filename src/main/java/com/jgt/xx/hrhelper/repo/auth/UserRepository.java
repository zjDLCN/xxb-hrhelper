package com.jgt.xx.hrhelper.repo.auth;

import com.jgt.xx.hrhelper.base.BaseRepository;
import com.jgt.xx.hrhelper.model.auth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 用户实体Dao操作
 */
@Repository
public interface UserRepository
    extends BaseRepository<User,Long> {

  /**
   * 查询符合登录名的数据
   * @param loginName 登录名
   * @return 用户信息
   */
  User findByLoginName(String loginName);

  /**
   * 判断存在相同登录名的数据
   * @param loginName 登录名
   * @return 如果有则返回 true 如果没有则返回false
   */
  boolean existsByLoginName(String loginName);

  /**
   * 查询在无效数据中是否存在该条数据
   * @param id 主键
   * @return 用户实体
   */
  User findByIdAndValidIsFalse(Long id);
}
