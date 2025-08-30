<template>
  <el-container style="min-height: 100vh;">
    <template v-if="!isLoginPage">
      <el-header>
        <el-menu :default-active="activeMenu" mode="horizontal" @select="onMenuSelect">
          <el-menu-item v-if="route.path.startsWith('/doctor')" index="/doctor">医生管理</el-menu-item>
          <el-menu-item v-else-if="route.path.startsWith('/nurse')" index="/nurse">护士管理</el-menu-item>
          <el-menu-item v-else-if="route.path.startsWith('/family')" index="/family">家属管理</el-menu-item>
          <el-menu-item v-else-if="route.path.startsWith('/finance')" index="/finance">财务管理</el-menu-item>
          <el-menu-item v-else-if="route.path.startsWith('/admin')" index="/admin">管理员管理</el-menu-item>
        </el-menu>
      </el-header>
    </template>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import { computed } from 'vue';
const route = useRoute();
const router = useRouter();
const activeMenu = computed(() => route.path);
const onMenuSelect = (index: string) => {
  router.push(index);
};
const isLoginPage = computed(() => route.path === '/' || route.path === '/login');
</script> 