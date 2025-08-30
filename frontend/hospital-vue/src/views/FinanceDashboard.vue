<template>
  <div class="finance-dashboard">
    <el-header class="header">
      <h2>财务工作台</h2>
      <el-button type="text" @click="goLogin" style="position:absolute;right:30px;top:14px;">返回登录</el-button>
    </el-header>
    <div class="main-container">
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" class="menu" @select="handleMenuSelect">
          <el-menu-item index="1">
            <el-icon><Document /></el-icon>
            <span>设备信息</span>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Files /></el-icon>
            <span>设备采购</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main">
        <!-- 设备信息 -->
        <div v-if="activeMenu === '1'">
          <div class="section">
            <h3>设备信息</h3>
            <el-table :data="deviceList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="deviceId" label="设备编号" width="120" />
              <el-table-column prop="deviceName" label="设备名称" width="160" />
              <el-table-column prop="deviceType" label="设备类型" width="120" />
              <el-table-column prop="deviceStatus" label="状态" width="100" />
              <el-table-column prop="wardId" label="存放位置" />
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button size="small" @click="handleEditDevice(scope.row.deviceId)">编辑</el-button>
                  <el-button size="small" type="danger" @click="handleDeleteDevice(scope.row.deviceId)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <!-- 设备采购 -->
        <div v-else-if="activeMenu === '2'">
          <div class="section">
            <h3>设备采购</h3>
            <el-form :model="purchaseForm" :rules="purchaseRules" ref="purchaseFormRef" label-width="100px" style="max-width: 600px;">
              <el-form-item label="设备编号" prop="id">
                <el-input v-model="purchaseForm.id" />
              </el-form-item>
              <el-form-item label="设备名称" prop="name">
                <el-input v-model="purchaseForm.name" />
              </el-form-item>
              <el-form-item label="设备类型" prop="type">
                <el-input v-model="purchaseForm.type" />
              </el-form-item>
              <el-form-item label="型号" prop="modelNumber">
                <el-input v-model="purchaseForm.modelNumber" />
              </el-form-item>
              <el-form-item label="生产厂家" prop="manufacturer">
                <el-input v-model="purchaseForm.manufacturer" />
              </el-form-item>
              <el-form-item label="状态" prop="status">
                <el-input v-model="purchaseForm.status" />
              </el-form-item>
              <el-form-item label="存放位置" prop="location">
                <el-input v-model="purchaseForm.location" />
              </el-form-item>
              <el-form-item label="购买日期" prop="purchaseDate">
                <el-date-picker v-model="purchaseForm.purchaseDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
              </el-form-item>
              <el-form-item label="保修日期" prop="warrantyDate">
                <el-date-picker v-model="purchaseForm.warrantyDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
              </el-form-item>
              <el-form-item label="采购数量" prop="quantity">
                <el-input-number v-model="purchaseForm.quantity" :min="1" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleAddPurchase">添加采购</el-button>
              </el-form-item>
            </el-form>
            <el-table :data="purchaseList" style="width: 100%; margin-top: 30px;">
              <el-table-column prop="id" label="设备编号" width="120" />
              <el-table-column prop="name" label="设备名称" width="120" />
              <el-table-column prop="type" label="设备类型" width="120" />
              <el-table-column prop="status" label="状态" width="100" />
              <el-table-column prop="location" label="存放位置" width="120" />
              <el-table-column prop="quantity" label="采购数量" width="100" />
              <el-table-column label="操作" width="100">
                <template #default="scope">
                  <el-button type="danger" size="small" @click="handleDeletePurchase(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-main>
    </div>

    <!-- 编辑设备模态框 -->
    <el-dialog title="编辑设备信息" v-model="editDialogVisible" width="500px">
      <el-form v-if="editingDevice" :model="editingDevice" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="设备编号" prop="deviceId">
          <el-input v-model="editingDevice.deviceId" disabled />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="editingDevice.deviceName" />
        </el-form-item>
        <el-form-item label="设备类型" prop="deviceType">
          <el-input v-model="editingDevice.deviceType" />
        </el-form-item>
        <el-form-item label="状态" prop="deviceStatus">
          <el-input v-model="editingDevice.deviceStatus" />
        </el-form-item>
        <el-form-item label="存放位置" prop="wardId">
           <el-input v-model="editingDevice.wardId" />
        </el-form-item>
         <el-form-item label="型号" prop="modelNumber">
           <el-input v-model="editingDevice.modelNumber" />
         </el-form-item>
         <el-form-item label="制造商" prop="manufacturer">
           <el-input v-model="editingDevice.manufacturer" />
         </el-form-item>
         <el-form-item label="购买日期" prop="purchaseDate">
           <el-date-picker v-model="editingDevice!.purchaseDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
         </el-form-item>
          <el-form-item label="保修日期" prop="warrantyDate">
            <el-date-picker v-model="editingDevice!.warrantyDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
          </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveEdit">保存</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Document, Files, Warning } from '@element-plus/icons-vue';
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElButton, ElTable, ElTableColumn, ElInputNumber, ElSelect, ElOption, ElTag, ElDatePicker } from 'element-plus';
import axios from 'axios';

interface Device {
  deviceId: string;
  deviceName: string;
  deviceType: string;
  modelNumber: string | null;
  manufacturer: string | null;
  purchaseDate: string | null;
  warrantyDate: string | null;
  wardId: string | null;
  deviceStatus: string;
  createTime: string;
  updateTime: string;
}

interface PurchaseForm {
  id: string;
  name: string;
  type: string;
  status: string;
  location: string;
  quantity: number;
  modelNumber: string | null;
  manufacturer: string | null;
  purchaseDate: string | null;
  warrantyDate: string | null;
}

const router = useRouter();
const goLogin = () => {
  router.push('/login');
};
const activeMenu = ref('1');
const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
};

// 设备信息
const deviceList = ref<Device[]>([]);
const editDialogVisible = ref(false);
const editingDevice = ref<Device | null>(null);
const editFormRef = ref();
const editFormRules = {
  deviceName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  deviceType: [{ required: true, message: '请输入设备类型', trigger: 'blur' }],
  deviceStatus: [{ required: true, message: '请输入设备状态', trigger: 'blur' }],
};
const fetchDeviceList = async () => {
  try {
    const res = await axios.get<Device[]>('http://localhost:8080/api/medical-devices');
    deviceList.value = res.data;
  } catch (e) {
    deviceList.value = [];
  }
};

// 处理编辑设备
const handleEditDevice = async (deviceId: string) => {
  try {
    const res = await axios.get<Device>(`http://localhost:8080/api/medical-devices/${deviceId}`);
    editingDevice.value = res.data;
    editDialogVisible.value = true;
  } catch (e) {
    ElMessage.error('获取设备信息失败');
    console.error(e);
  }
};

// 处理删除设备
const handleDeleteDevice = async (deviceId: string) => {
  try {
    await axios.delete(`http://localhost:8080/api/medical-devices/${deviceId}`);
    ElMessage.success('设备删除成功');
    fetchDeviceList(); // 刷新列表
  } catch (e) {
    ElMessage.error('设备删除失败');
    console.error(e);
  }
};

// 处理保存编辑
const handleSaveEdit = async () => {
  if (!editFormRef.value || !editingDevice.value) {
    ElMessage.error('当前没有可保存的设备信息');
    return;
  }

  const deviceToUpdate: any = { ...editingDevice.value };
  if (deviceToUpdate.purchaseDate instanceof Date) {
    deviceToUpdate.purchaseDate = deviceToUpdate.purchaseDate.toISOString().split('T')[0];
  }
  if (deviceToUpdate.warrantyDate instanceof Date) {
    deviceToUpdate.warrantyDate = deviceToUpdate.warrantyDate.toISOString().split('T')[0];
  }

  editFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        const res = await axios.put(`http://localhost:8080/api/medical-devices/${deviceToUpdate.deviceId}`, deviceToUpdate);
        if (res.data.success) {
          ElMessage.success('设备信息更新成功');
          editDialogVisible.value = false;
          fetchDeviceList(); // 刷新列表
        } else {
          ElMessage.error(res.data.message || '设备信息更新失败');
        }
      } catch (e) {
        ElMessage.error('设备信息更新失败');
        console.error(e);
      }
    } else {
      ElMessage.error('表单验证失败，请检查输入项');
    }
  });
};

// 设备采购
const purchaseFormRef = ref();
const purchaseForm = ref<PurchaseForm>({ id: '', name: '', type: '', status: '', location: '', quantity: 1, modelNumber: null, manufacturer: null, purchaseDate: null, warrantyDate: null });
const purchaseRules = {
  id: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  type: [{ required: true, message: '请输入设备类型', trigger: 'blur' }],
  modelNumber: [],
  manufacturer: [],
  status: [{ required: true, message: '请输入设备状态', trigger: 'blur' }],
  location: [{ required: true, message: '请输入存放位置', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入采购数量', trigger: 'blur' }],
  purchaseDate: [],
  warrantyDate: [],
};
const purchaseList = ref<PurchaseForm[]>([]);
const handleAddPurchase = () => {
  purchaseFormRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    try {
      // Create a new device object based on the form data.
      // Note: The backend expects a MedicalDevice object, which has more fields.
      // We will map the purchase form fields to the MedicalDevice fields.
      const newDevice = {
        deviceId: purchaseForm.value.id,
        deviceName: purchaseForm.value.name,
        deviceType: purchaseForm.value.type,
        modelNumber: purchaseForm.value.modelNumber,
        manufacturer: purchaseForm.value.manufacturer,
        deviceStatus: purchaseForm.value.status,
        wardId: purchaseForm.value.location,
        purchaseDate: purchaseForm.value.purchaseDate,
        warrantyDate: purchaseForm.value.warrantyDate,
        // Backend is expected to handle createTime and updateTime
      };

      const res = await axios.post('http://localhost:8080/api/medical-devices', newDevice);

      if (res.data.success) {
        ElMessage.success('添加设备成功');
        // Clear the form after successful addition
        purchaseForm.value = { id: '', name: '', type: '', status: '', location: '', quantity: 1, modelNumber: null, manufacturer: null, purchaseDate: null, warrantyDate: null };
        // If you want the '设备信息' table to update automatically after adding a device,
        // you would call fetchDeviceList() here.
        // fetchDeviceList();
      } else {
        ElMessage.error(res.data.message || '添加设备失败');
      }
    } catch (error) {
      console.error('添加设备失败:', error);
      ElMessage.error('添加设备失败');
    }
  });
};
const handleDeletePurchase = (index: number) => {
  purchaseList.value.splice(index, 1);
  ElMessage.success('删除成功');
};

onMounted(() => {
  fetchDeviceList();
});
</script>

<style scoped>
.finance-dashboard {
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
</style> 