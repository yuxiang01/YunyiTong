<script>
import PatientBaseInfo from "@/components/PatientInfo/index.vue";
import Prescription from "@/components/Prescription/index.vue";
import {getRegorder, updateRegorder} from "@/api/os/regorder";
import {getPatient} from "@/api/os/patient";
import {getDecode, getEncode} from "@/utils/web-utils";
import CaseHistory from "@/components/CaseHistory/index.vue";
import {addOrder, getOrderByRegId, recipeFinish, saveOrder, updateOrder} from "@/api/os/recipe";
import dayjs from "dayjs";

export default {
  name: "consultation",
  components: {CaseHistory, PatientBaseInfo, Prescription},
  dicts: ['sys_prescription_type', 'sys_drug_classification'],
  data() {
    return {
      registerId: null,
      regStatus: null,
      icon: false,
      labelPosition: 'left',
      patient: {
        name: undefined,
        sex: undefined,
        age: undefined,
        phone: undefined,
        card: undefined,
        address: undefined,
        detailsAddress: undefined,
        remark: undefined,
      },
      doctor: null,
      tableHight: 380,
      recipe: {}
    }
  },
  created() {
    this.registerId = getDecode(this.$route.params && this.$route.params.registerId);
    this.getInitData()
  },
  methods: {
    async getInitData() {
      let reg = await getRegorder(this.registerId)
      this.regStatus = reg.data.status
      this.doctor = `${reg.data.deptName}/${reg.data.doctor}`
      if (!this.recipe.hasOwnProperty('type')) this.recipe.type = reg.data.type
      getPatient(reg.data.patientId).then(({data}) => {
        this.patient = data
        if (!this.recipe.hasOwnProperty('patientId')) this.recipe.patientId = this.patient.patientId
        let other = {
          path: this.$route.path,
          title: `问诊【${this.patient.name}·${reg.data.type === '0' ? '初诊' : '复诊'}】`
        }
        this.$store.commit("tagsView/updatePatientViewTitle", other)
      })
      getOrderByRegId(this.registerId).then(({data}) => {
        if (data) {
          this.recipe = data
          return
        }
        if (!this.recipe.hasOwnProperty('registerId')) this.recipe.registerId = this.registerId
      })
    },
    handleShow() {
      this.icon = !this.icon
      this.tableHight = this.icon ? this.tableHight - 140 : this.tableHight + 140
    },
    savePreEvent(pre) {
      if (!this.updateRegStatus()) {
        this.$modal.msgError("操作失败~")
        return
      }
      // 处方单、处方详情一起添加
      this.recipe.osPrescriptionList = pre
      // 新增、修改处方
      if (!this.recipe.hasOwnProperty('preCode')) {
        addOrder(this.recipe).then(({code}) => {
          if (code === 200) {
            this.getInitData()
            this.$modal.msgSuccess("保存成功");
          }
        })
      } else {
        updateOrder(this.recipe).then(({code}) => {
          if (code === 200) {
            this.getInitData()
            this.$modal.msgSuccess("保存成功");
          }
        })
      }
    },
    saveCaseEvent(obj) {
      if (!this.updateRegStatus()) {
        this.$modal.msgError("操作失败~")
        return
      }
      Object.assign(this.recipe, obj)
      let {osPrescriptionList, ...data} = this.recipe
      saveOrder(data).then(({code}) => {
        if (code === 200) {
          this.getInitData()
          this.$modal.msgSuccess("保存成功");
        }
      })
    },
    // 结束问诊
    finishEvent() {
      let that = this
      this.$modal.confirm("确定要结束此次就诊吗？").then(() => {
        that.recipeMerge().then(({code}) => {
          if (code === 200) {
            this.$modal.msgSuccess("此次就诊已完成");
            this.$tab.closePage();
          }
        }).catch(err => that.$modal.msgWarning(err));
      })
    },
    // 处方合并提交
    recipeMerge() {
      return new Promise((resolve, reject) => {
        // 合并处方
        this.recipe.osPrescriptionList = this.$refs.prescriptionRef.mergeFn()
        Object.assign(this.recipe, this.$refs.caseHistoryRef.mergeFn())
        // 判断病历信息是否填写
        let $caseRef = this.$refs.caseHistoryRef
        $caseRef.$refs.historyForm.validate(valid => {
          if (!valid) {
            return reject("请填写完整的病历信息")
          } else {
            // 修改挂号订单状态
            if (!this.updateRegStatus('2')) {
              return reject("操作失败")
            } else {
              return resolve(recipeFinish(this.recipe))
            }
          }
        })
      })
    },
    // 缴费
    chargeEvent() {
      this.recipeMerge().then(({code}) => {
        if (code === 200) {
          this.$tab.closeOpenPage({path: `/os/charge/${getEncode(this.recipe.preCode)}`});
        }
      }).catch(err => this.$modal.msgWarning(err))
    },
    // 修改订单状态
    async updateRegStatus(status = '1') {
      if (this.regStatus != null) {
        let reg = {regId: this.registerId, status: status}
        if (status === '1') this.reg.receTime = dayjs().format("YYYY-MM-DD HH:mm:ss")
        let {code} = await updateRegorder(reg)
        return code === 200
      }
      return false
    }
  }
}
</script>

<template>
  <div class="consultation">
    <div class="base_info">
      <div class="top">
        <h1 class="h_title flex-center" @click="handleShow" style="cursor: pointer;">
          基本信息 <i :class="'el-icon-arrow-'+(icon ?'up':'down')"/>
        </h1>
        <el-radio-group v-model="labelPosition" size="small">
          <el-radio-button label="left">病历</el-radio-button>
          <el-radio-button label="right">处方</el-radio-button>
        </el-radio-group>
      </div>
      <div class="form" v-if="icon">
        <PatientBaseInfo :form="patient"/>
      </div>
    </div>
    <div class="center-body">
      <Prescription
        ref="prescriptionRef"
        @savePre="savePreEvent" :doctor="doctor"
        :pre-data-list="recipe.osPrescriptionList"
        v-show="labelPosition === 'right'"
        @finish="finishEvent" @charge="chargeEvent"
        :table-hight="tableHight"/>
      <CaseHistory
        ref="caseHistoryRef"
        :case-history-info="recipe.caseHistory"
        :physique-info="recipe.physique"
        @finish="finishEvent"
        @saveCase="saveCaseEvent" :icon="icon"
        v-show="labelPosition !== 'right'"/>
    </div>
  </div>
</template>

<style scoped>
.consultation {
  height: 100%;
  padding: 10px 20px;
  font-size: 14px;
  color: #999;
  background-color: #F2F2F2;
}

.base_info, .center-body {
  padding: 10px 20px;
  background-color: #FEFEFE;
  border-radius: .4rem;
}

.base_info .form {
  padding: 0 40px;
}

.base_info .top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.center-body {
  margin-top: 10px;
}

.el-icon-arrow-up {
  font-size: 12px;
}

h1 {
  font-size: 24px;
}
</style>
