package com.jgt.xx.hrhelper.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jgt.xx.hrhelper.base.BasePO;
import com.jgt.xx.hrhelper.valid.CreateGroup;
import com.jgt.xx.hrhelper.valid.UpdateGroup;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户相关实体
 * 与角色是多对多关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_auth_user")
public class User extends BasePO {

  private static final long serialVersionUID = -6551862073400925769L;

  private static final String INIT_PWD = "jgt8888";

  /**
   *  登录用户名
   *  有效数据中需要保证唯一性
   */
  @NotBlank(groups = {CreateGroup.class},message = "登录名不可为空！")
  @Column(nullable = false,length = 40,unique = true)
  private String loginName;

  /**
   *  用户昵称，用于显示用，所有相关报表都显示该值
   *  有效数据中，需要保证唯一性
   */
  @NotBlank(groups = {CreateGroup.class,UpdateGroup.class},message = "昵称不可为空!")
  @Column(nullable = false,length = 40)
  private String nickName;

  /**
   * 用户邮件
   */
  @NotBlank(groups = {CreateGroup.class,UpdateGroup.class},message = "邮箱不可为空！")
  @Email(message = "邮箱格式错误！")
  private String email;

  /**
   * 加密存储后的密码，不可逆
   */
  @JsonIgnore
  @Column(nullable = false)
  private String password = initPwd();

  /**
   * 用户状态
   * 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户
   * 1:正常状态,
   * 2：用户被锁定
   */
  private short state = 1;

  /**
   * 用户的角色集合
   * 多对多关系
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="t_auth_user_role",
      joinColumns = {@JoinColumn(name = "userId",referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "roleId",referencedColumnName = "id")})
  private List<Role> roleList;

  /**
   * 新建用户信息时初始化用户密码
   * Spring官方推荐使用BCryptPasswordEncoder加密时，无需考虑Salt的使用
   * @return 经过BCryptPasswordEncoder加密的结果
   */
  public final String initPwd(){
    BCryptPasswordEncoder bcryEncoder = new BCryptPasswordEncoder();
    return bcryEncoder.encode(INIT_PWD);
  }

//  /**
//   * 根据传输过来的User创建保存时需要的User
//   * @param user
//   * @return
//   */
//  public final User createNewUser(User user){
//    User userResult = new User();
//    userResult.setLoginName(user.getLoginName());
//    userResult.setNickName(user.getNickName());
//    userResult.setEmail(user.getEmail());
//    userResult.setState(user.getState());
//    userResult.setRemark(user.getRemark());
//    return  userResult;
//  }
}
