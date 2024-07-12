<template>
  <el-container style="width: 100%; height: 100%">
    <el-col>
      <el-row>
        <h1 style="margin-left: 18px">订单数据统计</h1>
        <el-date-picker
          style="margin-left: 100px; max-width: 220px"
          v-model="datePicker"
          placeholder="选择日期范围"
          type="daterange"
          value-format="YYYY-MM-DD"
          :clearable="false"
          @change="fetchOrderStatisticsData"
        ></el-date-picker>
        <el-button style="margin-left: 8px" type="primary" @click="reset">重置</el-button>
      </el-row>
      <el-row style="width: 100%; height: 100%">
        <div ref="orderStatistics" class="orderStatistics"></div>
      </el-row>
    </el-col>
  </el-container>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, onMounted, ref } from 'vue'
import { GetOrderStatisticsData } from '@/api/order'
import dayjs from 'dayjs'

const defaultStartDate = dayjs()
  .startOf('month')
  .format('YYYY-MM-DD')

const defaultEndDate = dayjs()
  .endOf('month')
  .format('YYYY-MM-DD')

const datePicker = ref([defaultStartDate, defaultEndDate])
const orderStatisticsDto = ref({
  createTimeBegin: computed(() => datePicker.value[0]),
  createTimeEnd: computed(() => datePicker.value[1]),
})
const orderStatistics = ref({})
const orderStatisticsOpts = ref({
  color: ['#3398DB'],
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      // 坐标轴指示器，坐标轴触发有效
      type: 'shadow', // 默认为直线，可选为：'line' | 'shadow'
    },
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true,
  },
  xAxis: [
    {
      type: 'category',
      data: [],
      axisTick: {
        alignWithLabel: true,
      },
    },
  ],
  yAxis: [
    {
      type: 'value',
    },
  ],
  series: [
    {
      type: 'bar',
      barWidth: '60%',
      data: [],
    },
  ],
})

const reset = () => {
  datePicker.value = [defaultStartDate, defaultEndDate]
  fetchOrderStatisticsData()
}

const fetchOrderStatisticsData = async () => {
  const { code, data, message } = await GetOrderStatisticsData(
    orderStatisticsDto.value
  )
  if (code === 200) {
    orderStatisticsOpts.value.xAxis[0].data = data.dateList
    orderStatisticsOpts.value.series[0].data = data.amountList
    const orderStatisticsChart = echarts.init(orderStatistics.value)
    orderStatisticsChart.setOption(orderStatisticsOpts.value)
  }
}

onMounted(() => {
  fetchOrderStatisticsData()
})
</script>

<style scoped>
.orderStatistics {
  width: 100%;
  height: 100%;
}
</style>
