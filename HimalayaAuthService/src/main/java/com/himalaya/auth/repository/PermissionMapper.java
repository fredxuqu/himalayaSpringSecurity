package com.himalaya.auth.repository;

import com.himalaya.auth.domain.PermissionDO;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    PermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);

    // Added by fred
    List<PermissionDO> getAllPermission();
}