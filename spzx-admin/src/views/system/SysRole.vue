<template>
  <div class="search-div">
    <!-- 搜索表单 -->
    <el-form label-width="70px" size="small">
      <el-row>
        <el-col :span="5">
          <el-form-item label="角色名称">
            <el-input style="width: 100%" placeholder="角色名称" v-model="queryDto.roleName" @keyup.enter="fetchSysRoleList"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" size="small" @click="fetchSysRoleList" style="margin-left: 12px">搜索</el-button>
          <el-button size="small" @click="reset">重置</el-button>
          <el-button type="success" size="small" @click="showAddRole" >添 加</el-button>
        </el-col>
      </el-row>
    </el-form>

    <!--- 角色表格数据 -->
    <el-table :data="list" style="width: 100%" height="515px">
      <el-table-column prop="roleName" label="角色名称" width="180" />
      <el-table-column prop="roleCode" label="角色code" width="180" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="280" #default="scope">
        <el-button type="primary" size="small" @click="showUpdateRole(scope.row)">修改</el-button>
        <el-button type="danger" size="small" @click="showRemoveRole(scope.row)">删除</el-button>
      </el-table-column>
    </el-table>

    <!--分页条-->
    <el-pagination
      v-model:current-page="pageNum" 
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      :total="total"
    />
  </div>

  <!-- 添加 -->
  <el-dialog v-model="isShowAdd" title="添加角色" align-center width="30%" @keyup.enter="addRole">
    <el-form :model="roleForm">
      <el-form-item label="角色名称：">
        <el-input v-model="roleForm.roleName" autocomplete="off" />
      </el-form-item>
      <el-form-item label="角色编码：">
        <el-input v-model="roleForm.roleCode" autocomplete="off" />
      </el-form-item>
      <el-form-item label="角色描述：">
        <el-input v-model="roleForm.description" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="isShowAdd = false">取消</el-button>
        <el-button type="primary" @click="addRole">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 修改 -->
    <el-dialog v-model="isShowUpdate" title="修改角色" align-center width="30%" @keyup.enter="updateRole">
    <el-form :model="roleForm">
      <el-form-item label="角色名称：">
        <el-input v-model="roleForm.roleName" autocomplete="off" />
      </el-form-item>
      <el-form-item label="角色编码：">
        <el-input v-model="roleForm.roleCode" autocomplete="off" />
      </el-form-item>
      <el-form-item label="角色描述：">
        <el-input v-model="roleForm.description" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="isShowUpdate = false">取消</el-button>
        <el-button type="primary" @click="updateRole">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, toRefs, onMounted, watch } from 'vue'
import { SaveSysRole, RemoveSysRole, UpdateSysRole, FindSysRolePage } from '@/api/sysRole'
import { ElMessage } from 'element-plus'
import { ElMessageBox } from 'element-plus'

export default {
  name: 'SysRole',
  setup() {
    onMounted(() => {
      fetchSysRoleList()
    })

    // 是否显示弹窗
    const isShow = reactive({
      isShowAdd: false,
      isShowUpdate: false
    })

    // 角色表单
    let roleForm = reactive({
      id: '',
      roleName: '',
      roleCode: '',
      description: ''
    })

    // 删除 ----------------------------------------------------------------------------
    const showRemoveRole = (row) => {
      ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', `角色：${row.roleName}`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(
        async () => {
          const { code } = await RemoveSysRole(row.id)
          if(code === 200) {
                ElMessage.success('删除角色成功')
                fetchSysRoleList()
          } else {
            ElMessage.error('删除角色失败')
          }
        },
        () => {}
      ).catch(async (e) => {
        ElMessage.error('错误，请查看控制台信息')
        console.log(e);
      })
    }

    // 修改 ----------------------------------------------------------------------------
    const showUpdateRole = (row) => {
      isShow.isShowUpdate = true
      // 回显数据
      roleForm.id = row.id
      roleForm.roleName = row.roleName
      roleForm.roleCode = row.roleCode
      roleForm.description = row.description
    }

    const updateRole = async () => {
      isShow.isShowUpdate = false
      const { code } = await UpdateSysRole(roleForm)  
      if (code === 200) {
        ElMessage.success('修改角色成功')
        fetchSysRoleList()
      } else {
        ElMessage.error(`修改角色失败`)
      }
    }

    // 添加 ----------------------------------------------------------------------------

    const showAddRole = () => {
      isShow.isShowAdd = true
      // 清空数据
      roleForm.id = ''
      roleForm.roleName = ''
      roleForm.roleCode = ''
      roleForm.description = ''
    }

    const addRole = async () => {
      isShow.isShowAdd = false
      // 添加角色的请求
      const { code } = await SaveSysRole(roleForm)
      if (code === 200) {
        fetchSysRoleList()
        ElMessage.success(`添加角色成功`)
      } else {
        ElMessage.error(`添加角色失败`)
      }
    }

    // 查询 ----------------------------------------------------------------------------
    // 角色列表
    let sysRoleList = reactive({
      list: [], // 数据列表
      total: 0, // 总数据条目数
      pageNum: 1,
      pageSize: 10,
      queryDto: {
        roleName: ''
      }
    })

    // 监视pageNum和pageSize
    watch([() => sysRoleList.pageNum, () => sysRoleList.pageSize], () => {
      fetchSysRoleList()
    })

    // 获取角色数据
    const fetchSysRoleList = async () => {
      const { data, code } = await FindSysRolePage(sysRoleList.pageNum, sysRoleList.pageSize, sysRoleList.queryDto)
      if (code === 200) {
        sysRoleList.list = data.list
        sysRoleList.total = data.total
      } else {
        ElMessage.error(`请求角色数据失败，错误码${code}`)
      }
    }

    const reset = async () => {
      sysRoleList.queryDto.roleName = ''
      fetchSysRoleList();
    }


    return {
      ...toRefs(sysRoleList),
      ...toRefs(isShow),
      fetchSysRoleList,
      reset,

      // 添加角色
      roleForm,
      showAddRole,
      addRole,

      // 修改角色
      showUpdateRole,
      updateRole,

      // 删除角色
      showRemoveRole,
      
    }
  }
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
</style>