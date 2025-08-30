<template>
  <div class="ward-management">
    <div class="section">
      <div class="section-header">
        <h3>病房管理</h3>
      </div>

      <!-- 科室选择 -->
      <div class="department-select">
        <el-select v-model="selectedDepartment" placeholder="选择科室" @change="loadWardData">
          <el-option
            v-for="dept in departments"
            :key="dept.value"
            :label="dept.label"
            :value="dept.value"
          />
        </el-select>
      </div>

      <!-- 病房和床位信息 -->
      <div class="ward-list" v-if="selectedDepartment">
        <el-card class="ward-info">
          <template #header>
            <div class="card-header">
              <span>{{ selectedDepartment }}病房信息</span>
              <div>
                <el-tag type="success">可用床位：{{ availableBedCount }}</el-tag>
              </div>
            </div>
          </template>
          
          <el-table :data="bedList" style="width: 100%">
            <el-table-column prop="bedNumber" label="床位号" width="100" />
            <el-table-column prop="bedStatus" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.bedStatus === '空闲' ? 'success' : 'danger'">
                  {{ scope.row.bedStatus }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="patientName" label="患者姓名" />
            <el-table-column prop="admissionTime" label="入院时间">
              <template #default="scope">
                {{ scope.row.admissionTime ? new Date(scope.row.admissionTime).toLocaleString() : '-' }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';

const selectedDepartment = ref('');
const departments = ref([
  { label: '内科', value: 'internal' },
  { label: '外科', value: 'surgery' },
  { label: '儿科', value: 'pediatrics' },
  { label: '妇产科', value: 'obstetrics' }
]);
const availableBedCount = ref(0);
const bedList = ref([]);

// 加载病房数据
const loadWardData = async () => {
  if (!selectedDepartment.value) return;
  
  try {
    // 获取可用床位数量
    const countResponse = await axios.get(`http://localhost:8080/api/bed-assignments/department/${selectedDepartment.value}/available-count`);
    availableBedCount.value = countResponse.data.availableBeds;

    // 获取所有床位信息
    const bedsResponse = await axios.get(`http://localhost:8080/api/bed-assignments/department/${selectedDepartment.value}/available-beds`);
    bedList.value = bedsResponse.data.availableBeds;
  } catch (error) {
    ElMessage.error('获取病房信息失败');
    console.error('Error loading ward data:', error);
  }
};

onMounted(() => {
  // 默认选择内科并加载数据
  selectedDepartment.value = 'internal';
  loadWardData();
});
</script>

<style scoped>
.ward-management {
  padding: 20px;
}

.section-header {
  margin-bottom: 20px;
}

.department-select {
  margin-bottom: 20px;
}

.ward-list {
  margin-top: 20px;
}

.ward-info {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-tag {
  margin-left: 10px;
}
</style> 