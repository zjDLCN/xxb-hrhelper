package com.jgt.xx.hrhelper.repo.auth;

import com.jgt.xx.hrhelper.base.BaseRepository;
import com.jgt.xx.hrhelper.model.auth.Permission;
import org.springframework.stereotype.Repository;

/**
 * 权限实体操作
 */
@Repository
public interface PermissionRepository
    extends BaseRepository<Permission,Long> {

}
