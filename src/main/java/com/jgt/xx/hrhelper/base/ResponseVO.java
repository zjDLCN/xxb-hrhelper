package com.jgt.xx.hrhelper.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseVO {
  private int code;
  private String msg;
  private Object data;

  private ResponseVO(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  private ResponseVO(int code, String msg,Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static ResponseVO success() {
    return new ResponseVO(1, "success");
  }

  public static ResponseVO success(Object data) {
    return new ResponseVO(1, "success",data);
  }

  public static ResponseVO fail() {
    return new ResponseVO(40001, "fail");
  }
}
