<template>
  <el-dialog
    :model-value="visible"
    title="添加就诊患者"
    width="400px"
    @close="handleClose"
  >
    <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
      <el-form-item label="患者" prop="patientId">
        <el-select
          v-model="form.patientId"
          filterable
          remote
          reserve-keyword
          placeholder="搜索患者姓名/手机号"
          :remote-method="searchPatients"
          :loading="searchLoading"
        >
          <el-option
            v-for="item in patientOptions"
            :key="item.id"
            :label="item.name + '（' + item.phone + '）'"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预约时间" prop="appointmentTime">
        <el-date-picker
          v-model="form.appointmentTime"
          type="datetime"
          placeholder="选择日期时间"
          style="width: 100%;"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="loading" @click="submitForm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

interface PatientOption {
  id: string;
  name: string;
  phone: string;
}

const props = defineProps<{ visible: boolean }>();
const emits = defineEmits(['update:visible', 'success']);

const formRef = ref();
const form = ref({
  patientId: '',
  appointmentTime: ''
});
const rules = {
  patientId: [{ required: true, message: '请选择患者', trigger: 'change' }],
  appointmentTime: [{ required: true, message: '请选择预约时间', trigger: 'change' }]
};
const loading = ref(false);

const patientOptions = ref<PatientOption[]>([]);
const searchLoading = ref(false);

const searchPatients = async (query) => {
  if (!query) return;
  searchLoading.value = true;
  try {
    const res = await axios.get('http://localhost:8080/patients/search', { params: { keyword: query } });
    patientOptions.value = res.data; // [{id, name, phone}]
  } catch (e) {
    patientOptions.value = [];
  }
  searchLoading.value = false;
};

watch(
  () => props.visible,
  (val) => {
    if (val) {
      // 打开时重置表单
      form.value = { patientId: '', appointmentTime: '' };
      formRef.value?.clearValidate();
      patientOptions.value = [];
    }
  }
);

const handleClose = () => {
  emits('update:visible', false);
};

const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (!valid) return;
    loading.value = true;
    try {
      await axios.post('http://localhost:8080/visits', {
        patientId: form.value.patientId,
        appointmentTime: form.value.appointmentTime
      });
      ElMessage.success('添加成功');
      emits('success');
      handleClose();
    } catch (e) {
      ElMessage.error('添加失败');
    } finally {
      loading.value = false;
    }
  });
};
</script> 