<template>
  <div class="search-div">
    <!-- 搜索表单 -->
    <el-form label-width="70px" size="small">
      <el-row>
        <el-col :span="5">
          <el-form-item label="角色名称">
            <el-input style="width: 100%" placeholder="角色名称" v-model="sysRoleList.queryDto.roleName" @keyup.enter="fetchSysRoleList"></el-input>
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
    <el-table :data="sysRoleList.list" style="width: 100%" height="515px">
      <el-table-column prop="roleName" label="角色名称" width="180" />
      <el-table-column prop="roleCode" label="角色code" width="180" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="280" #default="scope">
        <el-button type="primary" size="small" @click="showUpdateRole(scope.row)">修改</el-button>
        <el-button type="warning" size="small" @click="showAssignMenu(scope.row)">分配菜单</el-button>
        <el-button type="danger" size="small" @click="showRemoveRole(scope.row)">删除</el-button>
      </el-table-column>
    </el-table>

    <!--分页条-->
    <el-pagination
      v-model:current-page="sysRoleList.pageNum" 
      v-model:page-size="sysRoleList.pageSize"
      :page-sizes="[3, 5, 10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      :total="sysRoleList.total"
    />
  </div>

  <!-- 添加 -->
  <el-dialog v-model="isShow.isShowAdd" title="添加角色" align-center width="30%" @keyup.enter="addRole">
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
        <el-button @click="isShow.isShowAdd = false">取消</el-button>
        <el-button type="primary" @click="addRole">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 修改 -->
  <el-dialog v-model="isShow.isShowUpdate" title="修改角色" align-center width="30%" @keyup.enter="updateRole">
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
        <el-button @click="isShow.isShowUpdate = false">取消</el-button>
        <el-button type="primary" @click="updateRole">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 分配菜单 -->
  <!-- 分配菜单的对话框 tree组件添加ref属性，后期方便进行tree组件对象的获取 -->
  <el-dialog v-model="assignRoleDialogVisible" title="分配菜单" width="40%">
    <el-form label-width="80px">
      <el-tree
              :data="sysMenuTreeList.allMenu"
              ref="tree"
              show-checkbox
              default-expand-all
              :check-on-click-node="true"
              node-key="id"
              :props="{label: 'title', children: 'children'}"/>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="assignMenu">提交</el-button>
      <el-button @click="assignRoleDialogVisible = false">取消</el-button>
    </template>
  </el-dialog>
  
</template>

<script setup>
import { reactive, toRefs, onMounted, watch, ref, getCurrentInstance } from 'vue'
import { SaveSysRole, RemoveSysRole, UpdateSysRole, FindSysRolePage, AssignMenu } from '@/api/sysRole'
import { GetSysMenuTreeList } from '@/api/menu'
import { ElMessage } from 'element-plus'
import { ElMessageBox } from 'element-plus'

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

  let assignRoleDialogVisible = ref(false)

  let sysMenuTreeList = ref({})
  
  const tree = ref()

  const roleId = ref()

  // 展示分配菜单
  const showAssignMenu = async (row) => {
    assignRoleDialogVisible.value = true
    roleId.value = row.id
    const { data, code } = await GetSysMenuTreeList(row.id)
    if (code === 200) {
      tree.value.setCheckedKeys(data.assignedMenu)
      sysMenuTreeList.value = data
    } else {
      ElMessage.error("获取菜单数据失败")
    }
  }

  // 分配菜单
  const assignMenu = async () => {
    const assignedMenuDto = {
      roleId: roleId.value,
      menuIdList: [...tree.value.getCheckedNodes().map(node => ({ id: node.id, isHalf: 0 })),
                   ...tree.value.getHalfCheckedNodes().map(node => ({ id: node.id, isHalf: 1 }))],
    }
    console.log(assignedMenuDto);
    // 发送请求
    const { code } = await AssignMenu(assignedMenuDto);
    if (code === 200) {
      ElMessage.success('操作成功')
    } else {
      ElMessage.error('操作失败')
    }
    assignRoleDialogVisible.value = false

} 

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
      () => {

      }).catch(async (e) => {
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