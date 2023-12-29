<script>
import {commonDictFormat, getEncode} from "@/utils/web-utils";

export default {
  name: "caseOnline",
  props: {tableData: Array},
  dicts: [
    'sys_reception_type', 'sys_usage_usage', 'sys_unit_unit',
    'sys_frequentness_frequentness','sys_visit_status'
  ],
  data() {
    return {
      labelPosition: 'right',
      currentPreCode: '',
      pre: {},
      preForm_0: [],
      preForm_1: [],
    }
  },
  watch: {
    labelPosition(newVal) {
      this.$emit('switch', newVal)
    },
    tableData: {
      handler() {
        if (this.currentPreCode === '') {
          this.currentPreCode = this.tableData[0].preCode
        }
      },
      deep: true,
      immediate: true
    },
    currentPreCode: {
      handler(newVal) {
        let obj = this.tableData.filter(i => i.preCode === newVal)[0]
        this.pre = {...obj}
        this.preForm_0 = obj.osPrescriptionList.filter(i => i.type === 0)
        this.preForm_1 = obj.osPrescriptionList.filter(i => i.type === 1)
      },
      immediate: true
    }
  },
  methods: {
    commonDictFormat,
    clickEvent(code) {
      this.currentPreCode = code
    },
    editEvent() {
      this.$tab.closeOpenPage({path: `/os/consultation/${getEncode(this.pre.registerId)}`});
    }
  }
}
</script>

<template>
  <div class="case_online">
    <div class="body_center">
      <div class="top">
        <h1>电子病历</h1>
        <el-radio-group v-model="labelPosition" size="small">
          <el-radio-button label="left">基本信息</el-radio-button>
          <el-radio-button label="right">电子病历</el-radio-button>
          <el-radio-button label="top">缴费记录</el-radio-button>
        </el-radio-group>
      </div>
      <div class="center flex-ver-center">
        <div class="left">
          <div class="flex_sides" style="align-items: center;height: 36px;">
            <div class="header_left">
              <span :class="'tag tag-'+pre.type">
              {{ commonDictFormat('sys_reception_type', pre.type) }}
            </span>
            </div>
            <el-button v-if="pre.regorder.status < 3" @click="editEvent" plain type="primary" icon="el-icon-edit">编辑病历</el-button>
          </div>
          <h3>体格信息</h3>
          <table>
            <tr>
              <td>体温：{{ pre.physique.temp }} ℃</td>
              <td>呼吸：{{ pre.physique.rate }} 次/分</td>
              <td>脉搏: {{ pre.physique.pulse }} 次/分</td>
              <td>
                血压: {{ pre.physique.systolicBloodPressure }}
                ~{{ pre.physique.diastolicBloodPressure }}mmgh
              </td>
            </tr>
            <tr>
              <td>身高: {{ pre.physique.height }} cm</td>
              <td>体重: {{ pre.physique.weight }} kg</td>
              <td>血糖: {{ pre.physique.bloodSugar }} mmol/l</td>
              <td>血脂: {{ pre.physique.bloodLipid }} mmol/l</td>
            </tr>
          </table>
          <h3>病历信息</h3>
          <table>
            <tr>
              <td colspan="2">
                <span class="blod_span">症状出现日期: </span> {{ pre.caseHistory.dateOfOnset }}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <span class="blod_span">主诉: </span> {{ pre.caseHistory.activeInChief }}
              </td>
            </tr>
            <tr>
              <td>现病史：{{ pre.caseHistory.hpi }}</td>
              <td>既往史：{{ pre.caseHistory.pastHistory }}</td>
            </tr>
            <tr>
              <td>过敏史：{{ pre.caseHistory.allergy }}</td>
              <td>家庭史：{{ pre.caseHistory.familyHistory }}</td>
            </tr>
            <tr>
              <td colspan="2">初步诊断：{{ pre.caseHistory.diagnosis }}</td>
            </tr>
            <tr>
              <td colspan="2">医嘱：{{ pre.caseHistory.idea }}</td>
            </tr>
          </table>
          <div class="preInfo" v-if="preForm_0.length > 0 || preForm_1.length > 0">
            <h3>处方信息</h3>
            <div class="preForm_0" v-if="preForm_0.length > 0">
              <p class="blod_span">西药处方</p>
              <p v-for="(item,index) in preForm_0">
                <b>{{ index + 1 }} {{ item.name }}</b>
                用法：{{ commonDictFormat('sys_usage_usage', item.usage) }}
                {{ commonDictFormat('sys_frequentness_frequentness', item.frequency) }}
                1次{{ item.dosage }}{{ commonDictFormat('sys_unit_unit', item.unit) }}
                共 {{ item.total }}{{ commonDictFormat('sys_unit_unit', item.totalUnit) }}
              </p>
            </div>
            <div class="preForm_1" v-if="preForm_1.length > 0">
              <p class="blod_span">中药处方</p>
              <p v-for="(item,index) in preForm_1">
                <b>{{ index + 1 }} {{ item.name }}</b>
                用法：{{ commonDictFormat('sys_usage_usage', item.usage) }}
                {{ commonDictFormat('sys_frequentness_frequentness', item.frequency) }}
                1次{{ item.dosage }}{{ commonDictFormat('sys_unit_unit', item.unit) }}
                共 {{ item.total }}{{ commonDictFormat('sys_unit_unit', item.totalUnit) }}
              </p>
            </div>
          </div>
        </div>
        <div class="right">
          <el-timeline>
            <el-timeline-item
              v-for="item in tableData" :key="item.preCode" placement="top"
              :class="currentPreCode === item.preCode ? 'action' : ''"
              :timestamp="item.createTime">
              <div class="cart" @click="clickEvent(item.preCode)">
                <p class="flex_sides">
                  <span>门诊编号: {{ item.preCode }}</span>
                  <dict-tag :options="dict.type.sys_visit_status" :value="item.regorder.status"/>
                </p>
                <p>诊断：<span class="blod_span">{{ item.caseHistory.diagnosis }}</span></p>
                <p class="flex_sides">
                  <span>接诊科室：{{ item.regorder.deptName }}</span>
                  <span> 接诊医生：{{ item.regorder.doctor }}</span>
                </p>
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.case_online {
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

.top h1 {
  margin: 10px;
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 24px;
  color: rgb(102, 110, 232);
}

.tag {
  padding: 5px 20px;
  font-size: 14px;
  font-weight: 700;
}

.tag-0 {
  background-color: rgba(40, 208, 148, 1);
}

.tag-1 {
  background-color: #FFC71C;
}

.center .left,
.center .right {
  height: 600px;
  overflow-y: auto;
  overflow-x: hidden;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.center .left::-webkit-scrollbar,
.center .right::-webkit-scrollbar {
  display: none;
}

.center .left {
  flex: 60%;
  margin: 10px;
  padding: 10px 20px;
  color: #000;
  border-radius: 4px;
  box-shadow: 0 0 6px rgba(102, 110, 232, 0.4);
}

.center .left h3 {
  font-weight: 700;
  margin: 10px 0;
}

.center .left table {
  width: 100%;
}

.center .left table td {
  padding: 6px 0;
}

.center .right {
  padding-top: 10px;
  width: 0;
  flex: 40%;
}

/deep/ .el-timeline {
  padding-left: 20px;
}

/deep/ .el-timeline-item__node {
  background-color: #fff;
  border: 2px solid #ccc;
}

/deep/ .el-timeline-item__timestamp {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 36px;
  font-weight: bold;
  color: #666EE8;
  border-radius: 4px;
  border: 1px solid #666EE8FF;
}

/deep/ .el-timeline-item__timestamp.is-top {
  padding: 0;
}

.cart {
  width: 415px;
  cursor: pointer;
  border: 1px solid #e6ebf5;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.cart p {
  padding: 0 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.action /deep/ .el-timeline-item__node {
  background-color: #666EE8FF;
  border: 2px solid #666EE8FF;
}

.action /deep/ .el-timeline-item__timestamp {
  color: #FFFFFF;
  background-color: #666EE8FF;
}

.blod_span {
  color: #666EE8;
  font-weight: 400;
}
</style>
