<template>
  <div class="search-div">
    <el-row>
      <el-col :span="6">
        <el-button type="success" size="small" @click="showAddTopMenu">添加顶级菜单</el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-table :data="menuData" row-key="id" border default-expand-all height="540px" style="margin-top: 20px">
        <el-table-column prop="title" label="菜单标题" width="200px" />
        <el-table-column prop="component" label="路由名称" width="200px" />
        <el-table-column prop="sortValue" label="排序" width="200px" />
        <el-table-column prop="status" label="状态" width="200px" />
        <el-table-column prop="createTime" label="创建时间" width="200px" />
        <el-table-column label="操作" #default="scope">
          <el-button type="success" size="small" @click="showAddMenu(scope.row)">添加子菜单</el-button>
          <el-button type="primary" size="small" @click="showUpdateMenu(scope.row)">修 改</el-button>
          <el-button type="danger" size="small" @click="removeMenu(scope.row)">删 除</el-button>
        </el-table-column>
      </el-table>
    </el-row>
    
    <!-- 对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="addMenuDialogVisible"
      width="30%"
       @keyup.enter="addMenu">
      <el-form :model="menuDto" label-width="120px">
        <el-form-item label="菜单标题">
          <el-input v-model="menuDto.title"></el-input>
        </el-form-item>
        <el-form-item label="路由名称">
          <el-input v-model="menuDto.component"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="menuDto.sortValue"></el-input>
        </el-form-item>
        <el-form-item label="状态">
            <el-radio-group v-model="menuDto.status">
                <el-radio :label="1">正常</el-radio>
                <el-radio :label="0">停用</el-radio>
            </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addMenuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMenu">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref, toRaw } from "vue"
import { GetMenus, RemoveMenu, AddMenu, UpdateMenu } from '@/api/menu'
import { ElMessage, ElMessageBox } from "element-plus";

let menuData = ref([])

let dialogTitle = ref('')

let menuDto = ref({
  menuId: '',
  parentId: 0,
  title: '',
  component: '',
  sortValue: '',
  status: 1
})

let addMenuDialogVisible = ref(false)

// 添加或修改菜单
const addMenu = async () => {
  if (menuDto.value.id) {
    // 修改菜单
    const { code } = await UpdateMenu(menuDto.value)
    if (code === 200) {
      ElMessage.success('修改菜单成功')
      fetchMenus()
    } else {
      ElMessage.error('修改菜单失败')
    }
  } else {
    // 添加菜单
    const { code } = await AddMenu(menuDto.value)
    if (code === 200) {
      ElMessage.success('添加菜单成功')
      fetchMenus()
    } else {
      ElMessage.error('添加菜单失败')
    }
  }
  addMenuDialogVisible.value = false
}

const showAddTopMenu = () => {
  dialogTitle.value = '添加顶级菜单'
  menuDto.value = {}
  menuDto.value.parentId = 0
  menuDto.value.status = 1
  addMenuDialogVisible.value = !addMenuDialogVisible.value
}

const showAddMenu = (row) => {
  dialogTitle.value = '添加子级菜单'
  menuDto.value = {}
  menuDto.value.parentId = row.id
  menuDto.value.status = 1
  addMenuDialogVisible.value = !addMenuDialogVisible.value
}

// 修改菜单
const showUpdateMenu = (row) => {
  dialogTitle.value = '修改菜单'
  // 回显数据
  menuDto.value = row
  addMenuDialogVisible.value = !addMenuDialogVisible.value
}

// 删除菜单
const removeMenu = async (row) => {
  const menuDto = {
    menuId: row.id
  }
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', `菜单：${row.title}`, {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const { code } = await RemoveMenu(menuDto)
    if (code === 200) {
      ElMessage.success('删除菜单成功')
      fetchMenus()
    } else {
      if (code === 217) {
        ElMessage.error('不能删除带有子节点的菜单')
      } else {
        ElMessage.error('删除菜单失败')
      }
    }
  },
  () => {
    
  }).catch((e) => {
    ElMessage.error('错误，请查看控制台信息')
    console.log(e);
  })
}


// 获取菜单
const fetchMenus = async () => {
  const { data, code } = await GetMenus();
  if (code === 200) {
    menuData.value = data
  } else {
    ElMessage.error('请求菜单列表失败')
  }
}

// 挂载请求
onMounted(() => {
  fetchMenus()
})

</script>

<style>
  .search-div {
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ebeef5;
    border-radius: 3px;
    background-color: #fff;
  }
</style>