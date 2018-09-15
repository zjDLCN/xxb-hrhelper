package com.jgt.xx.hrhelper.model.auth;

import com.jgt.xx.hrhelper.base.BasePO;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 权限实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_auth_permission")
public class Permission extends BasePO {

  private static final long serialVersionUID = -8760174573928462336L;

  /**
   * 权限名称
   */
  @Column(nullable = false)
  private String permissionName;

  /**
   * 权限字符串
   */
  private String permissionText;

  /**
   * 资源类型
   * 0 ： menu
   * 1 ： button
   */
  private short resourceType;

  /**
   * 资源地址
   */
  private String resourceUrl;

  /**
   * 父编号ID
   */
  private Long parentId;

  /**
   * 父编号ID列表
   */
  private String parentIds;

  /**
   * 权限状态
   * 0 ： 未启用
   * 1 ： 正常使用
   * 2 ： 停用
   */
  private short state = 1;

  /**
   * 角色 - 权限关系表
   * 多对多关系
   */
  @ManyToMany
  @JoinTable(name = "t_auth_role_permission",
    joinColumns={@JoinColumn(name="permissionId",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "roleId",referencedColumnName = "id")})
  private List<Role> roleList;
}
