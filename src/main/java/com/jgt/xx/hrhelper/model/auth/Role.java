package com.jgt.xx.hrhelper.model.auth;

import com.jgt.xx.hrhelper.base.BasePO;
import com.jgt.xx.hrhelper.valid.CreateGroup;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 角色实体信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_auth_role")
public class Role extends BasePO {

  private static final long serialVersionUID = 8428856908224847729L;
  /**
   * 角色名称
   * 需保证有效数据中的唯一性
   */
  @NotBlank(message = "角色名称不可为空！",
            groups = CreateGroup.class)
  @Column(nullable = false)
  private String roleName;

  /**
   * 角色描述
   */
  @Lob
  @Column(columnDefinition = "text")
  private String description;

  /**
   * 角色状态
   * 0:未启用
   * 1：正常使用
   * 2：停用
   */
  private short state =1;

  /**
   * 用户-角色关系定义
   * 多对多
   */
  @ManyToMany
  @JoinTable(name = "t_auth_user_role",
    joinColumns = {@JoinColumn(name = "roleId",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "userId",referencedColumnName = "id")})
  private List<User> userList;

  /**
   * 角色-权限关系定义
   * 多对多
   */
  @ManyToMany
  @JoinTable(name = "t_auth_role_permission",
    joinColumns = {@JoinColumn(name = "roleId",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "permissionId",referencedColumnName = "id")})
  private List<Permission> permissionList;
}
