<template>
  <div class="login-bg">
    <div class="login-center">
      <el-card class="login-card">
        <h2 class="login-title">医院管理系统登录</h2>
        <el-form :model="form" class="login-form">
          <el-form-item label="账号类型">
            <el-select v-model="form.role" placeholder="请选择账号类型" @change="handleRoleChange" style="width: 100%">
              <el-option label="医生" value="doctor" />
              <el-option label="管理员" value="admin" />
              <el-option label="护士" value="nurse" />
              <el-option label="家属" value="family" />
              <el-option label="财务" value="finance" />
            </el-select>
          </el-form-item>
          <el-form-item label="账号">
            <el-input v-model="form.username" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%; font-size: 16px;" @click="handleLogin">登录</el-button>
          </el-form-item>
        </el-form>
        <div class="login-members">
          <div class="members-title">成员</div>
          <div class="members-list">
            <div><span class="member-id">232210901115</span> <span class="member-name">李盛厚</span></div>
            <div><span class="member-id">232211901228</span> <span class="member-name">石岩山</span></div>
            <div><span class="member-id">232241807629</span> <span class="member-name">尹耀康</span></div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const router = useRouter();

const form = ref({
  role: '',
  username: '',
  password: ''
});

const handleLogin = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      userId: form.value.username,
      password: form.value.password
    });

    // 保存用户信息和token
    const { userId, username, roleType, token, route } = response.data;
    localStorage.setItem('userId', userId);
    localStorage.setItem('username', username);
    localStorage.setItem('roleType', roleType);
    localStorage.setItem('token', token);

    ElMessage.success('登录成功');
    
    // 根据后端返回的路由进行跳转
    router.push(route);
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '登录失败');
  }
};

const handleRoleChange = (val: string) => {
  // 可根据选择的角色做不同处理
};
</script>

<style scoped>
.login-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #e0e7ff 0%, #f5f7fa 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-center {
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.login-card {
  width: 400px;
  padding: 40px 30px 30px 30px;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.15);
  border-radius: 16px;
}
.login-title {
  text-align: center;
  margin-bottom: 30px;
  font-size: 28px;
  font-weight: bold;
  color: #409eff;
  letter-spacing: 2px;
}
.login-form {
  margin-top: 20px;
}
.el-form-item {
  margin-bottom: 24px;
}
.login-members {
  margin-top: 18px;
  text-align: center;
  color: #666;
  font-size: 14px;
}
.members-title {
  font-weight: bold;
  color: #409eff;
  margin-bottom: 6px;
  font-size: 16px;
  letter-spacing: 2px;
}
.members-list {
  display: flex;
  flex-direction: column;
  gap: 2px;
  align-items: center;
}
.member-id {
  font-family: monospace;
  margin-right: 8px;
  color: #888;
}
.member-name {
  font-weight: 500;
  color: #222;
}
</style> 