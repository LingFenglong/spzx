<template>
  <div class="search-div">
    <!-- 搜索表单 -->
    <el-form label-width="70px" size="small">
      <el-row>
        <el-col :span="12">
          <el-form-item label="关键字">
            <el-input style="width: 100%" placeholder="用户名" v-model="sysUserDto.keyword"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="createTimes"
              type="daterange"
              range-separator="到"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row style="display: flex">
        <el-button type="primary" size="small">搜索</el-button>
        <el-button size="small">重置</el-button>
      </el-row>
    </el-form>

    <!-- 添加按钮 -->
    <div class="tools-div">
      <el-button type="success" size="small" @click="showAddRole">添 加</el-button>
    </div>

    <!--- 用户表格数据 -->
    <el-table :data="sysUserList.list" style="width: 100%">
      <el-table-column prop="userName" label="用户名" width="140" />
      <el-table-column prop="name" label="姓名" width="140" />
      <el-table-column prop="phone" label="手机" width="140" />
      <el-table-column prop="avatar" label="头像" #default="scope" width="140">
        <img :src="scope.row.avatar" width="50">
      </el-table-column>
      <el-table-column prop="description" label="描述" width="140" />
      <el-table-column prop="status" label="状态" #default="scope" width="140">
        {{ scope.row.status === 1 ? '正常' : '停用' }}
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" width="280" #default="scope">
        <el-button
          type="primary"
          size="small"
        >
          修改
        </el-button>

        <el-button
          type="warning"
          size="small"
        >
          分配角色
        </el-button>

        <el-button
          type="danger"
          size="small"
        >
          删除
        </el-button>
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
  </div>
</template>

<script setup>
  import { FindSysUserPage } from '@/api/SysUser'
  import { ElMessage } from 'element-plus'
  import { computed, onMounted, reactive, ref, toRefs, watch } from 'vue'

  const createTimes = ref([])

  const sysUserDto = reactive({
    keyword: '',
    createTimeBegin: computed(() => createTimes.value[0]),
    createTimeEnd: computed(() => createTimes.value[1])
  })

  // 用户列表
  const sysUserList = reactive({
    list: [], // 数据列表
    total: 0, // 总数据条目数
    pageNum: 1,
    pageSize: 10,
    queryDto: {
      keyword: '',
      createTimeBegin: '',
      createTimeEnd: ''
    }
  })

  // 监视pageNum和pageSize
  watch([() => sysUserList.pageNum, () => sysUserList.pageSize], () => {
    fetchSysUserList()
  })

  onMounted(() => {
    fetchSysUserList()
  })

  const fetchSysUserList = async () => {
    const { data, code } = await FindSysUserPage(sysUserList.pageNum, sysUserList.pageSize, sysUserDto)
    if (code === 200) {
      sysUserList.list = data.list
      sysUserList.total = data.total
    } else {
      ElMessage.error(`请求用户数据失败，错误码${code}`)
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