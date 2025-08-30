<template>
  <div class="nurse-dashboard">
    <el-header class="header">
      <div class="header-left">
        <h2>护士工作站</h2>
      </div>
      <div class="header-right">
        <el-button type="text" @click="goLogin">退出登录</el-button>
      </div>
    </el-header>

    <div class="main-container">
      <el-aside width="200px" class="aside">
        <el-menu :default-active="activeMenu" class="menu" @select="handleMenuSelect">
          <el-menu-item index="1">
            <el-icon><Document /></el-icon>
            <span>住院办理</span>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Bell /></el-icon>
            <span>患者呼叫</span>
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon><Files /></el-icon>
            <span>医嘱执行</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><House /></el-icon>
            <span>病房管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main class="main">
        <!-- 住院办理部分 -->
        <div v-if="activeMenu === '1'">
          <div class="section">
            <div class="section-header">
              <h3>住院办理</h3>
            </div>
            
            <div class="tab-header">
              <el-button type="primary" @click="showAddPatientDialog = true">新增患者</el-button>
            </div>

            <!-- 患者列表表格 -->
            <el-table :data="patientList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="patientName" label="患者姓名" />
              <el-table-column prop="gender" label="性别" width="80" />
              <el-table-column prop="age" label="年龄" width="80" />
              <el-table-column prop="phoneNumber" label="联系电话" />
              <el-table-column prop="idNumber" label="身份证号" />
              <el-table-column prop="address" label="家庭住址" />
              <el-table-column prop="patientStatus" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.patientStatus)">
                    {{ scope.row.patientStatus === '未入院' ? '未入住' : scope.row.patientStatus }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="admissionTime" label="入院时间" width="180">
                <template #default="scope">
                  {{ scope.row.admissionTime ? new Date(scope.row.admissionTime).toLocaleString() : '未入院' }}
                </template>
              </el-table-column>
              <el-table-column prop="diagnosis" label="诊断" width="120">
                <template #default="scope">
                  {{ scope.row.diagnosis || '暂无' }}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="handleEditPatient(scope.row)">
                    编辑
                  </el-button>
                  <el-button 
                    v-if="scope.row.patientStatus === '未入住'"
                    type="success" 
                    size="small" 
                    @click="handleAssignBed(scope.row)"
                  >
                    分配病房
                  </el-button>
                  <el-button 
                    v-if="scope.row.patientStatus === '住院'"
                    type="warning" 
                    size="small" 
                    @click="handleDischarge(scope.row)"
                  >
                    办理出院
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 患者信息录入对话框 -->
          <el-dialog
            v-model="showAddPatientDialog"
            title="新增患者"
            width="500px"
          >
            <el-form
              ref="addPatientFormRef"
              :model="addPatientForm"
              :rules="addPatientRules"
              label-width="100px"
            >
              <el-form-item label="姓名" prop="patientName">
                <el-input v-model="addPatientForm.patientName" />
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-select v-model="addPatientForm.gender" style="width: 100%">
                  <el-option label="男" value="男" />
                  <el-option label="女" value="女" />
                </el-select>
              </el-form-item>
              <el-form-item label="年龄" prop="age">
                <el-input-number v-model="addPatientForm.age" :min="0" style="width: 100%" />
              </el-form-item>
              <el-form-item label="联系电话" prop="phoneNumber">
                <el-input v-model="addPatientForm.phoneNumber" />
              </el-form-item>
              <el-form-item label="身份证号" prop="idNumber">
                <el-input v-model="addPatientForm.idNumber" />
              </el-form-item>
              <el-form-item label="家庭住址" prop="address">
                <el-input v-model="addPatientForm.address" type="textarea" />
              </el-form-item>
              <el-form-item label="紧急联系人" prop="emergencyContact">
                <el-input v-model="addPatientForm.emergencyContact" />
              </el-form-item>
              <el-form-item label="紧急电话" prop="emergencyPhone">
                <el-input v-model="addPatientForm.emergencyPhone" />
              </el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="showAddPatientDialog = false">取消</el-button>
              <el-button type="primary" @click="handleAddPatient">确定</el-button>
            </template>
          </el-dialog>

          <!-- 病房分配对话框 -->
          <el-dialog
            v-model="showAssignmentDialog"
            title="安排病房"
            width="600px"
            :close-on-click-modal="false"
          >
            <el-form
              ref="assignmentFormRef"
              :model="assignmentForm"
              :rules="assignmentRules"
              label-width="100px"
            >
              <el-form-item label="患者" prop="patientId">
                <el-select
                  v-model="assignmentForm.patientId"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="输入姓名或手机号搜索患者"
                  :remote-method="searchPatients"
                  :loading="searchLoading"
                  style="width: 100%"
                  clearable
                >
                  <el-option
                    v-for="item in patientOptions"
                    :key="item.patientId"
                    :label="`${item.patientName} | ${item.phoneNumber} | ${item.idNumber}`"
                    :value="item.patientId"
                  >
                    <div style="display: flex; justify-content: space-between; align-items: center">
                      <span>{{ item.patientName }}</span>
                      <span style="color: #8492a6; font-size: 13px">
                        {{ item.phoneNumber }}
                      </span>
                    </div>
                    <div style="font-size: 13px; color: #8492a6">
                      <span>身份证: {{ item.idNumber }}</span>
                      <span style="margin-left: 10px">
                        状态: <el-tag size="small" :type="getStatusType(item.patientStatus)">{{ item.patientStatus }}</el-tag>
                      </span>
                    </div>
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="病房" prop="wardId">
                <el-input
                  v-model="assignmentForm.wardId"
                  placeholder="请输入病房编号"
                  style="width: 100%"
                  clearable
                />
              </el-form-item>

              <el-form-item label="床位号" prop="bedNumber">
                <el-input
                  v-model="assignmentForm.bedNumber"
                  placeholder="请输入床位编号 (如: B005)"
                  style="width: 100%"
                  clearable
                />
              </el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="showAssignmentDialog = false">取消</el-button>
              <el-button type="primary" :loading="submitting" @click="handleAssignmentSubmit">
                确定
              </el-button>
            </template>
          </el-dialog>
        </div>

        <!-- 其他功能区域 -->
        <div v-else-if="activeMenu === '2'">
          <div class="section">
            <h3>患者呼叫</h3>
            <el-table :data="callingPatients" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="patientId" label="患者ID" />
              <el-table-column prop="bedId" label="床位ID" />
              <el-table-column prop="wardId" label="病房ID" />
              <el-table-column prop="bedNumber" label="床号" />
              <el-table-column prop="wardName" label="病房名称" />
              <el-table-column prop="callStatus" label="呼叫状态" />
              <el-table-column label="操作" width="120">
                <template #default="scope">
                  <el-button 
                    type="success" 
                    size="small" 
                    @click="handleCallProcessed(scope.row.patientId)"
                  >
                    处理
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div v-if="callingPatients.length === 0" style="color:#999; margin-top: 20px;">
              暂无呼叫中的患者
            </div>
          </div>
        </div>
        <div v-else-if="activeMenu === '3'">
          <div class="section">
            <div class="section-header">
              <h3>医嘱执行</h3>
            </div>

            <!-- 处方列表 -->
            <el-table :data="prescriptionList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="prescriptionId" label="处方编号" width="100" />
              <el-table-column prop="patientId" label="患者ID" width="100" />
              <el-table-column prop="doctorId" label="医生ID" width="100" />
              <el-table-column prop="medicationName" label="药品名称" width="120" />
              <el-table-column prop="dosage" label="剂量" width="100" />
              <el-table-column prop="frequency" label="频率" width="100" />
              <el-table-column label="用药时间" width="200">
                <template #default="scope">
                  {{ scope.row.startDate }} 至 {{ scope.row.endDate }}
                </template>
              </el-table-column>
              <el-table-column prop="instruction" label="医嘱" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="scope.row.status === '已执行' ? 'success' : 'warning'">
                    {{ scope.row.status || '未执行' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="handleExecutePrescription(scope.row)"
                    :disabled="scope.row.status === '已执行'"
                  >
                    执行医嘱
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 医嘱执行确认对话框 -->
          <el-dialog
            v-model="showExecuteDialog"
            title="执行医嘱"
            width="500px"
          >
            <div v-if="selectedPrescription" class="execute-form">
              <p><strong>药品名称：</strong>{{ selectedPrescription.medicationName }}</p>
              <p><strong>剂量：</strong>{{ selectedPrescription.dosage }}</p>
              <p><strong>频率：</strong>{{ selectedPrescription.frequency }}</p>
              <p><strong>医嘱：</strong>{{ selectedPrescription.instruction || '无' }}</p>
            </div>
            <template #footer>
              <el-button @click="showExecuteDialog = false">取消</el-button>
              <el-button type="primary" @click="confirmExecute">确认执行</el-button>
            </template>
          </el-dialog>
        </div>
        <div v-else-if="activeMenu === '4'">
          <div class="section">
            <div class="section-header">
              <h3>病房管理</h3>
            </div>

            <!-- 病房列表 -->
            <el-table :data="wardList" style="width: 100%; margin-bottom: 20px;">
              <el-table-column prop="wardId" label="病房编号" width="120" />
              <el-table-column prop="wardName" label="病房名称" />
              <el-table-column prop="wardType" label="病房类型" width="120">
                <template #default="scope">
                  <el-tag :type="getWardTypeTag(scope.row.wardType)">{{ scope.row.wardType }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="floorNumber" label="楼层" width="80" />
              <el-table-column label="床位使用情况" width="200">
                <template #default="scope">
                  <el-progress 
                    :percentage="Math.round((scope.row.occupiedBeds / scope.row.totalBeds) * 100)"
                    :format="(percentage) => `${scope.row.occupiedBeds}/${scope.row.totalBeds}`"
                    :status="getProgressStatus(scope.row.occupiedBeds, scope.row.totalBeds)"
                  />
                </template>
              </el-table-column>
              <el-table-column prop="wardStatus" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="scope.row.wardStatus === '开放' ? 'success' : 'danger'">
                    {{ scope.row.wardStatus }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150">
                <template #default="scope">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="showWardDetails(scope.row)"
                  >
                    查看详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 病房详情对话框 -->
          <el-dialog
            v-model="showWardDetailsDialog"
            :title="selectedWard ? `${selectedWard.wardName} - 床位详情` : '床位详情'"
            width="800px"
          >
            <div v-if="selectedWard" class="ward-details">
              <div class="ward-info-header">
                <div>
                  <el-tag>{{ selectedWard.wardType }}</el-tag>
                  <el-tag style="margin-left: 10px">{{ selectedWard.floorNumber }}层</el-tag>
                </div>
                <div>
                  <el-tag type="success">可用床位：{{ selectedWard.availableBeds }}</el-tag>
                  <el-tag type="info" style="margin-left: 10px">总床位：{{ selectedWard.totalBeds }}</el-tag>
                </div>
              </div>

              <el-table :data="bedList" style="width: 100%; margin-top: 20px;">
                <el-table-column prop="bedNumber" label="床位号" width="100" align="center" />
                <el-table-column prop="bedId" label="床位编号" width="150" align="center" />
                <el-table-column prop="bedStatus" label="状态" width="100" align="center">
                  <template #default="scope">
                    <el-tag :type="scope.row.bedStatus === '空闲' ? 'success' : 'warning'">
                      {{ scope.row.bedStatus }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="wardType" label="病房类型" align="center">
                  <template #default="scope">
                    {{ scope.row.wardType || '-' }}
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="价格" align="center" width="160">
                  <template #default="scope">
                    {{ scope.row.price || '-' }}
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-dialog>
        </div>
      </el-main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { Document, Bell, Files, House } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from 'axios';

interface Patient {
  patientId: string;
  patientName: string;
  gender: string;
  age: number;
  idNumber: string;
  phoneNumber: string;
  address: string;
  emergencyContact: string;
  emergencyPhone: string;
  admissionTime: string | null;
  dischargeTime: string | null;
  patientStatus: string;
  diagnosis: string | null;
  createTime: string;
  updateTime: string;
}

interface Room {
  id: string;
  number: string;
}

interface Bed {
  bedId: string;
  wardId: string;
  bedNumber: number;
  bedStatus: string;
  bedPrice: number | null;
  createTime: string;
  updateTime: string;
}

interface Ward {
  wardId: string;
  wardName: string;
  wardType: string;
  floorNumber: number;
  totalBeds: number;
  availableBeds: number;
  occupiedBeds: number;
  wardStatus: string;
  createTime: string;
  updateTime: string | null;
}

interface RoomAssignment {
  assignmentId: string;
  patientId: string;
  wardId: string;
  bedNumber: string;
  assignmentTime: string;
  status: string;
  patientName: string;
  wardName: string;
}

interface PatientOption {
  patientId: string;
  patientName: string;
  phoneNumber: string;
  idNumber: string;
  patientStatus: string;
}

interface BedAssignment {
  assignId: string;
  patientId: string;
  patientName: string;
  wardId: string;
  bedNumber: number;
  bedStatus: string;
  diagnosis: string;
  assignTime: string;
  assignStatus: string;
}

interface BedInfo {
  bedNumber: string;
  bedStatus: string;
  patientName: string | null;
  admissionTime: string | null;
}

interface BedDisplay {
  bedNumber: string;
  bedStatus: string;
  wardType: string;
  price: string;
}

interface Prescription {
  prescriptionId: string;
  patientId: string;
  doctorId: string;
  medicationName: string;
  dosage: string;
  frequency: string;
  startDate: string;
  endDate: string;
  instruction: string;
  status: string;
}

const router = useRouter();
const activeMenu = ref('1');
const admissionTab = ref('registration');
const callingPatients = ref<any[]>([]);

// 患者信息相关
const patientList = ref<Patient[]>([]);
const showAddPatientDialog = ref(false);
const addPatientFormRef = ref();
const addPatientForm = ref({
  patientName: '',
  gender: '',
  age: 0,
  phoneNumber: '',
  idNumber: '',
  address: '',
  emergencyContact: '',
  emergencyPhone: ''
});

const addPatientRules = {
  patientName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  idNumber: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
  address: [{ required: true, message: '请输入家庭住址', trigger: 'blur' }]
};

// 病房分配相关数据
const roomAssignmentList = ref<RoomAssignment[]>([]);
const showAssignmentDialog = ref(false);
const assignmentFormRef = ref();
const assignmentForm = ref({
  patientId: '',
  wardId: '',
  bedNumber: ''
});

const assignmentRules = {
  patientId: [{ required: true, message: '请选择患者', trigger: 'change' }],
  wardId: [{ required: true, message: '请选择病房', trigger: 'change' }],
  bedNumber: [{ required: true, message: '请选择床位', trigger: 'change' }]
};

// 患者搜索相关
const patientOptions = ref<PatientOption[]>([]);
const searchLoading = ref(false);
const wardList = ref<Ward[]>([]);
const availableBedNumbers = computed<string[]>(() => {
  const selectedWard = wardList.value.find(ward => ward.wardId === assignmentForm.value.wardId);
  if (!selectedWard || selectedWard.availableBeds === 0) return [];
  
  // 生成可用床位号列表
  const beds: string[] = [];
  for (let i = 1; i <= selectedWard.totalBeds; i++) {
    beds.push(i.toString().padStart(2, '0'));
  }
  return beds;
});
const submitting = ref(false);

// 病房管理相关
const showWardDetailsDialog = ref(false);
const selectedWard = ref<Ward | null>(null);
const bedList = ref<BedDisplay[]>([]);

// 处方相关数据
const prescriptionList = ref<Prescription[]>([]);
const showExecuteDialog = ref(false);
const selectedPrescription = ref<Prescription | null>(null);

const goLogin = () => {
  router.push('/login');
};

const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
  if (index === '4') {
    fetchWards(); // 切换到病房管理时获取病房列表
  }
};

// 获取患者列表
const fetchPatientList = async () => {
  try {
    const res = await axios.get<Patient[]>('http://localhost:8080/admin/patients');
    patientList.value = res.data;
  } catch (e) {
    ElMessage.error('获取患者列表失败');
  }
};

// 新增患者
const handleAddPatient = () => {
  addPatientFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      const payload = {
        ...addPatientForm.value,
        admissionTime: new Date().toISOString(),
        patientStatus: '住院'
      };
      await axios.post('http://localhost:8080/nurse/patients', payload);
      ElMessage.success('新增患者成功');
      showAddPatientDialog.value = false;
      fetchPatientList();
    } catch (e) {
      ElMessage.error('新增失败');
    }
  });
};

// 编辑患者
const handleEditPatient = (patient: Patient) => {
  addPatientForm.value = {
    patientName: patient.patientName,
    gender: patient.gender,
    age: patient.age,
    phoneNumber: patient.phoneNumber,
    idNumber: patient.idNumber,
    address: patient.address,
    emergencyContact: patient.emergencyContact,
    emergencyPhone: patient.emergencyPhone
  };
  showAddPatientDialog.value = true;
};

// 获取状态标签类型
const getStatusType = (status: string) => {
  switch (status) {
    case '住院':
      return 'success';
    case '待入院':
      return 'warning';
    case '已出院':
      return 'info';
    default:
      return '';
  }
};

// 搜索患者
const searchPatients = async (query: string) => {
  if (query.length < 1) {
    patientOptions.value = [];
    return;
  }
  searchLoading.value = true;
  try {
    const res = await axios.get<Patient[]>('http://localhost:8080/patients/search', { 
      params: { 
        keyword: query,
        limit: 10 // 限制返回结果数量
      } 
    });
    patientOptions.value = res.data.map(patient => ({
      patientId: patient.patientId,
      patientName: patient.patientName,
      phoneNumber: patient.phoneNumber,
      idNumber: patient.idNumber,
      patientStatus: patient.patientStatus
    }));
  } catch (e) {
    ElMessage.error('搜索患者失败');
    patientOptions.value = [];
  } finally {
    searchLoading.value = false;
  }
};

// 获取所有病房
const fetchWards = async () => {
  try {
    const res = await axios.get<Ward[]>('http://localhost:8080/api/wards');
    wardList.value = res.data;
  } catch (e) {
    ElMessage.error('获取病房列表失败');
  }
};

// 提交病房分配
const handleAssignmentSubmit = () => {
  assignmentFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    submitting.value = true;
    try {
      const selectedWard = wardList.value.find(ward => ward.wardId === assignmentForm.value.wardId);
      if (!selectedWard) {
        ElMessage.error('所选病房不存在');
        return;
      }
      
      await axios.post('http://localhost:8080/room-assignments', {
        patientId: assignmentForm.value.patientId,
        wardId: assignmentForm.value.wardId,
        bedNumber: assignmentForm.value.bedNumber
      });
      
      ElMessage.success('病房分配成功');
      showAssignmentDialog.value = false;
      // 重新获取数据
      fetchWards();
      fetchPatientList();
    } catch (e) {
      ElMessage.error('病房分配失败');
    } finally {
      submitting.value = false;
    }
  });
};

// 处理分配病房
const handleAssignBed = (patient: Patient) => {
  assignmentForm.value = {
    patientId: patient.patientId,
    wardId: '',
    bedNumber: ''
  };
  showAssignmentDialog.value = true;
};

// 获取病房类型对应的标签类型
const getWardTypeTag = (type: string) => {
  switch (type) {
    case '普通':
      return '';
    case '重症':
      return 'danger';
    case '隔离':
      return 'warning';
    default:
      return 'info';
  }
};

// 获取进度条状态
const getProgressStatus = (occupied: number, total: number) => {
  const percentage = (occupied / total) * 100;
  if (percentage >= 90) return 'exception';
  if (percentage >= 70) return 'warning';
  return 'success';
};

// 显示病房详情
const showWardDetails = async (ward: Ward) => {
  selectedWard.value = ward;
  showWardDetailsDialog.value = true;
  
  try {
    // 获取病房的所有床位信息
    const response = await axios.get<Bed[]>(`http://localhost:8080/api/beds/ward/${ward.wardId}`);
    const beds = response.data;

    // 构造床位列表
    bedList.value = beds.map(bed => ({
      bedNumber: bed.bedNumber.toString().padStart(2, '0'),
      bedId: bed.bedId,
      bedStatus: bed.bedStatus || '空闲',
      wardType: ward.wardType || '-',  // 使用当前病房的类型
      price: bed.bedPrice ? `${bed.bedPrice}元/天` : '-'  // 使用后端返回的价格
    }));

    // 按床位号排序
    bedList.value.sort((a, b) => parseInt(a.bedNumber) - parseInt(b.bedNumber));
  } catch (e) {
    ElMessage.error('获取床位信息失败');
    console.error('Error loading bed details:', e);
  }
};

const handleDischarge = async (patient: Patient) => {
  try {
    await ElMessageBox.confirm(
      `确定为患者 ${patient.patientName} 办理出院吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );

    // 1. 先查找该患者当前的 bedId
    let bedId = '';
    try {
      // 查询患者当前床位
      const res = await axios.get('http://localhost:8080/api/patients/patient-bed', {
        params: { patientId: patient.patientId }
      });
      bedId = res.data.bedId;
      if (!bedId) {
        ElMessage.error('未找到该患者的床位信息，无法办理出院');
        return;
      }
    } catch (e) {
      ElMessage.error('获取患者床位信息失败');
      return;
    }

    // 2. 调用出院接口
    await axios.post('http://localhost:8080/room-assignments/discharge', {
      patientId: patient.patientId,
      bedId: bedId
    });

    ElMessage.success('出院办理成功');
    fetchPatientList(); // 刷新患者列表
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('出院办理失败');
    }
  }
};

// 获取处方列表
const fetchPrescriptions = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/prescriptions');
    prescriptionList.value = response.data;
  } catch (error) {
    ElMessage.error('获取处方列表失败');
  }
};

// 处理执行医嘱
const handleExecutePrescription = (prescription) => {
  selectedPrescription.value = prescription;
  showExecuteDialog.value = true;
};

// 确认执行医嘱
const confirmExecute = async () => {
  if (!selectedPrescription.value) return;
  
  try {
    // 调用后端API更新状态
    await axios.post(`http://localhost:8080/api/prescriptions/${selectedPrescription.value.prescriptionId}/execute`);
    
    // 更新本地数据状态
    const index = prescriptionList.value.findIndex(p => selectedPrescription.value && p.prescriptionId === selectedPrescription.value.prescriptionId);
    if (index !== -1) {
      prescriptionList.value[index].status = '已执行';
    }
    
    ElMessage.success('医嘱执行成功');
    showExecuteDialog.value = false;
  } catch (error) {
    ElMessage.error('医嘱执行失败');
  }
};

const fetchCallingPatients = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/patient-calls');
    callingPatients.value = res.data;
  } catch (e) {
    callingPatients.value = [];
  }
};

const handleCallProcessed = async (patientId: string) => {
  await axios.put(`http://localhost:8080/api/patient-calls/${patientId}`);
  fetchCallingPatients();
};

// 监听菜单切换
watch(activeMenu, (newValue) => {
  if (newValue === '3') {
    fetchPrescriptions();
  }
});

// 在组件挂载时获取数据
onMounted(() => {
  fetchPatientList();
  fetchWards();
  if (activeMenu.value === '3') {
    fetchPrescriptions();
  }
  fetchCallingPatients();
});
</script> 

<style scoped>
.nurse-dashboard {
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
  justify-content: space-between;
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

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.admission-tabs {
  margin-top: 20px;
}

.tab-header {
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

.ward-info-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

.ward-details {
  padding: 20px;
}
</style> 