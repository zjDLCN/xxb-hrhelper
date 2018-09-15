package com.jgt.xx.hrhelper.base;

import org.springframework.data.domain.Page;

/**
 * 基础服务层封装
 */
public interface BaseServcie<T,PK> {

  /**
   * 分页获取全部数据
   * @param pageNo 查询页数
   * @param pageSize 每页数据数
   * @return 当前页的数据集合
   */
  Page<T> findDataList(int pageNo, int pageSize,T params);

  /**
   * 保存数据
   *
   * @param data 需要插入的数据
   * @return 保存后的数据
   */
  T insertData(T data);

  /**
   * 保存数据
   * @param data 修改数据实体，必须包含主键
   * @return
   */
  T updateData(T data);

  /**
   * 数据逻辑删除
   * @param id 删除数据的主键
   * @return 是否删除成功
   */
  Boolean deleteData(PK id);

  /**
   * 数据逻辑删除后恢复
   * @param id 数据主键
   * @return 是否恢复成功
   */
  Boolean resetValid(PK id);
}
