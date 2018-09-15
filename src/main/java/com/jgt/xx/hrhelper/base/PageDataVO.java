package com.jgt.xx.hrhelper.base;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * 前端分页查询条件
 */
public class PageDataVO {
  private Long total;
  private Object rows;
}
