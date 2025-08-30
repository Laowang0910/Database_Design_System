package com.example.hospital.mapper;

import com.example.hospital.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 用户账号数据访问层接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 获取所有用户账号信息
     */
    @Select("SELECT * FROM `用户账号表`")
    List<User> getAllUsers();

    /**
     * 根据用户ID查询用户信息
     */
    @Select("SELECT * FROM `用户账号表` WHERE `user_id` = #{userId}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "roleType", column = "role_type"),
        @Result(property = "staffId", column = "staff_id"),
        @Result(property = "lastLoginTime", column = "last_login_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    User getUserById(String userId);

    /**
     * 根据用户名查询用户信息
     */
    @Select("SELECT * FROM `用户账号表` WHERE `username` = #{username}")
    User getUserByUsername(String username);

    /**
     * 插入新用户信息
     */
    @Insert("INSERT INTO `用户账号表` (`user_id`, `username`, `password`, `role_type`, `staff_id`, " +
            "`status`, `last_login_time`, `create_time`, `update_time`) " +
            "VALUES (#{userId}, #{username}, #{password}, #{roleType}, #{staffId}, " +
            "#{status}, #{lastLoginTime}, #{createTime}, #{updateTime})")
    int insertUser(User user);

    /**
     * 更新用户信息
     */
    @Update("UPDATE `用户账号表` SET " +
            "`username` = #{username}, " +
            "`password` = #{password}, " +
            "`role_type` = #{roleType}, " +
            "`staff_id` = #{staffId}, " +
            "`status` = #{status}, " +
            "`update_time` = NOW() " +
            "WHERE `user_id` = #{userId}")
    int updateUser(User user);

    /**
     * 删除用户信息
     */
    @Delete("DELETE FROM `用户账号表` WHERE `user_id` = #{userId}")
    int deleteUser(String userId);

    /**
     * 更新用户最后登录时间
     */
    @Update("UPDATE `用户账号表` SET `last_login_time` = #{lastLoginTime}, `update_time` = #{lastLoginTime} " +
            "WHERE `user_id` = #{userId}")
    int updateLastLoginTime(@Param("userId") String userId, @Param("lastLoginTime") LocalDateTime lastLoginTime);
} 