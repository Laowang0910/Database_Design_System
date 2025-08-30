package com.example.hospital.controller;

import com.example.hospital.entity.User;
import com.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            logger.info("收到登录请求: {}", loginRequest);
            
            String userId = loginRequest.get("userId");
            String password = loginRequest.get("password");
            
            if (userId == null || password == null) {
                logger.warn("登录参数不完整: userId={}, password={}", userId, password);
                Map<String, String> error = new HashMap<>();
                error.put("message", "用户名和密码不能为空");
                return ResponseEntity.badRequest().body(error);
            }
            
            logger.info("开始验证用户: {}", userId);
            User user = userService.login(userId, password);
            
            if (user != null) {
                logger.info("用户登录成功: {}", user.getUserId());
                Map<String, Object> response = new HashMap<>();
                response.put("userId", user.getUserId());
                response.put("username", user.getUsername());
                response.put("roleType", user.getRoleType());
                response.put("token", generateToken(user));
                
                String route = getRouteByRole(user.getRoleType());
                response.put("route", route);
                
                return ResponseEntity.ok(response);
            } else {
                logger.warn("用户登录失败: {}", userId);
                Map<String, String> error = new HashMap<>();
                error.put("message", "用户名或密码错误");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            logger.error("登录过程发生错误", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "服务器内部错误: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    private String generateToken(User user) {
        // TODO: 实现真正的JWT token生成
        return user.getUserId();
    }
    
    private String getRouteByRole(String roleType) {
        logger.info("根据角色类型获取路由: {}", roleType);
        switch (roleType) {
            case "管理员":
                return "/admin/dashboard";
            case "医生":
                return "/doctor/dashboard";
            case "护士":
                return "/nurse/dashboard";
            case "家属":
                return "/family/dashboard";
            case "财务":
                return "/finance/dashboard";
            default:
                logger.warn("未知的角色类型: {}", roleType);
                return "/login";
        }
    }
} 