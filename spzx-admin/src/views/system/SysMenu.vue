<template>
  <div class="search-div">
    <el-row>
      <el-col :span="6">
        <el-button type="success" size="small">添 加</el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-table :data="menuData" row-key="id" border default-expand-all height="540px" style="margin-top: 20px">
        <el-table-column prop="title" label="菜单标题" />
        <el-table-column prop="component" label="路由名称" />
        <el-table-column prop="sortValue" label="排序" />
        <el-table-column prop="status" label="状态" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作">
          <el-row>
            <el-col :span="10">
              <el-button type="success" size="small">添加子菜单</el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" size="small">修 改</el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="danger" size="small">删 除</el-button>
            </el-col>
          </el-row>
        </el-table-column>
      </el-table>
    </el-row>
    
  </div>
</template>

<script setup>
import { ref } from "vue"

const menuData = ref([
    {
      id: 1,
      title: '1',
      component: 'bbb',
      sortValue: '1',
      status: '1',
      createTime: 'l',
      children: [
        {
          id: 11,
          title: '11',
          component: 'bbb',
          sortValue: '1',
          status: '1',
          createTime: 'l',
          children: [
            {
              id: 111,
              title: '111',
              component: '6666666666666',
              sortValue: '1',
              status: '1',
              createTime: 'l'
            }
          ]
        },
        {
          id: 12,
          title: '12',
          component: 'bbb',
          sortValue: '1',
          status: '1',
          createTime: 'l'
        }
      ]
    },
    {
      id: 13,
      title: '13',
      component: 'bbb',
      sortValue: '1',
      status: '1',
      createTime: 'l'
    }
])

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