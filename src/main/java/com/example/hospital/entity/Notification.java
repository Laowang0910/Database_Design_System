package com.example.hospital.entity;

import java.time.LocalDateTime;

/**
 * 通知消息实体类
 * 对应数据库中的通知消息表
 */
public class Notification {
    
    // 通知编号
    private String notificationId;
    
    // 通知标题
    private String title;
    
    // 通知内容
    private String content;
    
    // 通知类型（系统通知/工作安排/紧急通知）
    private String notificationType;
    
    // 发送人编号
    private String senderId;
    
    // 接收人编号（可多个，用逗号分隔）
    private String receiverIds;
    
    // 发送时间
    private LocalDateTime sendTime;
    
    // 通知状态（未读/已读/已处理）
    private String notificationStatus;
    
    // 优先级（普通/重要/紧急）
    private String priority;
    
    // 备注
    private String remarks;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(String receiverIds) {
        this.receiverIds = receiverIds;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
} 