package com.jgt.xx.hrhelper.servcie.impl.auth;

import com.jgt.xx.hrhelper.model.auth.Organization;
import com.jgt.xx.hrhelper.model.auth.Role;
import com.jgt.xx.hrhelper.repo.auth.RoleRepository;
import com.jgt.xx.hrhelper.servcie.auth.RoleServcie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class RoleServcieImpl implements RoleServcie {

  @Autowired
  private RoleRepository roleRepository;

  /**
   * 分页获取全部数据
   *
   * @param pageNo 查询页数
   * @param pageSize 每页数据数
   * @return 当前页的数据集合
   */
  @Override
  public Page<Role> findDataList(int pageNo, int pageSize, Role params) {
    // 构建分页信息
    Pageable pageable = PageRequest.of(pageNo-1,pageSize,
        Direction.DESC,"updatedTime");
    // 构建查询条件信息
    Specification<Role> spec = (Specification<Role>) (root, query, cb) ->{
      List<Predicate> predicateList = new ArrayList<Predicate>();
      if (!StringUtils.isEmpty(params.getRoleName())) {
        predicateList.add(cb.like(root.get("roleName"),"%"+ params.getRoleName()+"%"));
      }
      if(params.getState() != 99){
        predicateList.add(cb.equal(root.get("state"), params.getState()));
      }
      predicateList.add(cb.equal(root.get("valid"), params.getValid()));

      int size = predicateList.size();
      return cb.and(predicateList.toArray(new Predicate[size]));
    };

    return roleRepository.findAll(spec,pageable);
  }

  /**
   * 保存数据
   *
   * @param data 需要插入的数据
   * @return 保存后的数据
   */
  @Override
  public Role insertData(Role data) {
    // 重复值校验
    if(roleRepository.existsByRoleName(data.getRoleName())){
      throw new RuntimeException("角色名重复！");
    }
    return roleRepository.save(data);
  }

  /**
   * 保存数据
   *
   * @param data 修改数据实体，必须包含主键
   */
  @Override
  public Role updateData(Role data) {
    // 根据主键查询实体数据
    Role modifyData = roleRepository.findById(data.getId()).orElseThrow(
        ()-> new RuntimeException("数据不存在")
    );
    // 将修改对象Copy到数据库实体中
    modifyData.setDescription(data.getDescription());
    modifyData.setState(data.getState());
    modifyData.setRemark(data.getRemark());
    // 数据库持久化
    return roleRepository.save(modifyData);
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
    Role modifyData = roleRepository.findById(id).orElseThrow(
        ()-> new RuntimeException("数据不存在")
    );
    // 逻辑删除
    modifyData.setValid(false);
    // 数据库持久化
    roleRepository.save(modifyData);
    return  true;
  }

  /**
   * 数据逻辑删除后恢复
   *
   * @param id 数据主键
   * @return 是否恢复成功
   */
  @Override
  public Boolean resetValid(Long id) {
    // 根据主键查询实体数据
    Role modifyData = roleRepository.findByIdAndValidIsFalse(id);
    if (modifyData == null || modifyData.getId() == null) {
      throw new RuntimeException("数据不存在");
    }
    // 有效性恢复
    modifyData.setValid(true);
    // 数据库持久化
    roleRepository.save(modifyData);
    return true;
  }
}
