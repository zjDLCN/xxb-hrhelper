package com.jgt.xx.hrhelper.base;


import com.jgt.xx.hrhelper.valid.UpdateGroup;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 基础数据库映射实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasePO implements Serializable {

  private static final long serialVersionUID = 6979639041985963037L;
  /**
   * 主键，采用自增策略
   * 更换ORACLE数据库时，需要更换
   */
  @NotBlank(groups = {UpdateGroup.class})
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 数据是否有效
   */
  private Boolean valid = true;

  /**
   * 备注
   */
  @Lob
  @Column(length = 2000,columnDefinition = "text")
  private String remark;

  /**
   * 数据创建时间
   */
  @CreatedDate
  private LocalDateTime createdTime;

  /**
   * 数据最后修改时间
   */
  @LastModifiedDate
  private LocalDateTime updatedTime;
}
