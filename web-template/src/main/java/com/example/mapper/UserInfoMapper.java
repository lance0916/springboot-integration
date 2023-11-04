package com.example.mapper;

import java.util.List;

import com.example.bean.entity.UserInfo;
import com.example.bean.entity.UserInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectOneByExample(UserInfoExample example);

    UserInfo selectOneByExampleSelective(@Param("example") UserInfoExample example,
        @Param("selective") UserInfo.Column... selective);

    List<UserInfo> selectByExampleSelective(@Param("example") UserInfoExample example,
        @Param("selective") UserInfo.Column... selective);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") UserInfo.Column... selective);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int batchInsert(@Param("list") List<UserInfo> list);

    int batchInsertSelective(@Param("list") List<UserInfo> list, @Param("selective") UserInfo.Column... selective);
}