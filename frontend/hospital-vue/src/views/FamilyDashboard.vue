<template>
  <div class="family-dashboard">
    <el-header class="header">
      <h2>家属工作台</h2>
      <div class="header-right">
        <span class="welcome-user">欢迎 {{ userId }}</span>
        <el-button type="text" @click="goLogin" style="margin-left: 18px;">返回登录</el-button>
      </div>
    </el-header>
    <div class="main-container">
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" class="menu" @select="handleMenuSelect">
          <el-menu-item index="1">
            <el-icon><Document /></el-icon>
            <span>患者信息</span>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Files /></el-icon>
            <span>费用缴纳</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main">
        <div v-if="activeMenu === '1'">
          <div class="section">
            <h3>未呼叫患者信息</h3>
            <el-table :data="uncalledPatients" style="width: 100%; margin-bottom: 30px;">
              <el-table-column prop="patientId" label="患者ID" />
              <el-table-column prop="bedId" label="床位ID" />
              <el-table-column prop="wardId" label="病房ID" />
              <el-table-column prop="bedNumber" label="床号" />
              <el-table-column prop="wardName" label="病房名称" />
              <el-table-column prop="callStatus" label="呼叫状态" />
              <el-table-column label="操作" width="120">
                <template #default="scope">
                  <el-button 
                    type="danger" 
                    size="small" 
                    @click="handleCall(scope.row.patientId)"
                  >
                    呼叫
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div v-if="uncalledPatients.length === 0" style="color:#999; margin-top: 20px;">
              暂无未呼叫的患者
            </div>

            <h3 style="margin-top: 40px;">呼叫中患者信息</h3>
            <el-table :data="calledPatients" style="width: 100%; margin-bottom: 30px;">
              <el-table-column prop="patientId" label="患者ID" />
              <el-table-column prop="bedId" label="床位ID" />
              <el-table-column prop="wardId" label="病房ID" />
              <el-table-column prop="bedNumber" label="床号" />
              <el-table-column prop="wardName" label="病房名称" />
              <el-table-column prop="callStatus" label="呼叫状态" />
            </el-table>
            <div v-if="calledPatients.length === 0" style="color:#999; margin-top: 20px;">
              暂无呼叫中的患者
            </div>
          </div>
        </div>
        <div v-else-if="activeMenu === '2'">
          <div class="section">
            <div class="balance-section">
              <h3>账户余额</h3>
              <div class="balance-amount">¥ {{ accountBalance.toFixed(2) }}</div>
            </div>

            <div class="payment-section">
              <h3>费用清单</h3>
              <el-table :data="expenseList" style="width: 100%; margin-top: 20px;">
                <el-table-column prop="date" label="日期" width="180" />
                <el-table-column prop="type" label="费用类型" width="120" />
                <el-table-column prop="amount" label="金额" width="120">
                  <template #default="scope">
                    ¥ {{ scope.row.amount.toFixed(2) }}
                  </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" width="100">
                  <template #default="scope">
                    <el-tag :type="scope.row.status === '已支付' ? 'success' : 'warning'">
                      {{ scope.row.status }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="description" label="说明" />
              </el-table>

              <div class="payment-form" style="margin-top: 20px;">
                <el-form :model="paymentForm" :rules="paymentRules" ref="paymentFormRef" label-width="100px">
                  <el-form-item label="缴费金额" prop="amount">
                    <el-input-number
                      v-model="paymentForm.amount"
                      :min="0"
                      :precision="2"
                      style="width: 200px"
                    />
                  </el-form-item>
                  <el-form-item label="支付方式" prop="paymentMethod">
                    <el-select v-model="paymentForm.paymentMethod" style="width: 200px">
                      <el-option label="现金" value="cash" />
                      <el-option label="银行卡" value="card" />
                      <el-option label="微信支付" value="wechat" />
                      <el-option label="支付宝" value="alipay" />
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="handlePayment" :loading="paymentLoading">
                      确认缴费
                    </el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>
          </div>
        </div>
      </el-main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Document, Files } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

interface Patient {
  id: string;
  name: string;
  gender: string;
  age: number;
  phone: string;
  idCard: string;
  address: string;
  roomNumber: string;
  bedNumber: string;
  admissionTime: string;
  doctorName: string;
  nurseName: string;
  status: string;
  medicalOrders: MedicalOrder[];
}

interface MedicalOrder {
  id: string;
  time: string;
  content: string;
  type: 'primary' | 'success' | 'warning' | 'danger' | 'info';
}

interface Expense {
  id: string;
  date: string;
  type: string;
  amount: number;
  status: string;
  description: string;
}

const router = useRouter();
const goLogin = () => {
  router.push('/login');
};
const activeMenu = ref('1');
const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
};

// 获取当前登录账号（假设 userId 就是患者ID）
const userId = localStorage.getItem('userId') || '';

// 患者信息（自动获取，无需搜索） - patientInfo 似乎不再使用，可以删除或忽略
// const patientInfo = ref<any | null>(null); // Adjusted type to any | null

// 分别存储未呼叫和呼叫中的患者信息
const uncalledPatients = ref<any[]>([]);
const calledPatients = ref<any[]>([]);

// 修改 fetchPatientCalls 方法，分别调用接口获取未呼叫和呼叫中的患者信息
const fetchPatientCalls = async () => {
  try {
    // 获取未呼叫患者信息（按用户ID）
    const uncalledRes = await axios.get(`http://localhost:8080/api/patient-nocalls`, {
      params: { patientId: userId }
    });
    uncalledPatients.value = uncalledRes.data;

    // 获取所有呼叫中患者信息，然后在前端按用户ID过滤
    const calledRes = await axios.get(`http://localhost:8080/api/patient-calls`);
    calledPatients.value = calledRes.data.filter(item => item.patientId === userId);

  } catch (e) {
    uncalledPatients.value = [];
    calledPatients.value = [];
    ElMessage.error('获取患者呼叫信息失败');
    console.error(e);
  }
};

// 呼叫按钮方法，将呼叫状态改为"呼叫"
const handleCall = async (patientId: string) => {
  try {
    await axios.put(`http://localhost:8080/api/patient-calls/${patientId}/request`);
    ElMessage.success('呼叫请求已发送');
    fetchPatientCalls(); // 刷新列表
  } catch (e) {
    ElMessage.error('发送呼叫请求失败');
     console.error(e);
  }
};

// 费用缴纳相关
const accountBalance = ref(0);
const expenseList = ref<any[]>([]); // Adjusted type to any[]
const paymentFormRef = ref();
const paymentForm = ref({
  amount: 0,
  paymentMethod: 'cash'
});
const paymentLoading = ref(false);

const paymentRules = {
  amount: [
    { required: true, message: '请输入缴费金额', trigger: 'blur' },
    { type: 'number', min: 0, message: '金额必须大于0', trigger: 'blur' }
  ],
  paymentMethod: [
    { required: true, message: '请选择支付方式', trigger: 'change' }
  ]
};

// 获取账户余额
const fetchAccountBalance = async () => {
  try {
    const res = await axios.get<{ balance: number }>('http://localhost:8080/family/account/balance');
    accountBalance.value = res.data.balance;
  } catch (e) {
    accountBalance.value = 0;
     ElMessage.error('获取账户余额失败');
     console.error(e);
  }
};

// 获取费用清单
const fetchExpenseList = async () => {
  try {
    const res = await axios.get<any[]>('http://localhost:8080/family/expenses'); // Adjusted type to any[]
    expenseList.value = res.data;
  } catch (e) {
    expenseList.value = [];
     ElMessage.error('获取费用清单失败');
     console.error(e);
  }
};

// 处理缴费
const handlePayment = () => {
  paymentFormRef.value.validate(async (valid: boolean) => {
    if (!valid) return;
    paymentLoading.value = true;
    try {
      const res = await axios.post<{ success: boolean; message: string; balance: number }>(
        'http://localhost:8080/family/payments',
        paymentForm.value
      );
      if (res.data.success) {
        ElMessage.success(res.data.message);
        accountBalance.value = res.data.balance;
        fetchExpenseList();
        paymentForm.value.amount = 0;
      } else {
        ElMessage.error(res.data.message);
      }
    } catch (e) {
      ElMessage.error('缴费失败');
      console.error(e);
    } finally {
      paymentLoading.value = false;
    }
  });
};

onMounted(() => {
  fetchPatientCalls(); // Fetch all patient calls for this user
  // patientInfo, accountBalance, and expenseList fetches were part of original template logic,
  // keep them if still needed for other parts of the family dashboard not shown in the screenshot.
  // fetchPatientInfo();
  // fetchAccountBalance();
  // fetchExpenseList();
});
</script>

<style scoped>
.family-dashboard {
  height: 100vh;
  display: flex;
  flex-direction: column;
}
.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between; /* Added to space out title and right section */
}
.header-right {
  display: flex;
  align-items: center;
}
.welcome-user {
  font-size: 16px;
  color: #888;
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
  flex: 1;
  overflow-y: auto;
}
.section {
  background-color: #fff;
  border-radius: 4px;
  padding: 20px;
}
.search-section { display: none; }
.patient-info {
  margin-top: 20px;
}
.balance-section {
  text-align: center;
  margin-bottom: 30px;
}
.balance-amount {
  font-size: 36px;
  color: #409EFF;
  font-weight: bold;
  margin-top: 10px;
}
.payment-section {
  margin-top: 30px;
}
.medical-orders {
  background-color: #f5f7fa;
  padding: 20px;
  border-radius: 4px;
}
.medical-orders h4 {
  margin-bottom: 15px;
  color: #606266;
}
</style> 