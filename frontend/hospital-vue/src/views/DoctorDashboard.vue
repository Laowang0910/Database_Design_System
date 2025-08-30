<template>
  <div class="doctor-dashboard">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h2>医生工作台</h2>
      </div>
      <el-button type="text" @click="goLogin" style="position:absolute;right:30px;top:14px;">返回登录</el-button>
      <div class="header-right">
        <span>当前医生：{{ doctorName }}</span>
        <el-badge :value="3" class="notification">
          <el-icon><Bell /></el-icon>
        </el-badge>
        <el-dropdown>
          <span class="user-info">
            <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <span>{{ staffId }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <div class="main-container">
      <!-- 左侧菜单 -->
      <el-aside width="200px" class="aside">
        <el-menu
          :default-active="activeMenu"
          class="menu"
          @select="handleMenuSelect"
        >
          <el-menu-item index="1">
            <el-icon><Calendar /></el-icon>
            <span>未就诊患者</span>
          </el-menu-item>
          
          <el-menu-item index="3">
            <el-icon><Document /></el-icon>
            <span>医嘱下达</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主要内容区 -->
      <el-main class="main">
        <div v-if="activeMenu === '1'">
          <!-- 数据统计区块 -->
          <el-row :gutter="20" style="margin-bottom: 20px;">
            <el-col :span="6">
              <el-card>
                <div>未就诊患者</div>
                <h2>{{ undiagnosedPatients.length }}</h2>
              </el-card>
            </el-col>
          </el-row>

          <div class="section">
            <div class="section-header">
              <h3>未就诊患者</h3>
              <div>
                <el-button @click="fetchUndiagnosedPatients">刷新</el-button>
                <el-button type="primary" @click="addPatientDialogVisible = true">添加患者</el-button>
              </div>
            </div>
            <!-- 添加患者弹窗 -->
            <el-dialog v-model="addPatientDialogVisible" title="添加未就诊患者" width="400px">
              <el-form :model="addPatientForm" :rules="addPatientFormRules" ref="addPatientFormRef" label-width="100px">
                <el-form-item label="患者编号" prop="patientId">
                  <el-input v-model="addPatientForm.patientId" placeholder="请输入患者编号"></el-input>
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="addPatientDialogVisible = false">取消</el-button>
                <el-button type="primary" :loading="addPatientLoading" @click="handleAddPatientConfirm">确定</el-button>
              </template>
            </el-dialog>

            <!-- 未就诊患者表格 -->
            <el-table :data="undiagnosedPatients" style="margin-top: 20px;">
              <el-table-column prop="patientName" label="姓名">
                <template #default="scope">
                  <el-link type="primary" @click="showPatientDetail(scope.row.patientId)">
                    {{ scope.row.patientName }}
                  </el-link>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button type="danger" size="small" @click="handleDeletePatient(scope.row.patientId)">完成就诊</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 患者详情弹窗 -->
          <el-dialog v-model="detailDialogVisible" title="患者详情" width="400px" :before-close="() => detailDialogVisible = false">
            <el-descriptions :column="1" v-if="patientDetail">
              <el-descriptions-item label="姓名">{{ patientDetail.name }}</el-descriptions-item>
              <el-descriptions-item label="性别">{{ patientDetail.gender }}</el-descriptions-item>
              <el-descriptions-item label="年龄">{{ patientDetail.age }}</el-descriptions-item>
              <el-descriptions-item label="电话">{{ patientDetail.phone }}</el-descriptions-item>
              <el-descriptions-item label="住址">{{ patientDetail.address }}</el-descriptions-item>
              <el-descriptions-item label="诊断">{{ patientDetail.diagnosis }}</el-descriptions-item>
            </el-descriptions>
          </el-dialog>
        </div>
        <div v-else-if="activeMenu === '3'">
          <div class="section">
            <h3>医嘱下达</h3>
            <el-table :data="prescriptionList" style="margin-top: 20px;">
              <el-table-column prop="prescriptionId" label="处方编号" width="100" />
              <el-table-column prop="patientId" label="患者ID" width="100" />
              <el-table-column prop="doctorId" label="医生ID" width="100" />
              <el-table-column prop="medicationName" label="药品名称" width="120" />
              <el-table-column prop="dosage" label="剂量" width="100" />
              <el-table-column prop="frequency" label="频率" width="100" />
              <el-table-column prop="instruction" label="备注" width="150" />
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="primary" @click="openOrderDialog(scope.row)">医嘱下达</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-dialog v-model="showOrderDialog" title="医嘱下达" width="500px">
              <el-form :model="orderForm">
                <el-form-item label="药品名称">
                  <el-input v-model="orderForm.medicationName" />
                </el-form-item>
                <el-form-item label="剂量">
                  <el-input v-model="orderForm.dosage" />
                </el-form-item>
                <el-form-item label="频率">
                  <el-input v-model="orderForm.frequency" />
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="orderForm.instruction" />
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="showOrderDialog = false">取消</el-button>
                <el-button type="primary" @click="submitOrder">提交</el-button>
              </template>
            </el-dialog>
          </div>
        </div>
      </el-main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import {
  Bell,
  Calendar,
  User,
  Document,
  Files,
  List,
  DataLine,
  Plus
} from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { useRouter } from 'vue-router';

interface PatientOption {
  id: string;
  name: string;
  gender: string;
  age: number;
  phone: string;
  address: string;
}

interface UndiagnosedPatient {
  patientId: string;
  patientName: string;
  doctorStaffId?: string; // 可选，如果需要也可以加上
  // 根据后端视图，可能还包含其他字段，如就诊状态 visitState
  visitState?: string;
}

// 当前选中的菜单
const activeMenu = ref('1');

// 假设当前医生编号
const staffId = 'S2025001';

// 未就诊患者列表
const undiagnosedPatients = ref<UndiagnosedPatient[]>([]);

// 添加患者对话框相关
const addPatientDialogVisible = ref(false);
const addPatientFormRef = ref();
const addPatientForm = ref({ patientId: '' });
const addPatientFormRules = {
  patientId: [{ required: true, message: '请输入患者编号', trigger: 'blur' }]
};
const addPatientLoading = ref(false);

// 患者详情弹窗
const detailDialogVisible = ref(false);
const patientDetail = ref<any>(null);

// 当前医生信息
const doctorName = ref('张医生');

const router = useRouter();

// 处理菜单选择
const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
};

// 获取未就诊患者
const fetchUndiagnosedPatients = async () => {
  try {
    const res = await axios.get<UndiagnosedPatient[]>(`http://localhost:8080/api/doctor-patient-visits/doctor/${staffId}`);
    undiagnosedPatients.value = res.data;
  } catch (e) {
    console.error('获取未就诊患者失败:', e);
    ElMessage.error('获取未就诊患者失败');
    undiagnosedPatients.value = [];
  }
};

onMounted(() => {
  fetchUndiagnosedPatients();
  // 获取当前登录的医生姓名
  const loggedInUsername = localStorage.getItem('username');
  if (loggedInUsername) {
    doctorName.value = loggedInUsername;
  }
});

// 处理添加患者确认
const handleAddPatientConfirm = () => {
  addPatientFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    addPatientLoading.value = true;
    try {
      // 调用后端API添加就诊记录
      await axios.post('http://localhost:8080/api/doctor-patient-visits', {
        staffId: staffId,
        patientId: addPatientForm.value.patientId
      });

      ElMessage.success('添加未就诊患者成功');
      addPatientDialogVisible.value = false;
      addPatientForm.value.patientId = ''; // 清空输入框
      fetchUndiagnosedPatients(); // 刷新列表
    } catch (e) {
      console.error('添加未就诊患者失败:', e);
      ElMessage.error('添加未就诊患者失败');
    } finally {
      addPatientLoading.value = false;
    }
  });
};

// 完成就诊
const handleDeletePatient = async (row: string) => {
  try {
    // 调用新的 PUT 接口更新状态
    await axios.put(`http://localhost:8080/api/doctor-patient-visits/${staffId}/${row}`);

    // 从本地状态中移除
    const index = undiagnosedPatients.value.findIndex(p => p.patientId === row);
    if (index > -1) {
      undiagnosedPatients.value.splice(index, 1);
    }
    
    ElMessage.success('完成就诊');
  } catch (e) {
    console.error('完成就诊失败:', e);
    ElMessage.error('完成就诊失败');
  }
};

// 查看患者详情
const showPatientDetail = async (patientId: string) => {
  try {
    const res = await axios.get(`http://localhost:8080/api/patients/${patientId}`);
    patientDetail.value = res.data;
    detailDialogVisible.value = true;
  } catch (e) {
    console.error('获取患者详情失败:', e);
    ElMessage.error('获取患者详情失败');
  }
};

const goLogin = () => {
  router.push('/login');
};

const prescriptionList = ref<any[]>([]);
const showOrderDialog = ref(false);
const orderForm = ref({
  medicationName: '',
  dosage: '',
  frequency: '',
  instruction: ''
});
let currentPrescriptionId = '';

const fetchPrescriptions = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/prescriptions');
    prescriptionList.value = res.data;
  } catch (e) {
    ElMessage.error('获取处方列表失败');
  }
};

function openOrderDialog(row: any) {
  currentPrescriptionId = row.prescriptionId;
  orderForm.value = {
    medicationName: row.medicationName || '',
    dosage: row.dosage || '',
    frequency: row.frequency || '',
    instruction: row.instruction || ''
  };
  showOrderDialog.value = true;
}

async function submitOrder() {
  try {
    await axios.put(`http://localhost:8080/api/prescriptions/${currentPrescriptionId}`, orderForm.value);
    ElMessage.success('医嘱下达成功');
    showOrderDialog.value = false;
    fetchPrescriptions();
  } catch (e) {
    ElMessage.error('医嘱下达失败');
  }
}

watch(activeMenu, (val) => {
  if (val === '3') fetchPrescriptions();
});
</script>

<style scoped>
.doctor-dashboard {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.notification {
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.main-container {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.aside {
  background-color: #fff;
  border-right: 1px solid #e6e6e6;
}

.menu {
  height: 100%;
  border-right: none;
}

.main {
  padding: 20px;
  background-color: #f5f7fa;
  overflow-y: auto;
}

.section {
  background-color: #fff;
  border-radius: 4px;
  padding: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.stat-card {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .card-content {
    text-align: center;
    
    h2 {
      font-size: 24px;
      margin: 10px 0;
      color: #409EFF;
    }

    p {
      color: #909399;
      font-size: 14px;
    }
  }
}
</style> 