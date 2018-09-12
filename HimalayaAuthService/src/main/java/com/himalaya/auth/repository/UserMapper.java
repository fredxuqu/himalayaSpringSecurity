package com.himalaya.auth.repository;

import com.himalaya.auth.domain.UserDO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
    
    UserDO selectUserByAppId(@Param("appId") String appId);
}