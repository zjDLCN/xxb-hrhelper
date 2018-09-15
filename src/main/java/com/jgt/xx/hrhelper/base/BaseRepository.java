package com.jgt.xx.hrhelper.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础Dao层接口，封装了普通CRUD以及特别查询
 * @param <T>  查询实体
 * @param <PK>  主键类型
 */
@NoRepositoryBean
public interface BaseRepository<T,PK>
    extends JpaRepository<T,PK> ,
    JpaSpecificationExecutor<T> {

}
