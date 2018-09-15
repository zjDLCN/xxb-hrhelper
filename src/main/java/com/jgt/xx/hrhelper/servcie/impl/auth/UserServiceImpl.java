package com.jgt.xx.hrhelper.servcie.impl.auth;

import com.google.common.base.Preconditions;
import com.jgt.xx.hrhelper.model.auth.Permission;
import com.jgt.xx.hrhelper.model.auth.Role;
import com.jgt.xx.hrhelper.model.auth.User;
import com.jgt.xx.hrhelper.repo.auth.UserRepository;
import com.jgt.xx.hrhelper.servcie.auth.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * {权限}用户实体实现类
 */
@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;

  /**
   * 通过登录用户名查询用户信息
   *
   * @param loginName 登录用户名
   * @return 用户信息
   */
  @Override
  public User findUserByLoginName(String loginName) {
    User result = userRepository.findByLoginName(loginName);
    //TODO : 手动构建权限信息，等待权限模块完成后再修改此处
    //TODO : 设置权限信息
    List<Permission> permissionList = new ArrayList<>();
    Permission permission = new Permission();
    permission.setPermissionText("user:view");
    permissionList.add(permission);
    //TODO: 设置角色信息
    List<Role> roleList = new ArrayList<>();
    Role role = new Role();
    role.setPermissionList(permissionList);
    role.setRoleName("admin");
    roleList.add(role);

    result.setRoleList(roleList);
    return result;
  }

  /**
   * 根据条件,分页获取全部数据
   *
   * @param pageNo 查询页数
   * @param pageSize 每页数据数
   * @return 当前页的数据集合
   */
  @Override
  public Page<User> findDataList(int pageNo, int pageSize,User params) {

    // 构建分页信息
    Pageable pageable = PageRequest.of(pageNo-1,pageSize,
        Direction.DESC,"updatedTime");
    // 构建查询条件信息
    Specification<User> spec = (Specification<User>) (root, query, cb) ->{
      List<Predicate> predicateList = new ArrayList<Predicate>();
      if (!StringUtils.isEmpty(params.getLoginName())) {
        predicateList.add(cb.like(root.get("loginName"),"%"+ params.getLoginName()+"%"));
      }
      if(params.getState() != 99){
        predicateList.add(cb.equal(root.get("state"), params.getState()));
      }
      predicateList.add(cb.equal(root.get("valid"), params.getValid()));
      return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
    };

    return userRepository.findAll(spec,pageable);
  }

  /**
   * 保存数据
   *
   * @param data 需要插入的数据
   * @return 保存后的数据
   */
  @Override
  public User insertData(User data) {
    // 重复值校验
    if(userRepository.existsByLoginName(data.getLoginName())){
      throw new RuntimeException("用户名重复！");
    }
    return userRepository.save(data);
  }

  /**
   * 保存数据
   * @param data 修改数据实体，必须包含主键
   */
  @Override
  public User updateData(User data) {
    // 根据主键查询实体数据
    User modifyData = userRepository.findById(data.getId()).orElseThrow(
        ()-> new RuntimeException("数据不存在")
    );
    // 将修改对象Copy到数据库实体中
    modifyData.setNickName(data.getNickName());
    modifyData.setEmail(data.getEmail());
    modifyData.setState(data.getState());
    modifyData.setRemark(data.getRemark());
    // 数据库持久化
    return userRepository.save(modifyData);
  }

  /**
   * 数据逻辑删除
   *
   * @param id 删除数据的主键
   * @return 是否删除成功
   */
  @Override
  public Boolean deleteData(Long id) {
    // 根据主键查询实体数据
    User modifyData = userRepository.findById(id).orElseThrow(
        ()-> new RuntimeException("数据不存在")
    );
    // 逻辑删除
    modifyData.setValid(false);
    // 数据库持久化
   userRepository.save(modifyData);
   return  true;
  }

  /**
   * 重置用户密码
   *
   * @param id 需要重置用户的主键
   * @return 是否重置成功
   */
  @Override
  public Boolean resetUserPwd(Long id) {
    // 根据主键查询实体数据
    User modifyData = userRepository.findById(id).orElseThrow(
        ()-> new RuntimeException("数据不存在")
    );
    // 密码初始化
    modifyData.setPassword(modifyData.initPwd());
    // 数据库持久化
    userRepository.save(modifyData);
    return true;
  }

  /**
   * 恢复数据有效性，并重置用户密码
   *
   * @param id 需要恢复的用户ID
   * @return 是否恢复成功
   */
  @Override
  public Boolean resetValid(Long id) {
    // 根据主键查询实体数据
    User modifyData = userRepository.findByIdAndValidIsFalse(id);
    if (modifyData == null || modifyData.getId() == null) {
      throw new RuntimeException("数据不存在");
    }
    // 有效性恢复
    modifyData.setValid(true);
    // 密码初始化
    modifyData.setPassword(modifyData.initPwd());
    // 数据库持久化
    userRepository.save(modifyData);
    return true;
  }
}
