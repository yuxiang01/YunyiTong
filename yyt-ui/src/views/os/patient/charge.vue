<script>
import {getOrder, recipePay} from "@/api/os/recipe";
import {calculateAge, commonDictFormat, getDecode} from "@/utils/web-utils";
import {getPatient} from "@/api/os/patient";
import {getRegorder, updateRegorder} from "@/api/os/regorder";

export default {
  name: "charge",
  dicts: [
    'sys_prescription_type', 'sys_usage_usage', 'sys_project_unit', 'sys_pay_method',
    'sys_drug_classification', 'sys_unit_unit', 'sys_project_classification',
    'sys_frequentness_frequentness', 'sys_china_drug', 'sys_user_sex', 'sys_reception_type'
  ],
  data() {
    return {
      preCode: null,
      obj: {},
      currentPre: '',
      preList: [],
      preform_0: [],
      preform_1: [],
      preform_2: [],
      preform_3: [],
      tableData: [],
      patient: {},
      regOrder: {},
      formData: {},
      open: false,
      rules: {
        money: [
          {required: true, message: "应收金额不能为空", trigger: "blur"}
        ],
        payMethod: [
          {required: true, message: "支付方式不能为空", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    this.preCode = getDecode(this.$route.params && this.$route.params.preCode)
    this.getInitData()
  },
  watch: {
    currentPre() {
      let key = `preform_${this.currentPre}`
      this.tableData = this[key]
    }
  },
  methods: {
    calculateAge,
    async getInitData() {
      let orderRes = await getOrder(this.preCode)
      this.obj = orderRes.data
      let arr = []
      for (let i = 0; i <= 3; i++) {
        let key = `preform_${i}`
        this[key] = this.obj.osPrescriptionList.filter(item => item.type === i)
        if (this[key].length > 0) arr.push(i + '')
      }
      this.preList = arr
      this.tableData = this[`preform_${arr[0]}`]
      this.currentPre = arr[0]
      let patientRes = await getPatient(this.obj.patientId)
      this.patient = patientRes.data
      let regRes = await getRegorder(this.obj.registerId)
      this.regOrder = regRes.data
      let other = {
        path: this.$route.path,
        title: `缴费【${this.patient.name}·${this.regOrder.type === '0' ? '初诊' : '复诊'}】`
      }
      this.$store.commit("tagsView/updatePatientViewTitle", other)
    },
    commonDictFormat,
    chargeEvent() {
      this.formData = {
        money: this.obj.money,
        payMethod: null
      }
      this.open = true
    },
    backEvent() {
      this.$tab.closePage();
      this.$router.back()
    },
    handleTagClick(tag) {
      this.currentPre = tag
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleConfirm() {
      this.$refs["elForm"].validate(valid => {
        if (valid) {
          this.obj.payMethod = this.formData.payMethod
          // TODO: 订单状态由后端修改，不要请求
          updateRegorder({regId: this.obj.registerId, status: '3'}).then(res => {
            if (res.code !== 200) {
              this.$modal.msgError('操作失败')
              return
            }
            recipePay(this.obj).then(({code}) => {
              if (code === 200) {
                this.open = false
                this.$modal.msgSuccess('缴费成功！')
              } else {
                this.$modal.msgError('操作失败')
              }
            })
          })
        }
      })
    }
  },
  computed: {
    getCurrentPreTotal() {
      let key = `preform_${this.currentPre}`
      if (this[key]) {
        if (key === 'preform_0' || key === 'preform_1') {
          return this[key].reduce((acc, item) => {
            return acc + (parseInt((item.total || '0')) * parseInt((item.unitPrice || '0')))
          }, 0)
        } else {
          return this[key].reduce((acc, item) => {
            return acc + (parseInt((item.dosage || '0')) * parseInt((item.unitPrice || '0')))
          }, 0)
        }
      }
      return 0
    }
  }
}
</script>

<template>
  <div class="charge">
    <div class="base_info flex-ver-center">
      <div class="left flex-ver-center">
        <p v-if="obj.payMethod === '' || obj.payMethod === null">
          应收金额：<span class="money">{{ obj.money }}</span> 元
        </p>
        <p v-else class="h_title">处方详情
          <el-tag type="success">已完成支付</el-tag>
        </p>
      </div>
      <div class="right">
        <el-button
          v-if="obj.payMethod === '' || obj.payMethod === null"
          @click="chargeEvent" type="warning"
          icon="el-icon-coin">收费
        </el-button>
        <el-button @click="backEvent" type="primary" plain icon="el-icon-back">返回</el-button>
      </div>
    </div>
    <div class="center-body">
      <div class="left">
        <h2 class="h_title">
          <i class="el-icon-s-order"/> 项目明细
        </h2>
        <div class="pre_list">
          <el-tag v-for="tag in preList"
                  :key="tag" effect="plain"
                  style="margin-right: 5px" type="info"
                  :class="tag === currentPre ? 'active' : ''"
                  @click="handleTagClick(tag)">
            {{ commonDictFormat('sys_prescription_type', tag) }}
          </el-tag>
        </div>
        <div class="pre_table">
          <el-table height="240" border v-show="currentPre < 2" :data="tableData" style="width: 100%">
            <el-table-column align="center" label="序号" width="50">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column align="center" label="频度" prop="frequency" width="125">
              <template #default="scope">
                {{ commonDictFormat('sys_frequentness_frequentness', scope.row.frequency) }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="单次用量" prop="dosage" width="165">
              <template #default="scope">
                1次{{ scope.row.dosage }}{{ commonDictFormat('sys_unit_unit', scope.row.unit) }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="用法" prop="usage" width="100">
              <template #default="scope">
                {{ commonDictFormat('sys_usage_usage', scope.row.usage) }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="总量" prop="total" width="100">
              <template #default="scope">
                {{ scope.row.total }}{{ commonDictFormat('sys_unit_unit', scope.row.totalUnit) }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice" width="85">
              <template slot-scope="scope">
                ￥{{ scope.row.unitPrice || '0.00' }}
              </template>
            </el-table-column>
          </el-table>
          <el-table height="240" v-show="currentPre === '2'" border :data="tableData" style="width: 100%">
            <el-table-column align="center" label="序号" width="60">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" show-overflow-tooltip/>
            <el-table-column align="center" label="部位" prop="part" width="150">
              <template #default="scope">{{ scope.row.part }}</template>
            </el-table-column>
            <el-table-column align="center" label="次数" prop="dosage">
              <template #default="scope">
                {{ scope.row.dosage }}{{ commonDictFormat('sys_project_unit', scope.row.unit) }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice" width="150">
              <template slot-scope="scope">
                ￥{{ scope.row.unitPrice || '0.00' }}
              </template>
            </el-table-column>
          </el-table>
          <el-table height="240" v-show="currentPre === '3'" border :data="tableData" style="width: 100%">
            <el-table-column align="center" label="序号" width="60">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" show-overflow-tooltip/>
            <el-table-column align="center" label="数量" prop="dosage" width="150">
              <template #default="scope">{{ scope.row.dosage }}</template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice">
              <template #default="scope">￥{{ scope.row.unitPrice || '0.00' }}</template>
            </el-table-column>
          </el-table>
        </div>
        <div class="footer">
          此方合计：
          <span style="font-size: 16px" class="blod_span">
          {{ getCurrentPreTotal.toFixed(2) }}
          </span> 元
        </div>
      </div>
      <div class="right">
        <div class="top">
          <h2 class="h_title"><i class="el-icon-user-solid"/> 人员信息</h2>
          <div class="table">
            <table border cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td>患者编号： {{ patient.patientId }}</td>
                <td>患者姓名： {{ patient.name }}</td>
              </tr>
              <tr>
                <td>年龄： {{ calculateAge(patient.card) }}</td>
                <td>性别：{{ commonDictFormat('sys_user_sex', patient.sex) }}</td>
              </tr>
              <tr>
                <td>手机：{{ patient.phone }}</td>
                <td>地址：{{ patient.address }}</td>
              </tr>
              <tr>
                <td colspan="2">详细地址：{{ patient.detailsAddress }}</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="bottom">
          <h2 class="h_title"><i class="el-icon-s-management"/> 接诊信息</h2>
          <div class="table">
            <table border cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td colspan="2">患者编号： {{ patient.patientId }}</td>
              </tr>
              <tr>
                <td colspan="2">门诊编号：{{ regOrder.regId }}</td>
              </tr>
              <tr>
                <td colspan="2">科室：{{ regOrder.deptName }}</td>
              </tr>
              <tr>
                <td colspan="2">接诊医生：{{ regOrder.doctor }}</td>
              </tr>
              <tr>
                <td colspan="2">接诊类型：{{ commonDictFormat('sys_reception_type', regOrder.type) }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="缴费" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="elForm" :rules="rules" :model="formData" size="medium" label-width="100px">
        <el-form-item label="应收金额" prop="money">
          <el-input v-model="formData.money" placeholder="请输入实收金额" readonly/>
        </el-form-item>
        <el-form-item label="支付方式" prop="payMethod">
          <el-radio-group v-model="formData.payMethod">
            <el-radio
              v-for="dict in dict.type.sys_pay_method"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定收款</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.charge {
  height: 100%;
  padding: 10px 20px;
  font-size: 14px;
  color: #999;
  background-color: #F2F2F2;
}

.base_info {
  padding: 10px 20px;
  background-color: #FEFEFE;
  border-radius: .4rem;
  justify-content: space-between;
  height: 70px;
  font-size: 16px;
  font-weight: 700;
  color: #000;
}

.money {
  padding: 0 10px;
  font-size: 30px;
  color: #FFC71C;
}

.center-body {
  display: flex;
  margin-top: 10px;
  background-color: #F2F2F2;
}

.center-body .left {
  flex: 60%;
  margin-right: 10px;
  padding: 10px 20px;
  border-radius: .4rem;
  color: #000;
  background-color: #FEFEFE;
}

.center-body .footer {
  padding: 10px 0;
  text-align: right;
}

.center-body .right {
  flex: 40%;
  padding: 10px 20px;
  border-radius: .4rem;
  background-color: #FEFEFE;
}

.pre_list {
  margin: 20px 0 10px 0;
}

.pre_list .el-tag {
  cursor: pointer;
}

.pre_list .el-tag:hover, .active {
  background-color: #e8f4ff;
  border-collapse: collapse;
  border-color: #d1e9ff;
  color: #1890ff;
}

.center-body .right table {
  padding: 5px;
  color: #000;
  border: 1px solid #000;
}

.right table td {
  padding-left: 10px;
  height: 40px;
}

.center-body .table {
  padding: 0 10px;
}

.center-body .top {
  margin-top: 10px;
}

.center-body .bottom {
  padding-bottom: 20px;
}
</style>
