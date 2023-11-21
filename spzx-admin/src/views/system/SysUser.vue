<template>
  <div class="search-div">
    <el-row>
      <!-- 搜索表单 -->
      <el-form label-width="70px" size="small" @keyup.enter="fetchSysUserList">
        <el-row>
          <!-- 关键字 -->
          <el-col :span="8">
            <el-form-item label="关键字">
              <el-input style="width: 100%" placeholder="用户名" v-model="sysUserDto.keyword" ></el-input>
            </el-form-item>
          </el-col>
          
          <!-- 创建时间 -->
          <el-col :span="8">
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="createTimes"
                type="daterange"
                range-separator="到"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                @change="fetchSysUserList"
              />
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <!-- 搜索按钮 -->
            <el-button type="primary" size="small" @click="fetchSysUserList" style="margin-left: 12px">搜索</el-button>
            
            <!-- 重置按钮 -->
            <el-button size="small" @click="reset">重置</el-button>

            <!-- 添加按钮 -->
            <el-button type="success" size="small" @click="showAddDialog">添 加</el-button>

          </el-col>
        </el-row>
      </el-form>
    </el-row>

    <!--- 用户表格数据 -->
    <el-table :data="sysUserList.list" style="width: 100%" height="515px">
      <el-table-column prop="userName" label="用户名" width="140" />
      <el-table-column prop="name" label="姓名" width="140" />
      <el-table-column prop="phone" label="手机" width="140" />
      <el-table-column prop="avatar" label="头像" #default="scope" width="140">
        <img :src="scope.row.avatar" width="50" />
      </el-table-column>
      <el-table-column prop="description" label="描述" width="140" />
      <el-table-column prop="status" label="状态" #default="scope" width="140">
        {{ scope.row.status === 1 ? '正常' : '停用' }}
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="240" #default="scope">
        <el-button type="primary" size="small" @click="showUpdateDialog(scope.row)">修改</el-button>

        <el-button type="warning" size="small" @click="showRoleAssignDialog(scope.row)">分配角色</el-button>

        <el-button type="danger" size="small" @click="removeSysUser(scope.row)">删除</el-button>
      </el-table-column>
    </el-table>

    <!--分页条-->
    <el-pagination
      v-model:current-page="sysUserList.pageNum"
      v-model:page-size="sysUserList.pageSize"
      :page-sizes="[3, 5, 10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      :total="sysUserList.total"
    />

    <!-- 修改 和 新增 -->
    <el-dialog v-model="dialog.visable" :title="dialog.title" width="40%" @keyup.enter="updateSysUser">
      <el-form label-width="120px">
        <el-form-item label="用户名：">
          <el-input v-model="sysUser.userName" />
        </el-form-item>
        <el-form-item v-if="sysUser.id == null" label="密码：">
          <el-input type="password" show-password v-model="sysUser.password" />
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="sysUser.name" />
        </el-form-item>
        <el-form-item label="手机：">
          <el-input v-model="sysUser.phone" />
        </el-form-item>
        <el-form-item label="头像：">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8501/admin/system/fileUpload/avatar"
            method="post"
            :show-file-list="false"
            :headers="headers"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="sysUser.avatar" :src="sysUser.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述：">
          <el-input v-model="sysUser.description" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="updateSysUser">确定</el-button>
          <el-button @click="dialog.visable = false">取消</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 分配角色 -->
    <el-dialog
      title="分配角色"
      v-model="roleAssignDialogVisible"
      width="40%">
      <el-form>
        <el-form-item label="用户名">
          <el-input disabled :value="sysUser.userName"/>
        </el-form-item>
        <el-form-item label="角色列表">
          <el-checkbox-group v-model="roles.assigned">
            <el-checkbox v-for="role in roles.all" :label="role.id" :key="role.id">
              {{ role.roleName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="assignUserRole">确 定</el-button>
          <el-button @click="roleAssignDialogVisible = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { FindSysUserPage, SaveSysUser, RemoveSysUser, UpdateSysUser, FindSysUserRoles, AssignSysUserRoles } from '@/api/SysUser'
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, onMounted, reactive, ref, toRefs, watch } from 'vue'
import { useApp } from '@/pinia/modules/app'
import md5 from 'crypto-js/md5'

// 请求头
const headers = {
  token: useApp().authorization.token
}

// 创建时间
const createTimes = ref([])
const sysUserDto = reactive({
  keyword: '',
  createTimeBegin: computed(() => createTimes.value[0]),
  createTimeEnd: computed(() => createTimes.value[1]),
})

let sysUser = ref({
  id: '',
   userName: '',
   password: '',
   name: '',
   phone: '',
   avatar: '',
   description: ''
})

// 更新对话框
const dialog = reactive({
  visable: false,
  title: ''
})

// 角色列表（后端查询用户角色返回的数据）
const roles = reactive({
  all: [],
  assigned: []
})

// 分配角色对话框
const roleAssignDialogVisible = ref(false)

// 分配角色 ------------------------------------------------------------------------------------------
const showRoleAssignDialog = async (row) => {
  sysUser.value.id = row.id
  sysUser.value.userName = row.userName
  const { data, code } = await FindSysUserRoles(row.id)
  if (code !== 200) {
    ElMessage.error('获取用户角色信息失败')
    throw new Error('获取用户角色信息失败')
  }
  roles.all = data.all
  roles.assigned = data.assigned
  roleAssignDialogVisible.value = true
}

const assignUserRole = async () => {
  const { code } = await AssignSysUserRoles({userId: sysUser.value.id, roleIdList: roles.assigned})
  roleAssignDialogVisible.value = false
  if (code === 200) {
    ElMessage.success('分配用户角色成功')
  } else {
    ElMessage.error('分配用户角色失败')
  }
}

// 头像上传 ------------------------------------------------------------------------------------------
const handleAvatarSuccess = (response, uploadFile) => {
  console.log(useApp());
  sysUser.value.avatar = response.data
}

// 删除 ------------------------------------------------------------------------------------------
const removeSysUser = (row) => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', `用户：${row.userName}`, {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(
    async () => {
      const { code } = await RemoveSysUser(row.id)
      if (code === 200) {
        fetchSysUserList()
        ElMessage.success('删除用户成功')
      } else {
        ElMessage.error('删除用户失败')
      }
    },
    () => {
      // 取消
    }
  ).catch((e) => {
            ElMessage.error('错误，请查看控制台信息')
    console.log(e);
  })
}

// 添加或修改 ------------------------------------------------------------------------------------------
const updateSysUser = async () => {
  if (sysUser.value.id) {
    // 修改用户
    const { code } = await UpdateSysUser(sysUser.value)
    if (code === 200) {
      fetchSysUserList()
      ElMessage.success('修改用户成功')
    } else {
      ElMessage.error('修改用户失败')
    }
  } else {
    // 添加用户
    const { code } = await SaveSysUser(sysUser.value)
    if (code === 200) {
      fetchSysUserList()
      ElMessage.success('添加用户成功')
    } else {
      ElMessage.error('添加用户失败')
    }
  }
  dialog.visable = false
}

const showAddDialog = () => {
  dialog.visable = true
  dialog.title = '添加用户'
  // 清空数据
  sysUser.value = ref({})
}

const showUpdateDialog = (row) => {
  dialog.visable = true
  dialog.title = '修改用户'
  // 回显
  console.log(row);
  sysUser.value = row
}

// 查询 ------------------------------------------------------------------------------------------
// 用户列表
const sysUserList = reactive({
  list: [], // 数据列表
  total: 0, // 总数据条目数
  pageNum: 1,
  pageSize: 10,
  queryDto: {
    keyword: '',
    createTimeBegin: '',
    createTimeEnd: '',
  },
})

// 监视pageNum和pageSize
watch([() => sysUserList.pageNum, () => sysUserList.pageSize], () => {
  fetchSysUserList()
})

onMounted(() => {
  fetchSysUserList()
})

const fetchSysUserList = async () => {
  const { data, code } = await FindSysUserPage(
    sysUserList.pageNum,
    sysUserList.pageSize,
    sysUserDto
  )
  if (code === 200) {
    sysUserList.list = data.list
    sysUserList.total = data.total
  } else {
    ElMessage.error(`请求用户数据失败，错误码${code}`)
  }
}

const reset = async () => {
  // 重置查询数据sysUserDto和createTimes
  sysUserDto.keyword = ''
  createTimes.value = []
  fetchSysUserList()
}
</script>

<style>
.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>