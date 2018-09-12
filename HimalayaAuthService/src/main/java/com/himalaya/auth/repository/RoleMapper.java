package com.himalaya.auth.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.himalaya.auth.domain.RoleDO;

public interface RoleMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(RoleDO record);

    int insertSelective(RoleDO record);

    RoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleDO record);

    int updateByPrimaryKey(RoleDO record);
    
    List<RoleDO> listRoleByAppId(@Param(value="appId")String appId);
    
    
}