package com.example.hospital.controller;

import com.example.hospital.entity.User;
import com.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 用户账号控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户账号信息
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 根据用户ID查询用户信息
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 添加新用户
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.isUsernameExists(user.getUsername())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }

        int result = userService.addUser(user);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "添加用户成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "添加用户失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
        // 设置用户ID
        user.setUserId(userId);
        
        // 如果要更改用户名，检查新用户名是否已存在
        User existingUser = userService.getUserById(userId);
        if (existingUser != null && !existingUser.getUsername().equals(user.getUsername()) 
            && userService.isUsernameExists(user.getUsername())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "新用户名已存在");
            return ResponseEntity.badRequest().body(response);
        }

        int result = userService.updateUser(user);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "更新用户信息成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "更新用户信息失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 删除用户信息
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        int result = userService.deleteUser(userId);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "删除用户成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "删除用户失败");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 更新用户最后登录时间
     */
    @PutMapping("/{userId}/last-login")
    public ResponseEntity<?> updateLastLoginTime(@PathVariable String userId) {
        int result = userService.updateLastLoginTime(userId);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "更新最后登录时间成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "更新最后登录时间失败");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 