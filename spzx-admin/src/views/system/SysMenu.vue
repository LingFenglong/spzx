<template>
  <div class="search-div">
    <el-row>
      <el-col :span="6">
        <el-button type="success" size="small" @click="showAddMenu">添 加</el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-table :data="menuData" row-key="id" border default-expand-all height="540px" style="margin-top: 20px">
        <el-table-column prop="title" label="菜单标题" />
        <el-table-column prop="component" label="路由名称" />
        <el-table-column prop="sortValue" label="排序" />
        <el-table-column prop="status" label="状态" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" #default="scope">
          <el-row>
            <el-col :span="10">
              <el-button type="success" size="small">添加子菜单</el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" size="small" @click="updateMenu">修 改</el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="danger" size="small" @click="removeMenu(scope.row)">删 除</el-button>
            </el-col>
          </el-row>
        </el-table-column>
      </el-table>
    </el-row>
    
    <el-dialog
      title="添加菜单"
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
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue"
import { GetMenus, RemoveMenu } from '@/api/menu'
import { ElMessage } from "element-plus";

let menuData = ref([])

let menuDto = ref({
  parentId: 0,
  title: '',
  component: '',
  sortValue: '',
  status: 1
})

let addMenuDialogVisible = ref(false)

// 添加菜单
const showAddMenu = () => {
  addMenuDialogVisible.value = !addMenuDialogVisible.value
}

// 修改菜单


// 删除菜单
const removeMenu = async (row) => {
  const menuDto = {
    menuId: row.id
  }
  const { code } = await RemoveMenu(menuDto)
  if (code === 200) {
    ElMessage.success('删除菜单成功')
    fetchMenus()
  } else {
    ElMessage.error('删除菜单失败')
  }
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