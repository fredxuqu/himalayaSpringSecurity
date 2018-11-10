package com.himalaya.auth.repository;

import com.himalaya.auth.domain.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(RoleDO record);

    int insertSelective(RoleDO record);

    RoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleDO record);

    int updateByPrimaryKey(RoleDO record);

    // added by fred
    List<String> listRoleNameByAppKey(@Param(value="appKey")String appKey);

    List<String> listRoleNameByPermissionId(@Param(value="permissionId")Long permissionId);
}