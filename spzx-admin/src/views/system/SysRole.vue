<template>
  <div class="search-div">
    <!-- 搜索表单 -->
    <el-form label-width="70px" size="small">
      <el-form-item label="角色名称">
        <el-input style="width: 100%" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-row style="display: flex">
        <el-button type="primary" size="small">搜索</el-button>
        <el-button size="small">重置</el-button>
      </el-row>
    </el-form>

    <!-- 添加按钮 -->
    <div class="tools-div">
      <el-button type="success" size="small">添 加</el-button>
    </div>

    <!--- 角色表格数据 -->
    <el-table :data="list" style="width: 100%">
      <el-table-column prop="roleName" label="角色名称" width="180" />
      <el-table-column prop="roleCode" label="角色code" width="180" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="280">
        <el-button type="primary" size="small">修改</el-button>
        <el-button type="danger" size="small">删除</el-button>
      </el-table-column>
    </el-table>

    <!--分页条-->
    <el-pagination
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      :total="total"
    />
  </div>
</template>

<script>
import { reactive, toRefs } from 'vue'
import { FindSysRolePage } from '@/api/sysRole'

export default {
  name: 'SysRole',
  setup() {
    onMounted(() => {
      fetchSysUserList()
    })

    // 查询 ----------------------------------------------------------------------------
    // 角色列表
    let sysRoleList = reactive({
      list: [], // 数据列表
      total: 0  // 总数据条目数
    })

    // 获取角色数据
    const fetchSysUserList = async () => {
      const { data, code } = await FindSysRolePage()
      if (code === 200) {
        sysRoleList.list = data.list
        sysRoleList.total = data.total
      }
    }


    return {
      ...toRefs(sysRoleList)
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