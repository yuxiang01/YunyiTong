<script>
export default {
  name: "CaseHistory",
  props: {icon: Boolean, caseHistoryInfo: Object, physiqueInfo: Object},
  data() {
    return {
      isLight: false,
      formHeight: 200,
      physicalForm: {},
      historyForm: {},
      rules: {
        dateOfOnset: [
          {required: true, message: "症状出现日期不能为空", trigger: "blur"}
        ],
        activeInChief: [
          {required: true, message: "主诉不能为空", trigger: "blur"}
        ],
        diagnosis: [
          {required: true, message: "诊断结果不能为空", trigger: "blur"}
        ],
      }
    }
  },
  methods: {
    save() {
      let obj = this.mergeFn()
      this.$emit('saveCase', obj)
    },
    finish() {
      this.$emit('finish')
    },
    mergeFn() {
      return {physique: this.physicalForm, caseHistory: this.historyForm}
    }
  },
  watch: {
    caseHistoryInfo(newVal) {
      this.historyForm = {...newVal}
    },
    physiqueInfo(newVal) {
      this.physicalForm = {...newVal}
    }
  }
}
</script>

<template>
  <div class="case_history">
    <h3 class="h_title">体格信息</h3>
    <div class="physical_info">
      <el-form ref="physicalForm" :model="physicalForm" label-width="70px">
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="体温" prop="temp">
            <el-input v-model="physicalForm.temp" placeholder="请输入体温"/>
            ℃
          </el-form-item>
          <el-form-item label="呼吸" prop="rate">
            <el-input v-model="physicalForm.rate" placeholder="请输入呼吸频率"/>
            次/分
          </el-form-item>
          <el-form-item label="脉搏" prop="pulse">
            <el-input v-model="physicalForm.pulse" placeholder="请输入脉搏"/>
            次/分
          </el-form-item>
          <el-form-item label="血压" prop="systolicBloodPressure">
            <div class="flex-ver-center">
              <div :class="'diy_input flex-center ' + (isLight ? 'diy_input_focus' : '')">
                <input @focus="isLight = true" @blur="isLight = false" v-model="physicalForm.systolicBloodPressure"/> /
                <input @focus="isLight = true" @blur="isLight = false" v-model="physicalForm.diastolicBloodPressure"/>
              </div>
              mmHg
            </div>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="身高" prop="height">
            <el-input v-model="physicalForm.height" placeholder="请输入身高"/>
            cm
          </el-form-item>
          <el-form-item label="体重" prop="weight">
            <el-input v-model="physicalForm.weight" placeholder="请输入体重"/>
            kg
          </el-form-item>
          <el-form-item label="血糖" prop="bloodSugar">
            <el-input v-model="physicalForm.bloodSugar" placeholder="请输入血糖"/>
            mmol/l
          </el-form-item>
          <el-form-item label="血脂" prop="bloodLipid">
            <el-input v-model="physicalForm.bloodLipid" placeholder="请输入血脂"/>
            mmol/l
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <h3 class="h_title">病历信息</h3>
    <div class="history_info" :style="{height: (icon ? formHeight : formHeight + 170)+'px'}">
      <el-form ref="historyForm" :rules="rules" :model="historyForm" label-width="150px" label-position="top">
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="症状出现日期" prop="dateOfOnset">
            <el-date-picker
              clearable style="width: 100%;" v-model="historyForm.dateOfOnset"
              type="date" value-format="yyyy-MM-dd"
              placeholder="请选择症状出现日期"/>
          </el-form-item>
          <el-form-item label="主诉" prop="activeInChief">
            <el-input type="textarea" v-model="historyForm.activeInChief" placeholder="请输入主诉"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="现病史" prop="hpi">
            <el-input type="textarea" v-model="historyForm.hpi" placeholder="请输入现病史"/>
          </el-form-item>
          <el-form-item label="既往史" prop="pastHistory">
            <el-input type="textarea" v-model="historyForm.pastHistory" placeholder="请输入既往史"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="过敏史" prop="allergy">
            <el-input type="textarea" v-model="historyForm.allergy" placeholder="请输入过敏史"/>
          </el-form-item>
          <el-form-item label="家族史" prop="familyHistory">
            <el-input type="textarea" v-model="historyForm.familyHistory" placeholder="请输入家族史"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="诊断" prop="diagnosis">
            <el-input v-model="historyForm.diagnosis" placeholder="请输入内容"/>
          </el-form-item>
          <el-form-item label="医嘱" prop="idea">
            <el-input v-model="historyForm.idea" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
    <div class="footer flex-ver-center">
      <el-button @click="save" type="primary" icon="el-icon-box">保存</el-button>
      <el-button @click="finish" type="warning" icon="el-icon-switch-button">结束问诊</el-button>
    </div>
  </div>
</template>

<style scoped>
.case_history > h3 {
  font-weight: 400;
}

.physical_info {
  color: #000;
}

.physical_info /deep/ .el-input {
  width: 180px;
}

.physical_info /deep/ .el-form-item {
  flex: 1;
  margin-bottom: 10px;
}

.diy_input {
  margin-right: 5px;
  width: 180px;
  height: 36px;
  line-height: 36px;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
}

.diy_input > input {
  width: 86px;
  height: 34px;
  text-align: center;
  outline: none;
  border: none;
}

.diy_input_focus {
  outline: none;
  border-color: #1890ff;
}

.history_info {
  padding: 0 40px;
  overflow-y: auto;
  overflow-x: hidden;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.history_info::-webkit-scrollbar {
  display: none;
}

.history_info /deep/ .el-form-item {
  flex: 1;
  margin-right: 60px;
  margin-bottom: 6px;
}

.history_info /deep/ .el-form--label-top .el-form-item__label {
  padding: 0;
}

.footer {
  margin-top: 10px;
  justify-content: flex-end;
}
</style>
