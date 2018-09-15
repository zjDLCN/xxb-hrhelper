package com.jgt.xx.hrhelper.model.auth;

import com.jgt.xx.hrhelper.base.BasePO;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_auth_org")
public class Organization extends BasePO {

}
