<template>
  <div class="admin-dashboard">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h2>管理员工作台</h2>
      </div>
      <div class="header-right">
        <span class="welcome">欢迎尊贵的管理员大人！</span>
        <span>当前管理员：{{ adminName }}</span>
        <el-dropdown>
          <span class="user-info">
            <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <span>{{ adminName }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="goLogin">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <el-button type="text" @click="goLogin" class="logout-btn">返回登录</el-button>
      </div>
    </el-header>
    <div class="main-container">
      <el-aside width="220px" class="aside">
        <el-menu :default-active="activeMenu" class="menu" @select="handleMenuSelect">
          <el-sub-menu index="1">
            <template #title>用户管理</template>
            <el-menu-item index="1-1"><el-icon><User /></el-icon>医生管理</el-menu-item>
            <el-menu-item index="1-2"><el-icon><User /></el-icon>护士管理</el-menu-item>
            <el-menu-item index="1-3"><el-icon><User /></el-icon>家属管理</el-menu-item>
            <el-menu-item index="1-4"><el-icon><User /></el-icon>财务人员管理</el-menu-item>
            <el-menu-item index="1-5"><el-icon><User /></el-icon>角色与权限分配</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>患者管理</template>
            <el-menu-item index="2-1"><el-icon><Files /></el-icon>患者信息总览</el-menu-item>
            <el-menu-item index="2-2"><el-icon><Files /></el-icon>住院/出院管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>医疗设备管理</template>
            <el-menu-item index="3-1"><el-icon><Document /></el-icon>设备信息</el-menu-item>
            <el-menu-item index="3-2"><el-icon><Document /></el-icon>设备采购</el-menu-item>
            <el-menu-item index="3-3"><el-icon><Document /></el-icon>设备记录</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="4"><el-icon><List /></el-icon>病房与床位管理</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main">
        <!-- 医生管理区 -->
        <div v-if="activeMenu === '1-1'">
          <div class="section">
            <div class="section-header">
              <h3>医生管理</h3>
              <div>
                <el-button type="primary" @click="showAddDoctorDialog = true">添加医生</el-button>
              </div>
            </div>
              <div class="search-area">
                <el-input
                  v-model="doctorSearchForm.staffName"
                  placeholder="输入姓名搜索"
                  style="width: 200px; margin-right: 10px;"
                  @keyup.enter="handleDoctorSearch"
                >
                  <template #prefix>
                    <el-icon><Search /></el-icon>
                  </template>
                </el-input>
                <el-select 
                  v-model="doctorSearchForm.department" 
                  placeholder="选择科室" 
                  clearable
                  style="width: 150px; margin-right: 10px;"
                >
                  <el-option label="内科" value="内科" />
                  <el-option label="外科" value="外科" />
                  <el-option label="儿科" value="儿科" />
                  <el-option label="妇产科" value="妇产科" />
                  <el-option label="眼科" value="眼科" />
                  <el-option label="骨科" value="骨科" />
                </el-select>
                <el-select 
                  v-model="doctorSearchForm.title" 
                  placeholder="选择职称" 
                  clearable
                  style="width: 150px; margin-right: 10px;"
                >
                  <el-option label="主任医师" value="主任医师" />
                  <el-option label="副主任医师" value="副主任医师" />
                  <el-option label="主治医师" value="主治医师" />
                  <el-option label="住院医师" value="住院医师" />
                </el-select>
                <el-input
                  v-model="doctorSearchForm.staffId"
                  placeholder="输入员工ID"
                  style="width: 150px; margin-right: 10px;"
                />
                <el-button type="primary" @click="handleDoctorSearch">搜索</el-button>
                <el-button @click="resetDoctorSearch">重置</el-button>
              </div>
            </div>

            <el-table :data="doctorList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="staffId" label="员工ID" width="100" />
              <el-table-column prop="staffName" label="姓名" width="100" />
              <el-table-column prop="gender" label="性别" width="60" />
              <el-table-column prop="age" label="年龄" width="60" />
              <el-table-column prop="department" label="科室" width="100" />
              <el-table-column prop="title" label="职称" width="100" />
              <el-table-column prop="phoneNumber" label="联系电话" width="120" />
              <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column label="操作" width="180">
                <template #default="scope">
                <el-button type="primary" size="small" @click="handleEditDoctor(scope.row)">编辑</el-button>
                <el-button type="danger" size="small" @click="handleDeleteDoctor(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          <!-- 添加医生弹窗 -->
          <el-dialog v-model="showAddDoctorDialog" title="添加医生" width="500px">
            <el-form :model="addDoctorForm" :rules="addDoctorRules" ref="addDoctorFormRef" label-width="100px">
              <el-form-item label="员工ID" prop="staffId">
                <el-input v-model="addDoctorForm.staffId" />
              </el-form-item>
              <el-form-item label="姓名" prop="staffName">
                <el-input v-model="addDoctorForm.staffName" />
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-select v-model="addDoctorForm.gender">
                  <el-option label="男" value="男" />
                  <el-option label="女" value="女" />
                </el-select>
              </el-form-item>
              <el-form-item label="年龄" prop="age">
                <el-input-number v-model="addDoctorForm.age" :min="18" />
              </el-form-item>
              <el-form-item label="科室" prop="department">
                <el-input v-model="addDoctorForm.department" />
              </el-form-item>
              <el-form-item label="职称" prop="title">
                <el-input v-model="addDoctorForm.title" />
              </el-form-item>
              <el-form-item label="联系电话" prop="phoneNumber">
                <el-input v-model="addDoctorForm.phoneNumber" />
              </el-form-item>
              <el-form-item label="职位" prop="position">
                <el-input v-model="addDoctorForm.position" />
              </el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="showAddDoctorDialog = false">取消</el-button>
              <el-button type="primary" @click="handleAddDoctor">确定</el-button>
            </template>
          </el-dialog>
          <!-- 编辑医生弹窗 -->
          <el-dialog v-model="showEditDoctorDialog" title="编辑医生" width="500px">
            <el-form :model="editDoctorForm" :rules="addDoctorRules" ref="editDoctorFormRef" label-width="100px">
              <el-form-item label="员工ID" prop="staffId">
                <el-input v-model="editDoctorForm.staffId" disabled />
              </el-form-item>
              <el-form-item label="姓名" prop="staffName">
                <el-input v-model="editDoctorForm.staffName" />
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-select v-model="editDoctorForm.gender">
                  <el-option label="男" value="男" />
                  <el-option label="女" value="女" />
                </el-select>
              </el-form-item>
              <el-form-item label="年龄" prop="age">
                <el-input-number v-model="editDoctorForm.age" :min="18" />
              </el-form-item>
              <el-form-item label="科室" prop="department">
                <el-input v-model="editDoctorForm.department" />
              </el-form-item>
              <el-form-item label="职称" prop="title">
                <el-input v-model="editDoctorForm.title" />
              </el-form-item>
              <el-form-item label="联系电话" prop="phoneNumber">
                <el-input v-model="editDoctorForm.phoneNumber" />
              </el-form-item>
              <el-form-item label="职位" prop="position">
                <el-input v-model="editDoctorForm.position" />
              </el-form-item>
            </el-form>
            <template #footer>
              <el-button @click="showEditDoctorDialog = false">取消</el-button>
              <el-button type="primary" @click="handleUpdateDoctor">保存</el-button>
            </template>
          </el-dialog>
        </div>
        <!-- 其他区块保留原有提示 -->
        <div v-else-if="activeMenu === '1-2'">
          <div class="section">
            <div class="section-header">
              <h3>护士管理</h3>
              <div class="search-area">
                <el-input
                  v-model="nurseSearchForm.staffName"
                  placeholder="输入姓名搜索"
                  style="width: 200px; margin-right: 10px;"
                  @keyup.enter="handleNurseSearch"
                >
                  <template #prefix>
                    <el-icon><Search /></el-icon>
                  </template>
                </el-input>
                <el-select 
                  v-model="nurseSearchForm.department" 
                  placeholder="选择科室" 
                  clearable
                  style="width: 150px; margin-right: 10px;"
                >
                  <el-option label="内科" value="内科" />
                  <el-option label="外科" value="外科" />
                  <el-option label="儿科" value="儿科" />
                  <el-option label="妇产科" value="妇产科" />
                  <el-option label="眼科" value="眼科" />
                  <el-option label="骨科" value="骨科" />
                </el-select>
                <el-select 
                  v-model="nurseSearchForm.title" 
                  placeholder="选择职称" 
                  clearable
                  style="width: 150px; margin-right: 10px;"
                >
                  <el-option label="主管护师" value="主管护师" />
                  <el-option label="护师" value="护师" />
                  <el-option label="护士" value="护士" />
                </el-select>
                <el-input
                  v-model="nurseSearchForm.staffId"
                  placeholder="输入员工ID"
                  style="width: 150px; margin-right: 10px;"
                />
                <el-button type="primary" @click="handleNurseSearch">搜索</el-button>
                <el-button @click="resetNurseSearch">重置</el-button>
              </div>
            </div>

            <el-table :data="nurseList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="staffId" label="员工ID" width="100" />
              <el-table-column prop="staffName" label="姓名" width="100" />
              <el-table-column prop="gender" label="性别" width="60" />
              <el-table-column prop="age" label="年龄" width="60" />
              <el-table-column prop="department" label="科室" width="100" />
              <el-table-column prop="title" label="职称" width="100" />
              <el-table-column prop="phoneNumber" label="联系电话" width="120" />
              <el-table-column prop="createTime" label="创建时间" width="180" />
            </el-table>
          </div>
        </div>
        <div v-else-if="activeMenu === '1-3'">
          <div class="section">
            <div class="section-header">
              <h3>家属管理</h3>
              <div class="search-area">
                <el-input
                  v-model="familySearchInput"
                  placeholder="输入患者姓名搜索"
                  style="width: 200px; margin-right: 10px;"
                  @keyup.enter="handleFamilySearch"
                >
                  <template #prefix>
                    <el-icon><Search /></el-icon>
                  </template>
                </el-input>
                <el-button type="primary" @click="handleFamilySearch">搜索</el-button>
                <el-button @click="fetchAllFamilies">重置</el-button>
              </div>
            </div>

            <el-table :data="familyList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="emergencyContact" label="家属姓名" width="120" />
              <el-table-column prop="phoneNumber" label="联系电话" width="150" />
              <el-table-column prop="patientId" label="患者ID" width="120" />
              <el-table-column prop="patientName" label="患者姓名" width="120" />
              <el-table-column prop="patientStatus" label="患者状态" width="120">
                <template #default="scope">
                  <el-tag :type="getPatientStatusType(scope.row.patientStatus)">
                    {{ scope.row.patientStatus }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination-container" style="margin-top: 20px; text-align: right;">
              <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="pageSize"
                :total="total"
                :page-sizes="[10, 20, 50, 100]"
                layout="total, sizes, prev, pager, next"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>
          </div>
        </div>
        <div v-else-if="activeMenu === '1-4'">
          <div class="section">
            <div class="section-header">
              <h3>财务人员管理</h3>
            </div>
            
            <el-table :data="financeStaffList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="userId" label="用户ID" width="120" />
              <el-table-column prop="username" label="用户名" width="120" />
              <el-table-column prop="roleType" label="角色类型" width="120">
                <template #default="scope">
                  <el-tag type="success">{{ scope.row.roleType }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="staffId" label="员工编号" width="120">
                <template #default="scope">
                  {{ scope.row.staffId || '未分配' }}
                </template>
              </el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="scope.row.status === '启用' ? 'success' : 'danger'">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="lastLoginTime" label="最近登录" width="180">
                <template #default="scope">
                  {{ scope.row.lastLoginTime || '暂无登录记录' }}
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="创建时间" width="180" />
              <el-table-column prop="updateTime" label="更新时间" width="180" />
              <el-table-column label="操作" width="120" fixed="right">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="handleEditFinanceStaff(scope.row)">
                    编辑
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <!-- 编辑财务人员对话框 -->
            <el-dialog v-model="showFinanceEditDialog" title="编辑财务人员" width="500px">
              <el-form :model="financeEditForm" :rules="formRules" ref="financeEditFormRef" label-width="100px">
                <el-form-item label="用户ID" prop="userId">
                  <el-input v-model="financeEditForm.userId" disabled />
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                  <el-input v-model="financeEditForm.username" />
                </el-form-item>
                <el-form-item label="员工编号" prop="staffId">
                  <el-input v-model="financeEditForm.staffId" placeholder="请输入员工编号" />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                  <el-select v-model="financeEditForm.status" style="width: 100%">
                    <el-option label="启用" value="启用" />
                    <el-option label="禁用" value="禁用" />
                  </el-select>
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="showFinanceEditDialog = false">取消</el-button>
                <el-button type="primary" @click="handleSaveFinanceStaff">保存</el-button>
              </template>
            </el-dialog>
          </div>
        </div>
        <div v-else-if="activeMenu === '1-5'">
          <div class="section">
            <div class="section-header">
              <h3>角色与权限分配</h3>
              <div>
                <el-button type="primary" @click="showAddUserDialog = true">添加用户</el-button>
                <el-button type="primary" @click="fetchUserList">刷新</el-button>
              </div>
            </div>
            
            <el-table :data="userList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="userId" label="用户ID" width="120" />
              <el-table-column prop="username" label="用户名" width="120" />
              <el-table-column prop="roleType" label="角色类型" width="120">
                <template #default="scope">
                  <el-tag :type="getRoleTagType(scope.row.roleType)">
                    {{ scope.row.roleType }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="staffId" label="员工编号" width="120" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="scope.row.status === '启用' ? 'success' : 'danger'">
                    {{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="280">
                <template #default="scope">
                  <el-button type="primary" size="small" @click="handleEditUser(scope.row)">
                    编辑
                  </el-button>
                  <el-button 
                    :type="scope.row.status === '启用' ? 'danger' : 'success'" 
                    size="small" 
                    @click="handleToggleUserStatus(scope.row)"
                  >
                    {{ scope.row.status === '启用' ? '禁用' : '启用' }}
                  </el-button>
                  <el-button type="danger" size="small" @click="handleDeleteUser(scope.row)">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <!-- 添加用户对话框 -->
            <el-dialog v-model="showAddUserDialog" title="添加用户" width="500px">
              <el-form :model="userForm" :rules="userRules" ref="userFormRef" label-width="100px">
                <el-form-item label="用户ID" prop="userId">
                  <el-input v-model="userForm.userId" placeholder="请输入用户ID" />
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                  <el-input v-model="userForm.username" placeholder="请输入用户名" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                  <el-input v-model="userForm.password" type="password" placeholder="请输入密码" />
                </el-form-item>
                <el-form-item label="角色类型" prop="roleType">
                  <el-select v-model="userForm.roleType" style="width: 100%">
                    <el-option label="管理员" value="管理员" />
                    <el-option label="医生" value="医生" />
                    <el-option label="护士" value="护士" />
                    <el-option label="财务" value="财务" />
                  </el-select>
                </el-form-item>
                <el-form-item label="员工编号" prop="staffId">
                  <el-input v-model="userForm.staffId" placeholder="请输入员工编号" />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                  <el-select v-model="userForm.status" style="width: 100%">
                    <el-option label="启用" value="启用" />
                    <el-option label="禁用" value="禁用" />
                  </el-select>
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="showAddUserDialog = false">取消</el-button>
                <el-button type="primary" @click="handleSaveUser">确定</el-button>
              </template>
            </el-dialog>

            <!-- 编辑用户对话框 -->
            <el-dialog v-model="showEditUserDialog" title="编辑用户" width="500px">
              <el-form :model="userForm" :rules="userRules" ref="editUserFormRef" label-width="100px">
                <el-form-item label="用户ID" prop="userId">
                  <el-input v-model="userForm.userId" disabled />
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                  <el-input v-model="userForm.username" />
                </el-form-item>
                <el-form-item label="角色类型" prop="roleType">
                  <el-select v-model="userForm.roleType" style="width: 100%">
                    <el-option label="管理员" value="管理员" />
                    <el-option label="医生" value="医生" />
                    <el-option label="护士" value="护士" />
                    <el-option label="财务" value="财务" />
                  </el-select>
                </el-form-item>
                <el-form-item label="员工编号" prop="staffId">
                  <el-input v-model="userForm.staffId" />
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="showEditUserDialog = false">取消</el-button>
                <el-button type="primary" @click="handleUpdateUser">保存</el-button>
              </template>
            </el-dialog>

            <!-- 删除确认对话框 -->
            <el-dialog v-model="showDeleteUserDialog" title="确认删除" width="400px">
              <p>确定要删除该用户吗？此操作不可恢复。</p>
              <template #footer>
                <el-button @click="showDeleteUserDialog = false">取消</el-button>
                <el-button type="danger" @click="confirmDeleteUser">确定</el-button>
              </template>
            </el-dialog>
          </div>
        </div>
        <div v-else-if="activeMenu === '2-1'">
          <div class="section">
            <div class="section-header">
              <h3>患者信息总览</h3>
              <div class="search-area">
                <el-input
                  v-model="patientSearchForm.keyword"
                  placeholder="输入姓名/ID搜索"
                  style="width: 200px; margin-right: 10px;"
                  @keyup.enter="handlePatientSearch"
                >
                  <template #prefix>
                    <el-icon><Search /></el-icon>
                  </template>
                </el-input>
                <el-select 
                  v-model="patientSearchForm.status" 
                  placeholder="患者状态" 
                  clearable
                  style="width: 150px; margin-right: 10px;"
                >
                  <el-option label="住院" value="住院" />
                  <el-option label="出院" value="出院" />
                  <el-option label="重症" value="重症" />
                </el-select>
                <el-button type="primary" @click="handlePatientSearch">搜索</el-button>
                <el-button @click="resetPatientSearch">重置</el-button>
              </div>
            </div>

            <el-table :data="patientList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="patientId" label="患者ID" width="120" />
              <el-table-column prop="patientName" label="姓名" width="100" />
              <el-table-column prop="gender" label="性别" width="60" />
              <el-table-column prop="age" label="年龄" width="60" />
              <el-table-column prop="phoneNumber" label="联系电话" width="120" />
              <el-table-column prop="idNumber" label="身份证号" width="180" />
              <el-table-column prop="emergencyContact" label="紧急联系人" width="100" />
              <el-table-column prop="emergencyPhone" label="紧急联系电话" width="120" />
              <el-table-column prop="patientStatus" label="状态" width="80">
                <template #default="scope">
                  <el-tag :type="getPatientStatusType(scope.row.patientStatus)">
                    {{ scope.row.patientStatus }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="diagnosis" label="诊断" width="150" show-overflow-tooltip />
              <el-table-column prop="admissionTime" label="入院时间" width="180">
                <template #default="scope">
                  {{ scope.row.admissionTime ? new Date(scope.row.admissionTime).toLocaleString() : '未入院' }}
                </template>
              </el-table-column>
              <el-table-column prop="dischargeTime" label="出院时间" width="180">
                <template #default="scope">
                  {{ scope.row.dischargeTime ? new Date(scope.row.dischargeTime).toLocaleString() : '未出院' }}
                </template>
              </el-table-column>
              <el-table-column prop="address" label="住址" min-width="200" show-overflow-tooltip />
              <el-table-column label="操作" width="120" fixed="right">
                <template #default="scope">
                  <el-button 
                    type="primary" 
                    size="small" 
                    @click="showPatientDetail(scope.row)"
                  >
                    查看详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <!-- 患者详情弹窗 -->
            <el-dialog v-model="patientDetailVisible" title="患者详情" width="600px">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="患者ID">{{ selectedPatient.patientId }}</el-descriptions-item>
                <el-descriptions-item label="姓名">{{ selectedPatient.patientName }}</el-descriptions-item>
                <el-descriptions-item label="性别">{{ selectedPatient.gender }}</el-descriptions-item>
                <el-descriptions-item label="年龄">{{ selectedPatient.age }}</el-descriptions-item>
                <el-descriptions-item label="身份证号">{{ selectedPatient.idNumber }}</el-descriptions-item>
                <el-descriptions-item label="联系电话">{{ selectedPatient.phoneNumber }}</el-descriptions-item>
                <el-descriptions-item label="紧急联系人">{{ selectedPatient.emergencyContact }}</el-descriptions-item>
                <el-descriptions-item label="紧急联系电话">{{ selectedPatient.emergencyPhone }}</el-descriptions-item>
                <el-descriptions-item label="住址">{{ selectedPatient.address }}</el-descriptions-item>
                <el-descriptions-item label="患者状态">
                  <el-tag :type="getPatientStatusType(selectedPatient.patientStatus)">
                    {{ selectedPatient.patientStatus }}
                  </el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="诊断">{{ selectedPatient.diagnosis || '暂无' }}</el-descriptions-item>
                <el-descriptions-item label="入院时间">
                  {{ selectedPatient.admissionTime ? new Date(selectedPatient.admissionTime).toLocaleString() : '未入院' }}
                </el-descriptions-item>
                <el-descriptions-item label="出院时间">
                  {{ selectedPatient.dischargeTime ? new Date(selectedPatient.dischargeTime).toLocaleString() : '未出院' }}
                </el-descriptions-item>
                <el-descriptions-item label="创建时间">
                  {{ new Date(selectedPatient.createTime).toLocaleString() }}
                </el-descriptions-item>
                <el-descriptions-item label="更新时间">
                  {{ new Date(selectedPatient.updateTime).toLocaleString() }}
                </el-descriptions-item>
              </el-descriptions>
            </el-dialog>
          </div>
        </div>
        <div v-else-if="activeMenu === '2-2'">
          <div class="section">
            <div class="section-header">
              <h3>住院/出院管理</h3>
            </div>
            <div class="tab-header">
              <el-button type="primary" @click="showAddPatientDialog = true">新增患者</el-button>
            </div>
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
        <div v-else-if="activeMenu === '3-1'">
          <div class="section">
            <div class="section-header">
              <h3>设备信息</h3>
              <div>
                <el-button @click="resetEquipmentSearch">重置</el-button>
              </div>
            </div>

            <el-table :data="equipmentList" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="deviceId" label="设备编号" width="120" />
              <el-table-column prop="deviceName" label="设备名称" width="150" />
              <el-table-column prop="deviceType" label="设备类型" width="100" />
              <el-table-column prop="modelNumber" label="型号" width="120">
                <template #default="scope">
                  {{ scope.row.modelNumber || '未设置' }}
                </template>
              </el-table-column>
              <el-table-column prop="manufacturer" label="生产厂商" width="150" />
              <el-table-column prop="purchaseDate" label="购入日期" width="180">
                <template #default="scope">
                  {{ new Date(scope.row.purchaseDate).toLocaleDateString() }}
                </template>
              </el-table-column>
              <el-table-column prop="warrantyDate" label="保修期至" width="120">
                <template #default="scope">
                  {{ scope.row.warrantyDate ? new Date(scope.row.warrantyDate).toLocaleDateString() : '未设置' }}
                </template>
              </el-table-column>
              <el-table-column prop="wardId" label="所在病房" width="120">
                <template #default="scope">
                  {{ scope.row.wardId || '未分配' }}
                </template>
              </el-table-column>
              <el-table-column prop="deviceStatus" label="状态" width="100">
                <template #default="scope">
                  <el-tag :type="getEquipmentStatusType(scope.row.deviceStatus)">
                    {{ scope.row.deviceStatus }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="创建时间" width="180">
                <template #default="scope">
                  {{ new Date(scope.row.createTime).toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="updateTime" label="更新时间" width="180">
                <template #default="scope">
                  {{ new Date(scope.row.updateTime).toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="150" fixed="right">
                <template #default="scope">
                  <el-button size="small" @click="handleEditEquipment(scope.row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="handleDeleteEquipment(scope.row.deviceId)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>

            <!-- 设备详情弹窗 -->
            <el-dialog v-model="equipmentDetailVisible" title="设备详情" width="600px">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="设备编号">{{ selectedEquipment.deviceId }}</el-descriptions-item>
                <el-descriptions-item label="设备名称">{{ selectedEquipment.deviceName }}</el-descriptions-item>
                <el-descriptions-item label="设备类型">{{ selectedEquipment.deviceType }}</el-descriptions-item>
                <el-descriptions-item label="型号">{{ selectedEquipment.modelNumber || '未设置' }}</el-descriptions-item>
                <el-descriptions-item label="生产厂商">{{ selectedEquipment.manufacturer }}</el-descriptions-item>
                <el-descriptions-item label="购入日期">
                  {{ new Date(selectedEquipment.purchaseDate).toLocaleDateString() }}
                </el-descriptions-item>
                <el-descriptions-item label="保修期至">
                  {{ selectedEquipment.warrantyDate ? new Date(selectedEquipment.warrantyDate).toLocaleDateString() : '未设置' }}
                </el-descriptions-item>
                <el-descriptions-item label="所在病房">{{ selectedEquipment.wardId || '未分配' }}</el-descriptions-item>
                <el-descriptions-item label="设备状态">
                  <el-tag :type="getEquipmentStatusType(selectedEquipment.deviceStatus)">
                    {{ selectedEquipment.deviceStatus }}
                  </el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="创建时间">
                  {{ new Date(selectedEquipment.createTime).toLocaleString() }}
                </el-descriptions-item>
                <el-descriptions-item label="更新时间">
                  {{ new Date(selectedEquipment.updateTime).toLocaleString() }}
                </el-descriptions-item>
              </el-descriptions>
            </el-dialog>

            <!-- 编辑设备模态框 -->
            <el-dialog title="编辑设备信息" v-model="editEquipmentDialogVisible" width="500px">
              <el-form v-if="editingEquipment" :model="editingEquipment" :rules="editEquipmentFormRules" ref="editEquipmentFormRef" label-width="100px">
                <el-form-item label="设备编号" prop="deviceId">
                  <el-input v-model="editingEquipment.deviceId" disabled />
                </el-form-item>
                <el-form-item label="设备名称" prop="deviceName">
                  <el-input v-model="editingEquipment.deviceName" />
                </el-form-item>
                <el-form-item label="设备类型" prop="deviceType">
                  <el-input v-model="editingEquipment.deviceType" />
                </el-form-item>
                <el-form-item label="状态" prop="deviceStatus">
                  <el-input v-model="editingEquipment.deviceStatus" />
                </el-form-item>
                <el-form-item label="存放位置" prop="wardId">
                  <el-input v-model="editingEquipment.wardId" />
                </el-form-item>
                <el-form-item label="型号" prop="modelNumber">
                  <el-input v-model="editingEquipment.modelNumber" />
                </el-form-item>
                <el-form-item label="制造商" prop="manufacturer">
                  <el-input v-model="editingEquipment.manufacturer" />
                </el-form-item>
                <el-form-item label="购买日期" prop="purchaseDate">
                  <el-date-picker v-model="editingEquipment!.purchaseDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="保修日期" prop="warrantyDate">
                  <el-date-picker v-model="editingEquipment!.warrantyDate" type="date" placeholder="选择日期" style="width: 100%;" value-format="YYYY-MM-DD" />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="editEquipmentDialogVisible = false">取消</el-button>
                  <el-button type="primary" @click="handleSaveEquipmentEdit">保存</el-button>
                </span>
              </template>
            </el-dialog>
          </div>
        </div>
        <div v-else-if="activeMenu === '3-2'">
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
        <div v-else-if="activeMenu === '3-3'">
          <div class="section">
            <div class="section-header">
              <h3>设备使用记录</h3>
              <div>
                <el-button type="primary" @click="addUsageRecordDialogVisible = true">记录使用</el-button>
              </div>
            </div>
            
            <el-table :data="usageRecords" style="width: 100%; margin-top: 20px;">
              <el-table-column prop="deviceId" label="设备编号" width="150" />
              <el-table-column prop="serviceTime" label="使用时间" width="180">
                 <template #default="scope">
                  {{ new Date(scope.row.serviceTime).toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="useAnnotations" label="使用备注" />
            </el-table>

            <!-- 添加设备使用记录对话框 -->
            <el-dialog v-model="addUsageRecordDialogVisible" title="记录设备使用" width="400px">
              <el-form :model="addUsageRecordForm" :rules="addUsageRecordRules" ref="addUsageRecordFormRef" label-width="100px">
                <el-form-item label="设备编号" prop="deviceId">
                  <el-input v-model="addUsageRecordForm.deviceId" placeholder="请输入设备编号"></el-input>
                </el-form-item>
                <el-form-item label="使用备注" prop="useAnnotations">
                  <el-input v-model="addUsageRecordForm.useAnnotations" type="textarea" placeholder="请输入使用备注"></el-input>
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="addUsageRecordDialogVisible = false">取消</el-button>
                  <el-button type="primary" :loading="addUsageRecordLoading" @click="handleAddUsageRecordConfirm">确定</el-button>
                </span>
              </template>
            </el-dialog>
          </div>
        </div>
        <div v-else-if="activeMenu === '4'">
          <div class="section">
            <div class="section-header">
              <h3>病房管理</h3>
              <div>
                <el-button @click="fetchWards">刷新</el-button>
              </div>
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
                    :percentage="getBedUsagePercent(scope.row.wardId, scope.row)"
                    :format="() => getBedUsageFormat(scope.row.wardId, scope.row)"
                    :status="getProgressStatusByWardId(scope.row.wardId, scope.row)"
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
                  <el-tag type="success">可用床位：{{ availableBeds }}</el-tag>
                  <el-tag type="info" style="margin-left: 10px">总床位：{{ selectedWard.totalBeds }}</el-tag>
                </div>
              </div>

              <el-table :data="bedList" style="width: 100%; margin-top: 20px;">
                <el-table-column prop="bedNumber" label="床位号" width="100" align="center" />
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
        <div v-else-if="activeMenu === '5-1'">
          <div class="section"><h3>费用总览</h3><div style="color:#999;">此处可扩展全院费用统计、查询、导出等功能</div></div>
        </div>
        <div v-else-if="activeMenu === '5-2'">
          <div class="section"><h3>设备采购记录</h3><div style="color:#999;">此处可扩展设备采购记录管理功能</div></div>
        </div>
        <div v-else-if="activeMenu === '6-1'">
          <div class="section"><h3>基础信息设置</h3><div style="color:#999;">此处可扩展医院基础信息设置功能</div></div>
        </div>
        <div v-else-if="activeMenu === '6-2'">
          <div class="section"><h3>日志审计</h3><div style="color:#999;">此处可扩展操作日志、审计等功能</div></div>
        </div>
        <div v-else-if="activeMenu === '6-3'">
          <div class="section"><h3>数据备份/恢复</h3><div style="color:#999;">此处可扩展数据备份、恢复、导入导出等功能</div></div>
        </div>
        <div v-else>
          <div class="section"><h3>欢迎来到管理员工作台</h3><div style="color:#999;">请选择左侧功能进行管理</div></div>
        </div>
      </el-main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { User, Files, Document, List, DataLine, Setting, Search } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { ElMessageBox } from 'element-plus';
import axios from 'axios';

// 接口定义
interface Doctor {
  staffId: string;
  staffName: string;
  gender: string;
  age: number;
  department: string;
  title: string;
  phoneNumber: string;
  createTime: string;
  updateTime: string;
}

interface Nurse {
  staffId: string;
  staffName: string;
  gender: string;
  age: number;
  department: string;
  title: string;
  phoneNumber: string;
  createTime: string;
  updateTime: string;
}

interface User {
  userId: string;
  username: string;
  password?: string;
  roleType: string;
  staffId: string | null;
  status: string;
  lastLogin?: string;
}

interface FinanceStaff {
  userId: string;
  username: string;
  roleType: string;
  staffId: string | null;
  status: string;
  lastLoginTime: string | null;
  createTime: string;
  updateTime: string;
}

// 患者相关
interface Patient {
  patientId: string;
  patientName: string;
  gender: string;
  age: number;
  phoneNumber: string;
  idNumber: string;
  address: string;
  emergencyContact: string;
  emergencyPhone: string;
  patientStatus: string;
  diagnosis: string | null;
  admissionTime: string | null;
  dischargeTime: string | null;
  createTime: string;
  updateTime: string;
}

// 设备相关
interface Equipment {
  deviceId: string;
  deviceName: string;
  deviceType: string;
  modelNumber: string | null;
  manufacturer: string;
  purchaseDate: string;
  warrantyDate: string | null;
  wardId: string | null;
  deviceStatus: string;
  createTime: string;
  updateTime: string;
}

interface MaintenanceRecord {
  recordId: string;
  equipmentId: string;
  maintenanceType: string;
  maintenanceTime: string;
  operator: string;
  cost: number;
  result: string;
  description: string;
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

// 设备使用记录相关
interface DeviceUsageRecord {
  deviceId: string;
  serviceTime: string;
  useAnnotations: string | null;
}

// 病房管理相关数据 (从 NurseDashboard.vue 复制)
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

interface BedDisplay {
  bedNumber: string;
  bedStatus: string;
  wardType: string;
  price: string;
}

const router = useRouter();
const goLogin = () => {
  router.push('/login');
};
const adminName = '管理员';
const activeMenu = ref('1-1');


// 设置 axios 默认配置
axios.defaults.baseURL = 'http://localhost:8080';

// 添加请求拦截器
axios.interceptors.request.use(config => {
  console.log('发送请求:', {
    url: config.url,
    method: config.method,
    data: config.data
  });
  return config;
});

// 添加响应拦截器
axios.interceptors.response.use(
  response => {
    console.log('接收响应:', {
      status: response.status,
      data: response.data
    });
    return response;
  },
  error => {
    console.error('请求错误:', {
      message: error.message,
      response: error.response?.data
    });
    return Promise.reject(error);
  }
);

// 医生管理相关
const doctorList = ref<Doctor[]>([]);
const doctorSearchForm = ref({
  staffId: '',
  staffName: '',
  department: '',
  title: ''
});

const fetchDoctorList = async () => {
  try {
    const res = await axios.get('/api/doctor-views');
    doctorList.value = res.data;
  } catch (e) {
    console.error('获取医生列表失败:', e);
    ElMessage.error('获取医生列表失败');
    doctorList.value = [];
  }
};

const handleDoctorSearch = async () => {
  try {
    let url = '/api/doctor-views';
    const { staffId, staffName, department, title } = doctorSearchForm.value;
    
    if (staffId) {
      url = `/api/doctor-views/${staffId}`;
    } else if (staffName) {
      url = `/api/doctor-views/name/${staffName}`;
    } else if (department) {
      url = `/api/doctor-views/department/${department}`;
    } else if (title) {
      url = `/api/doctor-views/title/${title}`;
    }
    
    const res = await axios.get(url);
    doctorList.value = Array.isArray(res.data) ? res.data : [res.data];
  } catch (e) {
    console.error('搜索失败:', e);
    ElMessage.error('搜索失败');
  }
};

const resetDoctorSearch = () => {
  doctorSearchForm.value = {
    staffId: '',
    staffName: '',
    department: '',
    title: ''
  };
  fetchDoctorList();
};

// 护士管理相关
const nurseList = ref<Nurse[]>([]);
const nurseSearchForm = ref({
  staffId: '',
  staffName: '',
  department: '',
  title: ''
});

const fetchNurseList = async () => {
  try {
    const res = await axios.get('/api/nurse-views');
    nurseList.value = res.data;
  } catch (e) {
    console.error('获取护士列表失败:', e);
    ElMessage.error('获取护士列表失败');
    nurseList.value = [];
  }
};

const handleNurseSearch = async () => {
  try {
    let url = '/api/nurse-views';
    const { staffId, staffName, department, title } = nurseSearchForm.value;
    
    if (staffId) {
      url = `/api/nurse-views/${staffId}`;
    } else if (staffName) {
      url = `/api/nurse-views/name/${staffName}`;
    } else if (department) {
      url = `/api/nurse-views/department/${department}`;
    } else if (title) {
      url = `/api/nurse-views/title/${title}`;
    }
    
    const res = await axios.get(url);
    nurseList.value = Array.isArray(res.data) ? res.data : [res.data];
  } catch (e) {
    console.error('搜索失败:', e);
    ElMessage.error('搜索失败');
  }
};

const resetNurseSearch = () => {
  nurseSearchForm.value = {
    staffId: '',
    staffName: '',
    department: '',
    title: ''
  };
  fetchNurseList();
};

// 家属管理相关
const familyList = ref([]);
const familySearchInput = ref('');
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const getPatientStatusType = (status: string) => {
  switch (status) {
    case '住院中':
      return 'success';
    case '出院':
      return 'info';
    case '重症':
      return 'danger';
    default:
      return 'warning';
  }
};

const fetchAllFamilies = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/patient-family-views');
    familyList.value = res.data;
    total.value = res.data.length;
    familySearchInput.value = '';
  } catch (e) {
    ElMessage.error('获取家属信息失败');
    familyList.value = [];
  }
};

const handleFamilySearch = async () => {
  if (!familySearchInput.value.trim()) {
    fetchAllFamilies();
    return;
  }
  try {
    const res = await axios.get(`http://localhost:8080/api/patient-family-views/search?patientName=${encodeURIComponent(familySearchInput.value.trim())}`);
    familyList.value = res.data;
    total.value = res.data.length;
    currentPage.value = 1;
  } catch (e) {
    ElMessage.error('搜索失败');
  }
};

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  fetchAllFamilies();
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
};

// 用户管理相关
const userList = ref<User[]>([]);
const showAddUserDialog = ref(false);
const showEditUserDialog = ref(false);
const showDeleteUserDialog = ref(false);
const userFormRef = ref();
const editUserFormRef = ref();
const userToDelete = ref<User | null>(null);

const userForm = ref<User>({
  userId: '',
  username: '',
  password: '',
  roleType: '',
  staffId: null,
  status: '启用'
});

const userRules = {
  userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  roleType: [{ required: true, message: '请选择角色类型', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
};

const getRoleTagType = (roleType: string) => {
  switch (roleType) {
    case '管理员':
      return 'danger';
    case '医生':
      return 'success';
    case '护士':
      return 'warning';
    case '财务':
      return 'info';
    default:
      return 'default';
  }
};

const fetchUserList = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/users');
    userList.value = res.data;
  } catch (e) {
    ElMessage.error('获取用户列表失败');
    userList.value = [];
  }
};

const handleEditUser = (user: any) => {
  userForm.value = { ...user };
  showEditUserDialog.value = true;
};

const handleDeleteUser = (user: any) => {
  userToDelete.value = user;
  showDeleteUserDialog.value = true;
};

const handleToggleUserStatus = async (user: any) => {
  try {
    const newStatus = user.status === '启用' ? '禁用' : '启用';
    await axios.put(`http://localhost:8080/api/users/${user.userId}`, {
      ...user,
      status: newStatus
    });
    ElMessage.success(`${newStatus}用户成功`);
    fetchUserList();
  } catch (e) {
    ElMessage.error('操作失败');
  }
};

const handleSaveUser = () => {
  userFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      await axios.post('http://localhost:8080/api/users', userForm.value);
      ElMessage.success('添加用户成功');
      showAddUserDialog.value = false;
      fetchUserList();
      // 重置表单
      userForm.value = {
        userId: '',
        username: '',
        password: '',
        roleType: '',
        staffId: null,
        status: '启用'
      };
    } catch (e) {
      ElMessage.error('添加用户失败');
    }
  });
};

const handleUpdateUser = () => {
  editUserFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      await axios.put(`http://localhost:8080/api/users/${userForm.value.userId}`, userForm.value);
      ElMessage.success('更新成功');
      showEditUserDialog.value = false;
      fetchUserList();
    } catch (e) {
      ElMessage.error('更新失败');
    }
  });
};

const confirmDeleteUser = async () => {
  if (!userToDelete.value) return;
  try {
    await axios.delete(`http://localhost:8080/api/users/${userToDelete.value.userId}`);
    ElMessage.success('删除成功');
    showDeleteUserDialog.value = false;
    fetchUserList();
  } catch (e) {
    ElMessage.error('删除失败');
  }
};

// 财务人员管理相关
const financeStaffList = ref<FinanceStaff[]>([]);
const showFinanceEditDialog = ref(false);
const financeEditForm = ref<FinanceStaff>({} as FinanceStaff);
const financeEditFormRef = ref();

const formRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
};

const fetchFinanceStaffList = async () => {
  try {
    console.log('开始获取财务人员数据');
    const res = await axios.get('/api/users');
    console.log('原始响应数据:', res.data);
    
    // 确保数据是数组
    if (!Array.isArray(res.data)) {
      console.warn('API返回的数据不是数组:', res.data);
      financeStaffList.value = [];
      return;
    }
    
    // 过滤财务人员并打印结果
    const financeStaff = res.data.filter((user: any) => user.roleType === '财务');
    console.log('过滤后的财务人员数据:', financeStaff);
    
    financeStaffList.value = financeStaff;
    
    if (financeStaff.length === 0) {
      console.warn('没有找到财务人员数据');
      ElMessage.warning('暂无财务人员数据');
    } else {
      console.log('成功设置财务人员列表:', financeStaffList.value);
    }
  } catch (e) {
    console.error('获取财务人员列表失败:', e);
    ElMessage.error('获取财务人员列表失败');
    financeStaffList.value = [];
  }
};

const handleEditFinanceStaff = (staff: FinanceStaff) => {
  financeEditForm.value = { ...staff };
  showFinanceEditDialog.value = true;
};

const handleSaveFinanceStaff = () => {
  financeEditFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      await axios.put(`/api/users/${financeEditForm.value.userId}`, financeEditForm.value);
      ElMessage.success('更新成功');
      showFinanceEditDialog.value = false;
      fetchFinanceStaffList();
    } catch (e) {
      ElMessage.error('更新失败');
    }
  });
};

// 患者管理相关
const patientList = ref<Patient[]>([]);
const patientSearchForm = ref({
  keyword: '',
  status: ''
});
const selectedPatient = ref<Patient>({} as Patient);
const patientDetailVisible = ref(false);

const fetchPatientList = async () => {
  try {
    const res = await axios.get('/admin/patients');
    patientList.value = res.data;
  } catch (e) {
    console.error('获取患者列表失败:', e);
    ElMessage.error('获取患者列表失败');
    patientList.value = [];
  }
};

const handlePatientSearch = async () => {
  try {
    const { keyword, status } = patientSearchForm.value;
    const params: any = {};
    if (keyword) params.keyword = keyword;
    if (status) params.status = status;
    
    const res = await axios.get('/admin/patients', { params });
    patientList.value = res.data;
  } catch (e) {
    console.error('搜索失败:', e);
    ElMessage.error('搜索失败');
  }
};

const resetPatientSearch = () => {
  patientSearchForm.value = {
    keyword: '',
    status: ''
  };
  fetchPatientList();
};

const showPatientDetail = (patient: Patient) => {
  selectedPatient.value = patient;
  patientDetailVisible.value = true;
};

// 设备管理相关
const equipmentList = ref<Equipment[]>([]);
const equipmentSearchForm = ref({
  keyword: '',
  deviceStatus: '',
  deviceType: ''
});
const selectedEquipment = ref<Equipment>({} as Equipment);
const equipmentDetailVisible = ref(false);
const maintenanceRecordVisible = ref(false);
const maintenanceRecords = ref<MaintenanceRecord[]>([]);
const editingEquipment = ref<Equipment | null>(null);
const editEquipmentDialogVisible = ref(false);
const editEquipmentFormRef = ref();
const editEquipmentFormRules = {
  deviceName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  deviceType: [{ required: true, message: '请输入设备类型', trigger: 'blur' }],
  deviceStatus: [{ required: true, message: '请输入设备状态', trigger: 'blur' }],
};

const fetchEquipmentList = async () => {
  try {
    const res = await axios.get('/api/medical-devices');
    equipmentList.value = res.data;
  } catch (e) {
    console.error('获取设备列表失败:', e);
    ElMessage.error('获取设备列表失败');
    equipmentList.value = [];
  }
};

const handleEditEquipment = (equipment: Equipment) => {
  editingEquipment.value = { ...equipment };
  editEquipmentDialogVisible.value = true;
};

const handleDeleteEquipment = async (deviceId: string) => {
  try {
    await axios.delete(`http://localhost:8080/api/medical-devices/${deviceId}`);
    ElMessage.success('设备删除成功');
    fetchEquipmentList(); // 刷新列表
  } catch (e) {
    ElMessage.error('设备删除失败');
    console.error(e);
  }
};

const handleSaveEquipmentEdit = async () => {
  if (!editEquipmentFormRef.value || !editingEquipment.value) {
    ElMessage.error('当前没有可保存的设备信息');
    return;
  }

  const equipmentToUpdate: any = { ...editingEquipment.value };
  if (equipmentToUpdate.purchaseDate instanceof Date) {
    equipmentToUpdate.purchaseDate = equipmentToUpdate.purchaseDate.toISOString().split('T')[0];
  }
  if (equipmentToUpdate.warrantyDate instanceof Date) {
    equipmentToUpdate.warrantyDate = equipmentToUpdate.warrantyDate.toISOString().split('T')[0];
  }

  editEquipmentFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        const res = await axios.put(`http://localhost:8080/api/medical-devices/${equipmentToUpdate.deviceId}`, equipmentToUpdate);
        if (res.data.success) {
          ElMessage.success('设备信息更新成功');
          editEquipmentDialogVisible.value = false;
          fetchEquipmentList(); // 刷新列表
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

const getEquipmentStatusType = (status: string) => {
  switch (status) {
    case '正常':
      return 'success';
    case '维修中':
      return 'warning';
    case '报废':
      return 'danger';
    default:
      return 'info';
  }
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
  quantity: [{ required: true, message: '请输入采购数量', trigger: 'blur' }]
};
const purchaseList = ref<PurchaseForm[]>([]); // This list is for display in the table, will keep for now but data comes from backend

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
        // Optionally refresh the device list table if needed, although this is for '设备采购' not '设备信息'
        // fetchEquipmentList(); // Call the fetch function in AdminDashboard
      } else {
        ElMessage.error(res.data.message || '添加设备失败');
      }
    } catch (error) {
      console.error('添加设备时发生错误:', error);
      ElMessage.error('添加设备时发生错误');
    }
  });
};

const handleDeletePurchase = (index: number) => {
  purchaseList.value.splice(index, 1);
  ElMessage.success('删除成功');
};

const resetEquipmentSearch = () => {
  // Since search inputs were removed, reset just calls fetchEquipmentList to refresh the list
  fetchEquipmentList();
};

// 病房管理相关数据 (从 NurseDashboard.vue 复制)
const wardList = ref<Ward[]>([]);
const showWardDetailsDialog = ref(false);
const selectedWard = ref<Ward | null>(null);
const bedList = ref<BedDisplay[]>([]);
// 用 computed 统计可用床位，兼容多种 bedStatus 取值
const availableBeds = computed(() => {
  return bedList.value.filter(
    bed => bed.bedStatus === '空闲' || bed.bedStatus === 'free' || bed.bedStatus === '可用'
  ).length;
});
// 病房床位统计映射表
const wardBedStats = ref<Record<string, { total: number, occupied: number }>>({});

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
const getProgressStatusByWardId = (wardId: string, row: any) => {
  const stat = wardBedStats.value[wardId];
  const occupied = stat ? stat.occupied : row.occupiedBeds;
  const total = stat ? stat.total : row.totalBeds;
  const percent = total ? (occupied / total) * 100 : 0;
  if (percent >= 90) return 'exception';
  if (percent >= 70) return 'warning';
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
      bedStatus: bed.bedStatus || '空闲',
      wardType: ward.wardType || '-',  // 使用当前病房的类型
      price: bed.bedPrice ? `${bed.bedPrice}元/天` : '-'  // 使用后端返回的价格
    }));

    // 按床位号排序
    bedList.value.sort((a, b) => parseInt(a.bedNumber) - parseInt(b.bedNumber));

    // 统计可用床位数（已用 computed）
    // 统计进度条用的床位总数和占用数
    wardBedStats.value[ward.wardId] = {
      total: bedList.value.length,
      occupied: bedList.value.filter(bed => bed.bedStatus === '占用' || bed.bedStatus === 'occupied').length
    };
  } catch (e) {
    ElMessage.error('获取床位信息失败');
    console.error('Error loading bed details:', e);
  }
};

const fetchWards = async () => {
  try {
    const res = await axios.get('/api/wards');
    wardList.value = res.data;
  } catch (e) {
    console.error('获取病房列表失败:', e);
    ElMessage.error('获取病房列表失败');
    wardList.value = [];
  }
};

// 设备使用记录相关变量和函数
const usageRecords = ref<DeviceUsageRecord[]>([]);
const addUsageRecordDialogVisible = ref(false);
const addUsageRecordFormRef = ref();
const addUsageRecordForm = ref<DeviceUsageRecord>({ deviceId: '', serviceTime: '', useAnnotations: null });
const addUsageRecordRules = {
  deviceId: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
};
const addUsageRecordLoading = ref(false);

const fetchUsageRecords = async () => {
  try {
    const res = await axios.get<DeviceUsageRecord[]>('/api/device-usage-records');
    usageRecords.value = res.data;
  } catch (e) {
    console.error('获取设备使用记录失败:', e);
    ElMessage.error('获取设备使用记录失败');
    usageRecords.value = [];
  }
};

const handleAddUsageRecordConfirm = () => {
  addUsageRecordFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    addUsageRecordLoading.value = true;
    try {
      // serviceTime will be set in backend
      const payload = {
        deviceId: addUsageRecordForm.value.deviceId,
        useAnnotations: addUsageRecordForm.value.useAnnotations
      };
      await axios.post('/api/device-usage-records', payload);
      ElMessage.success('设备使用记录添加成功');
      addUsageRecordDialogVisible.value = false;
      // Clear form and refresh list
      addUsageRecordForm.value = { deviceId: '', serviceTime: '', useAnnotations: null };
      fetchUsageRecords();
    } catch (e) {
      console.error('添加设备使用记录失败:', e);
      ElMessage.error('添加设备使用记录失败');
    } finally {
      addUsageRecordLoading.value = false;
    }
  });
};

onMounted(() => {
  console.log('组件挂载，开始获取数据');
  fetchAllFamilies();
  fetchUserList();
  fetchFinanceStaffList();
  fetchDoctorList();
  fetchNurseList();
  if (activeMenu.value === '2-1') {
    fetchPatientList();
  } else if (activeMenu.value === '3-1') {
    fetchEquipmentList();
  } else if (activeMenu.value === '3-3') {
    fetchUsageRecords(); // Fetch records when initially on this tab
  }
});

// Update handleMenuSelect to fetch data for the new tab
const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
  // Fetch data based on selected menu item
  if (index === '1-1') {
    fetchDoctorList();
  } else if (index === '1-2') {
    fetchNurseList();
  } else if (index === '1-3') {
    fetchAllFamilies(); // Or handle pagination if needed
  } else if (index === '1-4') {
    fetchFinanceStaffList();
  } else if (index === '1-5') {
    fetchUserList();
  } else if (index === '2-1') {
    fetchPatientList();
  } else if (index === '3-1') {
    fetchEquipmentList();
  } else if (index === '3-3') {
    fetchUsageRecords();
  }
  // Add conditions for other menu items as they are implemented
};

// 住院/出院管理相关变量和方法（从 NurseDashboard.vue 复制并适配）
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
const patientOptions = ref<any[]>([]);
const searchLoading = ref(false);
const submitting = ref(false);

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

const searchPatients = async (query: string) => {
  if (query.length < 1) {
    patientOptions.value = [];
    return;
  }
  searchLoading.value = true;
  try {
    const res = await axios.get('/admin/patients/search', {
      params: {
        keyword: query,
        limit: 10
      }
    });
    patientOptions.value = res.data.map((patient: any) => ({
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

const handleAddPatient = () => {
  addPatientFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      const payload = {
        ...addPatientForm.value,
        admissionTime: new Date().toISOString(),
        patientStatus: '住院'
      };
      await axios.post('/admin/patients', payload);
      ElMessage.success('新增患者成功');
      showAddPatientDialog.value = false;
      fetchPatientList();
    } catch (e) {
      ElMessage.error('新增失败');
    }
  });
};

const handleEditPatient = (patient: any) => {
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

const handleAssignBed = (patient: any) => {
  assignmentForm.value = {
    patientId: patient.patientId,
    wardId: '',
    bedNumber: ''
  };
  showAssignmentDialog.value = true;
};

const handleAssignmentSubmit = () => {
  assignmentFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    submitting.value = true;
    try {
      await axios.post('/room-assignments', {
        patientId: assignmentForm.value.patientId,
        wardId: assignmentForm.value.wardId,
        bedNumber: assignmentForm.value.bedNumber
      });
      ElMessage.success('病房分配成功');
      showAssignmentDialog.value = false;
      fetchWards();
      fetchPatientList();
    } catch (e) {
      ElMessage.error('病房分配失败');
    } finally {
      submitting.value = false;
    }
  });
};

const handleDischarge = async (patient: any) => {
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
    let bedId = '';
    try {
      const res = await axios.get('/api/patients/patient-bed', {
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
    await axios.post('/room-assignments/discharge', {
      patientId: patient.patientId,
      bedId: bedId
    });
    ElMessage.success('出院办理成功');
    fetchPatientList();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('出院办理失败');
    }
  }
};

// 医生管理相关
const showAddDoctorDialog = ref(false);
const addDoctorFormRef = ref();
const addDoctorForm = ref({
  staffId: '',
  staffName: '',
  gender: '',
  age: 0,
  department: '',
  title: '',
  phoneNumber: '',
  position: '医生'  // 添加 position 字段，默认值为"医生"
});
const showEditDoctorDialog = ref(false);
const editDoctorFormRef = ref();
const editDoctorForm = ref({
  staffId: '',
  staffName: '',
  gender: '',
  age: 0,
  department: '',
  title: '',
  phoneNumber: ''
});
const addDoctorRules = {
  staffId: [{ required: true, message: '请输入员工ID', trigger: 'blur' }],
  staffName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  department: [{ required: true, message: '请输入科室', trigger: 'blur' }],
  title: [{ required: true, message: '请输入职称', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
};

const handleAddDoctor = () => {
  addDoctorFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      await axios.post('/api/medical-workers', addDoctorForm.value);
      ElMessage.success('添加医生成功');
      showAddDoctorDialog.value = false;
      fetchDoctorList();
    } catch (e) {
      ElMessage.error('添加医生失败');
    }
  });
};

const handleUpdateDoctor = () => {
  editDoctorFormRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;
    try {
      await axios.put(`/api/medical-workers/${editDoctorForm.value.staffId}`, editDoctorForm.value);
      ElMessage.success('更新医生信息成功');
      showEditDoctorDialog.value = false;
      fetchDoctorList();
    } catch (e) {
      ElMessage.error('更新医生信息失败');
    }
  });
};

const handleEditDoctor = (doctor: any) => {
  editDoctorForm.value = { ...doctor };
  showEditDoctorDialog.value = true;
};

const handleDeleteDoctor = (doctor: any) => {
  ElMessageBox.confirm('确定要删除该医生吗？此操作不可恢复。', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    axios.delete(`/api/medical-workers/${doctor.staffId}`).then(() => {
      ElMessage.success('医生删除成功');
      fetchDoctorList();
    }).catch(error => {
      ElMessage.error('删除医生失败');
    });
  }).catch(() => {});
};

// 进度条相关方法
function getBedUsagePercent(wardId: string, row: any) {
  const stat = wardBedStats.value[wardId];
  if (stat && stat.total > 0) {
    return Math.round((stat.occupied / stat.total) * 100);
  }
  return Math.round((row.occupiedBeds / row.totalBeds) * 100);
}
function getBedUsageFormat(wardId: string, row: any) {
  const stat = wardBedStats.value[wardId];
  if (stat) {
    return `${stat.occupied}/${stat.total}`;
  }
  return `${row.occupiedBeds}/${row.totalBeds}`;
}
</script>

<style scoped>
.admin-dashboard {
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
  position: relative;
}
.header-left {
  flex: 0 0 auto;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-left: auto;
}
.welcome {
  font-weight: bold;
  color: #409EFF;
  margin-right: 18px;
  font-size: 18px;
}
.logout-btn {
  margin-left: 20px;
  position: static;
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
.search-area {
  display: flex;
  align-items: center;
  gap: 10px;
}
.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style> 