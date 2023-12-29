<script>
import {commonDictFormat, getDecode, getEncode} from "@/utils/web-utils";
import {listOrder} from "@/api/os/recipe";

export default {
  name: "records",
  dicts: ['sys_pay_method'],
  props: {tableData: Array},
  data() {
    return {
      labelPosition: 'top',
      totalPrice: 0
    }
  },
  watch: {
    labelPosition(newVal) {
      this.$emit('switch', newVal)
    },
    tableData: {
      handler() {
        if (this.tableData) {
          this.totalPrice = this.tableData.reduce((acc, item) => {
            return acc + parseInt(item.money || '0')
          }, 0)
        }
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    commonDictFormat,
    chargeEvent(preCode) {
      this.$tab.closeOpenPage({path: `/os/charge/${getEncode(preCode)}`});
    }
  }
}
</script>

<template>
  <div class="records">
    <div class="body_center">
      <div class="top">
        <h1>缴费记录</h1>
        <el-radio-group v-model="labelPosition" size="small">
          <el-radio-button label="left">基本信息</el-radio-button>
          <el-radio-button label="right">电子病历</el-radio-button>
          <el-radio-button label="top">缴费记录</el-radio-button>
        </el-radio-group>
      </div>
      <div class="total">
        消费总计金额：<span class="blod_span">{{ totalPrice.toFixed(2) }}</span> 元
      </div>
      <div class="table">
        <h1>消费明细</h1>
        <el-table :data="tableData" height="260" style="width: 100%;margin: 20px">
          <el-table-column label="订单创建时间" align="center" prop="createTime"/>
          <el-table-column label="缴费状态" align="center">
            <template #default="scope">
              <span class="blod_span" v-if="scope.row.payMethod === null">未缴费</span>
              <span class="blod_span" style="color: green;" v-else>已缴费</span>
            </template>
          </el-table-column>
          <el-table-column label="应收金额" align="center" prop="money"/>
          <el-table-column label="支付方式" align="center" prop="payMethod">
            <template #default="scope">
              {{ commonDictFormat('sys_pay_method', scope.row.payMethod) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                type="text" icon="el-icon-view"
                @click="chargeEvent(scope.row.preCode)"
                v-if="scope.row.payMethod !== null"
              >查看详情
              </el-button>
              <el-button
                type="text" icon="el-icon-money"
                @click="chargeEvent(scope.row.preCode)" v-else
              >缴费
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.records {
  height: 100%;
  padding: 10px 20px;
  font-size: 14px;
  color: #999;
  background-color: #F2F2F2;
}

.body_center {
  padding: 10px 20px;
  background-color: #FEFEFE;
  border-radius: .4rem;
}

.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h1 {
  margin: 10px;
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 24px;
  color: rgb(102, 110, 232);
}

.total {
  margin: 10px 20px;
  padding: 15px 10px;
  background-color: #EEEFFB;
  border-radius: 4px;
  color: #000;
  font-size: 16px;
  font-weight: 700;
}

.table {
  margin-top: 20px;
}

.table .blod_span {
  font-weight: 400;
}
</style>
