package com.example.hospital.service;

import com.example.hospital.entity.User;
import com.example.hospital.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户账号服务层
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户账号信息
     */
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    /**
     * 根据用户ID查询用户信息
     */
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    /**
     * 根据用户名查询用户信息
     */
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    /**
     * 添加新用户
     */
    public int addUser(User user) {
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        
        // 如果状态为空，设置默认状态为启用
        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            user.setStatus("启用");
        }
        
        return userMapper.insertUser(user);
    }

    /**
     * 更新用户信息
     */
    public int updateUser(User user) {
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateUser(user);
    }

    /**
     * 删除用户信息
     */
    public int deleteUser(String userId) {
        return userMapper.deleteUser(userId);
    }

    /**
     * 更新用户最后登录时间
     */
    public int updateLastLoginTime(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return userMapper.updateLastLoginTime(userId, now);
    }

    /**
     * 检查用户名是否已存在
     */
    public boolean isUsernameExists(String username) {
        return userMapper.getUserByUsername(username) != null;
    }

    /**
     * 用户登录
     * @return 登录成功返回用户信息，失败返回null
     */
    public User login(String userId, String password) {
        User user = userMapper.getUserById(userId);
        
        if (user != null && password.equals(user.getPassword()) && "启用".equals(user.getStatus())) {
            // 更新最后登录时间
            userMapper.updateLastLoginTime(userId, LocalDateTime.now());
            return user;
        }
        
        return null;
    }
} 