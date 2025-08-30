<template>
  <div class="bed-assignment">
    <div class="header">
      <h2>住院办理</h2>
      <div class="tabs">
        <el-tabs v-model="activeTab" class="admission-tabs">
          <el-tab-pane label="患者信息录入" name="registration">
            <div class="tab-header">
              <el-button type="primary" @click="showAddPatientDialog = true">新增患者</el-button>
            </div>
            <!-- 患者列表表格 -->
            <el-table :data="patientList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="name" label="患者姓名" />
              <el-table-column prop="gender" label="性别" width="80" />
              <el-table-column prop="age" label="年龄" width="80" />
              <el-table-column prop="phone" label="联系电话" />
              <el-table-column prop="idCard" label="身份证号" />
              <el-table-column prop="address" label="家庭住址" />
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="handleEditPatient(scope.row)">
                    编辑
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>

          <el-tab-pane label="病房床位安排" name="bedArrangement">
            <div class="bed-management">
              <el-button type="primary" @click="showAssignBedDialog">安排病房</el-button>
            </div>
            <!-- 原有的病床分配表格保持不变 -->
            <el-table :data="bedAssignmentList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="patientName" label="患者姓名" width="120" />
              <el-table-column prop="wardNumber" label="病房号" width="100">
                <template #default="scope">
                  {{ scope.row.wardNumber || '未分配' }}
                </template>
              </el-table-column>
              <el-table-column prop="bedNumber" label="床号" width="100">
                <template #default="scope">
                  {{ scope.row.bedNumber || '未分配' }}
                </template>
              </el-table-column>
              <el-table-column prop="assignTime" label="安排时间" width="180">
                <template #default="scope">
                  {{ scope.row.assignTime ? new Date(scope.row.assignTime).toLocaleString() : '未安排' }}
                </template>
              </el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.status)">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template #default="scope">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="handleAssignBed(scope.row)"
                    :disabled="scope.row.status === '已出院'"
                  >
                    分配床位
                  </el-button>
                  <el-button 
                    type="danger" 
                    size="small" 
                    @click="handleDischarge(scope.row)"
                    :disabled="!scope.row.wardNumber || scope.row.status === '已出院'"
                  >
                    办理出院
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <!-- 新增/编辑患者对话框 -->
    <el-dialog
      v-model="showAddPatientDialog"
      :title="editingPatient ? '编辑患者' : '新增患者'"
      width="500px"
    >
      <el-form
        ref="patientFormRef"
        :model="patientForm"
        :rules="patientRules"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="patientForm.name" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="patientForm.gender" style="width: 100%">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="patientForm.age" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="patientForm.phone" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="patientForm.idCard" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="patientForm.address" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddPatientDialog = false">取消</el-button>
          <el-button type="primary" @click="handleSavePatient">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 原有的床位分配对话框 -->
    <el-dialog v-model="bedAssignmentVisible" title="分配床位" width="500px">
      <el-form :model="bedAssignmentForm" label-width="100px">
        <el-form-item label="患者姓名">
          <el-input v-model="bedAssignmentForm.patientName" disabled />
        </el-form-item>
        <el-form-item label="选择病区">
          <el-select v-model="bedAssignmentForm.ward" placeholder="请选择病区" @change="handleWardChange">
            <el-option
              v-for="ward in wardList"
              :key="ward.wardId"
              :label="ward.wardName"
              :value="ward.wardId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择病房">
          <el-select 
            v-model="bedAssignmentForm.roomNumber" 
            placeholder="请选择病房"
            :disabled="!bedAssignmentForm.ward"
            @change="handleRoomChange"
          >
            <el-option
              v-for="room in roomList"
              :key="room.roomId"
              :label="room.roomNumber"
              :value="room.roomId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择床位">
          <el-select 
            v-model="bedAssignmentForm.bedNumber" 
            placeholder="请选择床位"
            :disabled="!bedAssignmentForm.roomNumber"
          >
            <el-option
              v-for="bed in availableBeds"
              :key="bed.bedId"
              :label="bed.bedNumber"
              :value="bed.bedId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="bedAssignmentVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmBedAssignment">确认分配</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Search } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

// 患者信息接口
interface Patient {
  id?: string;
  name: string;
  gender: string;
  age: number;
  phone: string;
  idCard: string;
  address: string;
}

// 原有的接口定义保持不变
interface Ward {
  wardId: string;
  wardName: string;
}

interface Room {
  roomId: string;
  roomNumber: string;
  wardId: string;
}

interface Bed {
  bedId: string;
  bedNumber: string;
  roomId: string;
  status: string;
}

// 响应式数据
const activeTab = ref('registration');
const patientList = ref<Patient[]>([]);
const showAddPatientDialog = ref(false);
const editingPatient = ref<Patient | null>(null);
const patientFormRef = ref();

const patientForm = ref({
  name: '',
  gender: '',
  age: 0,
  phone: '',
  idCard: '',
  address: ''
});

const patientRules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
  address: [{ required: true, message: '请输入家庭住址', trigger: 'blur' }]
};

// 获取所有患者列表
const fetchPatientList = async () => {
  try {
    const response = await axios.get('http://localhost:8080/admin/patients');
    patientList.value = response.data;
  } catch (error) {
    ElMessage.error('获取患者列表失败');
  }
};

// 处理编辑患者
const handleEditPatient = (patient: Patient) => {
  editingPatient.value = patient;
  patientForm.value = { ...patient };
  showAddPatientDialog.value = true;
};

// 处理保存患者信息
const handleSavePatient = () => {
  patientFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    
    try {
      if (editingPatient.value) {
        // 编辑现有患者
        await axios.put(`http://localhost:8080/admin/patients/${editingPatient.value.id}`, patientForm.value);
        ElMessage.success('患者信息更新成功');
      } else {
        // 新增患者
        await axios.post('http://localhost:8080/admin/patients', patientForm.value);
        ElMessage.success('新增患者成功');
      }
      
      showAddPatientDialog.value = false;
      editingPatient.value = null;
      fetchPatientList();
    } catch (error) {
      ElMessage.error(editingPatient.value ? '更新失败' : '新增失败');
    }
  });
};

// 原有的方法保持不变
const bedAssignmentVisible = ref(false);
const wardList = ref<Ward[]>([]);
const roomList = ref<Room[]>([]);
const availableBeds = ref<Bed[]>([]);
const bedAssignmentList = ref([]);

// 获取病区列表
const fetchWards = async () => {
  try {
    const response = await axios.get('/api/wards');
    wardList.value = response.data;
  } catch (error) {
    ElMessage.error('获取病区列表失败');
  }
};

// 根据病区获取病房列表
const handleWardChange = async (wardId: string) => {
  try {
    const response = await axios.get(`/api/wards/${wardId}/rooms`);
    roomList.value = response.data;
    bedAssignmentForm.value.roomNumber = '';
    bedAssignmentForm.value.bedNumber = '';
  } catch (error) {
    ElMessage.error('获取病房列表失败');
  }
};

// 根据病房获取可用床位
const handleRoomChange = async (roomId: string) => {
  try {
    const response = await axios.get(`/api/rooms/${roomId}/available-beds`);
    availableBeds.value = response.data;
    bedAssignmentForm.value.bedNumber = '';
  } catch (error) {
    ElMessage.error('获取床位列表失败');
  }
};

// 显示分配床位对话框
const showAssignBedDialog = () => {
  bedAssignmentForm.value = {
    patientId: '',
    patientName: '',
    ward: '',
    roomNumber: '',
    bedNumber: ''
  };
  bedAssignmentVisible.value = true;
};

// 确认床位分配
const confirmBedAssignment = async () => {
  try {
    await axios.post('/api/bed-assignments', {
      patientId: bedAssignmentForm.value.patientId,
      bedId: bedAssignmentForm.value.bedNumber
    });
    ElMessage.success('床位分配成功');
    bedAssignmentVisible.value = false;
    fetchPatientList();
  } catch (error) {
    ElMessage.error('床位分配失败');
  }
};

// 处理出院
const handleDischarge = async (patient: Patient) => {
  try {
    await axios.post(`/api/patients/${patient.id}/discharge`);
    ElMessage.success('出院办理成功');
    fetchPatientList();
  } catch (error) {
    ElMessage.error('出院办理失败');
  }
};

// 获取状态标签类型
const getStatusType = (status: string) => {
  switch (status) {
    case '住院中':
      return 'success';
    case '待分配':
      return 'warning';
    case '已出院':
      return 'info';
    default:
      return 'default';
  }
};

// 组件挂载时获取数据
onMounted(() => {
  fetchPatientList();
  fetchWards();
});
</script>

<style scoped>
.bed-assignment {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
}

.tab-header {
  margin: 20px 0;
}

.admission-tabs {
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.el-tag {
  margin-right: 8px;
}
</style> 