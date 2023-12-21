<script>
import PatientBaseInfo from "@/components/PatientInfo/index.vue";
import Prescription from "@/components/Prescription/index.vue";
import {getRegorder} from "@/api/os/regorder";
import {getPatient} from "@/api/os/patient";
import {getDecode} from "@/utils/web-utils";

export default {
  name: "consultation",
  components: {PatientBaseInfo, Prescription},
  dicts: ['sys_prescription_type', 'sys_drug_classification'],
  data() {
    return {
      registerId: null,
      icon: false,
      labelPosition: 'right',
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
      doctorId: null,
      tableHight: 380
    }
  },
  created() {
    this.registerId = getDecode(this.$route.params && this.$route.params.registerId);
    getRegorder(this.registerId).then(({data}) => {
      this.doctorId = data.doctorId
    })
    this.getList()
  },
  methods: {
    async getList() {
      let {data} = await getRegorder(this.registerId)
      getPatient(data.patientId).then(({data}) => {
        this.patient = data
        let other = {path: this.$route.path, title: `问诊【${this.patient.name}】`}
        this.$store.commit("tagsView/updatePatientViewTitle", other)
      })
    },
    handleShow() {
      this.icon = !this.icon
      this.tableHight = this.icon ? this.tableHight - 140 : this.tableHight + 140
    }
  }
}
</script>

<template>
  <div class="consultation">
    <div class="base_info">
      <div class="top">
        <h1 class="flex-center" @click="handleShow">
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
      <Prescription :doctor-id="doctorId" :table-hight="tableHight"/>
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

h1 {
  margin: 10px;
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 24px;
  color: rgb(102, 110, 232);
  cursor: pointer;
}

.center-body {
  margin-top: 10px;
}

.el-icon-arrow-up {
  font-size: 12px;
}
</style>
