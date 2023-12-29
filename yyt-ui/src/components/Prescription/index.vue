<script>
import {listDrug} from "@/api/os/drug";
import {listDoctor} from "@/api/system/doctor";
import {commonDictFormat, doctorsToMap} from "@/utils/web-utils";
import {listCheckItem} from "@/api/os/checkItem";
import {listExpense} from "@/api/os/expense";

export default {
  name: "prescriptionForm",
  dicts: [
    'sys_prescription_type', 'sys_usage_usage', 'sys_project_unit',
    'sys_drug_classification', 'sys_unit_unit', 'sys_project_classification',
    'sys_frequentness_frequentness', 'sys_china_drug'
  ],
  props: {
    doctor: String,
    tableHight: Number,
    preDataList: Array
  },
  data() {
    return {
      loading: true,
      ids: [],
      pre: '',
      preList: ['2'],
      currentPre: '2',
      itemList: [],
      preform_0: [],
      preform_1: [],
      preform_2: [],
      // 附加费用
      preform_3: [],
      drugForm: {
        name: null,
        drugType: null,
        preType: '2'
      },
      checkForm: {
        name: null,
        type: null
      },
      expenseForm: {
        name: null
      },
      cascadeList: [],
      form: {},
      tableData: [],
      currentPreTotal: "0.00",
      allPreTotal: "0.00",
    }
  },
  watch: {
    pre: {
      handler(newVal, oldVal) {
        if (newVal !== '') {
          // 不存在：添加处方单
          if (!this.preList.includes(newVal)) {
            this.preList.push(newVal)
          }
          this.currentPre = newVal
          this.pre = ''
          this.extracted()
        }
      }
    },
    drugForm: {
      handler() {
        this.getInitData()
      },
      deep: true
    },
    checkForm: {
      handler() {
        this.getInitData()
      },
      deep: true
    },
    expenseForm: {
      handler() {
        this.getInitData()
      },
      deep: true
    },
    tableData: {
      handler() {
        this.currentPreTotal = this.getCurrentPreTotal().toFixed(2)
        this.getAllPreTotal()
      },
      deep: true
    },
    preDataList: {
      // deep: true,
      handler(newVal) {
        if (newVal.length <= 0) return
        let preList = []
        for (let i = 0; i <= 3; i++) {
          let key = `preform_${i}`
          this[key] = newVal.filter(item => item.type === i)
          if (this[key].length > 0) preList.push(i + '')
        }
        this.preList = preList
        this.currentPre = preList[0]
        this.extracted()
      },
      // 这里增加了一个immediate属性，说明监听到props传参后立即先去执行handler方法
      //immediate: true,
    }
  },
  created() {
    this.getInitData()
    listDoctor().then(({rows}) => {
      this.cascadeList = doctorsToMap(rows)
    })
  },
  methods: {
    commonDictFormat,
    getInitData() {
      if (this.currentPre === '3') {
        this.getExtraChargeList()
        return
      }
      this.currentPre !== '2' ? this.getList() : this.getCheckItemList()
    },
    getList() {
      this.loading = true;
      listDrug(this.drugForm).then(response => {
        this.itemList = response.rows;
        this.loading = false;
      });
    },
    getCheckItemList() {
      this.loading = true
      listCheckItem(this.checkForm).then(res => {
        this.itemList = res.rows
        this.loading = false
      })
    },
    getExtraChargeList() {
      this.loading = true
      listExpense(this.expenseForm).then(res => {
        this.itemList = res.rows
        this.loading = false
      })
    },
    handleClose(tag) {
      let that = this
      this.$modal.confirm(`确认作废【${that.preTypeFormat(tag)}】?`).then(function () {
        that.preList = that.preList.filter(i => i !== tag)
        if (that.preList.length === 0) that.preList.push('0')
        that.currentPre = that.preList[that.preList.length - 1]
      })
        .then(() => {
          that[`preform_${tag}`] = []
          that.extracted()
          this.$modal.msgSuccess("删除成功")
        })
        .catch(err => console.log(err));
    },
    preTypeFormat(row) {
      return this.commonDictFormat('sys_prescription_type', row);
    },
    // 重新赋值，表格显示的数据
    extracted() {
      this.drugForm.drugType = null
      this.drugForm.preType = this.currentPre
      let key = `preform_${this.currentPre}`
      this.tableData = this[key]
    },
    handleTagClick(tag) {
      this.currentPre = tag
      this.extracted();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection
    },
    addItem() {
      if (this.ids.length === 0) {
        this.$modal.msgWarning("请至少选择一项");
        return
      }
      let key = `preform_${this.currentPre}`
      this.ids.forEach(i => {
        let obj = this.assignmentRecipe(i)
        console.log(obj);
        obj.index = this[key][this[key].length - 1]?.index + 1 || 1
        this[key].forEach(item => {
          if (item.name === obj.name) {
            item.dosage++
          } else {
            this[key].push(obj)
          }
        })
      })
      this.tableData = this[key]
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    handleDeletePrescription(scope) {
      let that = this
      let title = `确认删除【${that.preTypeFormat(scope.row.type)}】下序号【${scope.$index + 1}】信息吗？`
      this.$modal.confirm(title).then(() => {
        let key = `preform_${that.currentPre}`
        that[key] = that[key].filter(i => i.index !== scope.row.index)
        that.extracted()
      })
        .then(() => this.$modal.msgSuccess("删除成功"))
        .catch((err) => console.log(err));
    },
    // 赋值处方项对象
    assignmentRecipe(item) {
      let obj = {
        checkId: "",
        drugId: "",
        name: "",
        unitPrice: "",
        part: "",
        total: "",
        totalUnit: "",
        unit: "",
        type: "",
        dosage: "",
        usage: "",
        frequency: "",
        addCost: "",
      }
      for (const key in obj) {
        if (key === 'unitPrice') {
          obj[key] = item['drugPrice'] | item['retailPrice'] | item['price']
        } else if (key === 'dosage') {
          obj[key] = item[key] || 1
        } else {
          obj[key] = item[key]
        }
      }
      obj.type = this.currentPre
      return obj
    },
    getCurrentPreTotal(key = `preform_${this.currentPre}`) {
      if (key === 'preform_0' || key === 'preform_1') {
        return this[key].reduce((acc, item) => {
          return acc + (parseInt((item.total || '0')) * parseInt((item.unitPrice || '0')))
        }, 0)
      } else {
        return this[key].reduce((acc, item) => {
          return acc + (parseInt((item.dosage || '0')) * parseInt((item.unitPrice || '0')))
        }, 0)
      }
    },
    // 所有处方总价
    getAllPreTotal() {
      let that = this
      that.allPreTotal = 0
      this.preList.forEach(pre => {
        that.allPreTotal += that.getCurrentPreTotal(`preform_${pre}`)
      })
      that.allPreTotal = that.allPreTotal.toFixed(2)
    },
    // 附加费用
    extraCharge() {
      if (!this.preList.includes('3')) {
        this.preList.push('3')
      }
      this.currentPre = '3'
      this.extracted()
    },
    saveEvent() {
      let data = this.mergeFn()
      this.$emit('savePre', data)
    },
    finish() {
      this.$emit('finish')
    },
    charge() {
      this.$emit('charge')
    },
    // 合并
    mergeFn() {
      return [...this.preform_0, ...this.preform_1, ...this.preform_2, ...this.preform_3]
    }
  }
}
</script>

<template>
  <div class="pre_form">
    <div class="body-center">
      <div class="left">
        <div class="body-header flex-ver-center">
          <el-select v-model="pre" class="select_blod" placeholder="添加处方">
            <el-option
              v-for="dict in dict.type.sys_prescription_type"
              :key="dict.value"
              v-show="dict.label !== '附加费用'"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
          <div class="pre_list">
            <el-tag v-for="tag in preList"
                    :key="tag" effect="plain"
                    style="margin-left: 5px" closable type="info"
                    :class="tag === currentPre ? 'active' : ''"
                    @click="handleTagClick(tag)"
                    @close="handleClose(tag)">
              {{ preTypeFormat(tag) }}
            </el-tag>
          </div>
        </div>
        <div class="prescription">
          <h1 class="h_title" style="padding: 5px;">Rp</h1>
          <el-table border v-if="currentPre < 2" :data="tableData" :height="tableHight" style="width: 100%">
            <el-table-column align="center" label="序号" width="50">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" :show-overflow-tooltip="true"/>
            <el-table-column align="center" label="频度" prop="frequency" width="125">
              <template #default="scope">
                <el-select v-model="scope.row.frequency" placeholder="频度">
                  <el-option
                    v-for="dict in dict.type.sys_frequentness_frequentness"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column align="center" label="单次用量" prop="dosage" width="165">
              <template #default="scope">
                <div class="flex-ver-center">
                  <el-input style="width: 60px" v-model="scope.row.dosage" placeholder="单次用量"/>
                  <el-select style="width: 110px;margin-left: 5px" v-model="scope.row.unit" placeholder="单位">
                    <el-option
                      v-for="dict in dict.type.sys_unit_unit"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="用法" prop="usage" width="100">
              <template #default="scope">
                <el-select v-model="scope.row.usage" placeholder="用法">
                  <el-option
                    v-for="dict in dict.type.sys_usage_usage"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column align="center" label="总量" prop="total" width="100">
              <template #default="scope">
                <div class="flex-ver-center">
                  <el-input style="width: 60px;margin-right: 5px" v-model="scope.row.total" placeholder="单次用量"/>
                  {{ commonDictFormat('sys_unit_unit', scope.row.totalUnit) }}
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice" width="100">
              <template slot-scope="scope">
                <div class="flex-ver-center">
                  ￥
                  <el-input v-model="scope.row.unitPrice" placeholder="单价"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="编辑" width="60">
              <template #default="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeletePrescription(scope)"
                  v-hasPermi="['recipe:recipe:remove']"
                />
              </template>
            </el-table-column>
          </el-table>
          <el-table v-if="currentPre === '2'" border :data="tableData" :height="tableHight" style="width: 100%">
            <el-table-column align="center" label="序号" width="60">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" show-overflow-tooltip/>
            <el-table-column align="center" label="部位" prop="part" width="150">
              <template #default="scope">
                <el-input v-model="scope.row.part" placeholder="部位"/>
              </template>
            </el-table-column>
            <el-table-column align="center" label="次数" prop="dosage">
              <template #default="scope">
                <div class="flex-ver-center">
                  <el-input style="width: 60px;" v-model="scope.row.dosage" placeholder="单次用量"/>
                  <el-select style="width: 110px;margin-left: 5px" v-model="scope.row.unit" placeholder="单位">
                    <el-option
                      v-for="dict in dict.type.sys_project_unit"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice" width="150">
              <template slot-scope="scope">
                <div class="flex-ver-center">
                  ￥
                  <el-input v-model="scope.row.unitPrice" placeholder="单价"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="编辑" width="60">
              <template #default="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeletePrescription(scope)"
                  v-hasPermi="['recipe:recipe:remove']"
                />
              </template>
            </el-table-column>
          </el-table>
          <el-table v-if="currentPre === '3'" border :data="tableData" :height="tableHight" style="width: 100%">
            <el-table-column align="center" label="序号" width="60">
              <template #default="scope">{{ scope.$index + 1 }}</template>
            </el-table-column>
            <el-table-column align="center" label="名称" prop="name" show-overflow-tooltip/>
            <el-table-column align="center" label="数量" prop="dosage" width="150">
              <template #default="scope">
                <div class="flex-ver-center">
                  <el-input v-model="scope.row.dosage" placeholder="单次用量"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="单价" prop="unitPrice">
              <template slot-scope="scope">
                <div class="flex-ver-center">
                  ￥
                  <el-input v-model="scope.row.unitPrice" placeholder="单价"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="编辑" width="60">
              <template #default="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeletePrescription(scope)"
                  v-hasPermi="['recipe:recipe:remove']"
                />
              </template>
            </el-table-column>
          </el-table>
          <div class="footer">
            <el-button @click="extraCharge" type="success" icon="el-icon-edit">附加费用</el-button>
            <div class="doctor">
              接诊医生:
              <el-tag effect="plain" style="margin-left: 5px">{{ doctor }}</el-tag>
            </div>
            <div class="total">
              此方合计：<span class="blod_span">{{ currentPreTotal }}</span> 元；
              共 <span class="blod_span">{{ preList.includes('3') ? preList.length - 1 : preList.length }}</span> 个处方；
              共计：<span class="blod_span">{{ allPreTotal }}</span> 元；
            </div>
          </div>
        </div>
      </div>
      <div class="item_list">
        <div class="opt">
          <el-select v-if="currentPre === '0'" class="select_blod"
                     v-model="drugForm.drugType" placeholder="药品分类" clearable>
            <el-option
              v-for="dict in dict.type.sys_drug_classification"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-select v-if="currentPre === '1'" class="select_blod"
                     v-model="drugForm.drugType" placeholder="药品分类" clearable>
            <el-option
              v-for="dict in dict.type.sys_china_drug"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-select v-if="currentPre === '2'" class="select_blod" style="width: 140px"
                     v-model="checkForm.type" placeholder="项目类别" clearable>
            <el-option
              v-for="dict in dict.type.sys_project_classification"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-input
            placeholder="请输入名称中文或拼音首拼"
            suffix-icon="el-icon-search"
            style="width: 220px;" clearable
            v-if="currentPre < 2"
            v-model="drugForm.name"/>
          <el-input
            placeholder="请输入检查项目名"
            suffix-icon="el-icon-search"
            style="width: 200px;" clearable
            v-if="currentPre === '2'"
            v-model="checkForm.name"/>
          <el-input
            placeholder="请输入附加费用名"
            suffix-icon="el-icon-search"
            style="width: 210px;" clearable
            v-if="currentPre === '3'"
            v-model="expenseForm.name"/>
          <el-button @click="addItem" type="primary" plain icon="el-icon-first-aid-kit">
            添加{{ currentPre >= 2 ? '项目' : '药品' }}
          </el-button>
        </div>
        <el-table border v-if="currentPre < 2" v-loading="loading" @row-click="clickRow"
                  ref="table" :height="tableHight + 50" :data="itemList"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="名称" align="center" prop="name"/>
          <el-table-column label="规格" align="center" prop="specification"/>
          <el-table-column label="价格" align="center" prop="drugPrice"/>
        </el-table>
        <el-table border v-if="currentPre === '2'" v-loading="loading" @row-click="clickRow" border key="table_check"
                  ref="table" :height="tableHight + 50" :data="itemList"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="名称" align="center" prop="name"/>
          <el-table-column label="项目类别" align="center" show-overflow-tooltip>
            <template #default="scope">
              {{ commonDictFormat('sys_project_classification', scope.row.type) }}
            </template>
          </el-table-column>
          <el-table-column label="单位" align="center" width="60">
            <template #default="scope">
              {{ commonDictFormat('sys_project_unit', scope.row.unit) }}
            </template>
          </el-table-column>
          <el-table-column label="单价" align="center" width="80">
            <template #default="scope">￥{{ scope.row.retailPrice }}</template>
          </el-table-column>
        </el-table>
        <el-table border v-if="currentPre === '3'" v-loading="loading" @row-click="clickRow" border key="table_exp"
                  ref="table" :height="tableHight + 50" :data="itemList"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="名称" align="center" prop="name"/>
          <el-table-column label="单价" align="center">
            <template #default="scope">￥{{ scope.row.price }}</template>
          </el-table-column>
        </el-table>
        <div class="footer flex-ver-center">
          <el-button @click="saveEvent" type="primary" icon="el-icon-box">保存</el-button>
          <el-button @click="charge" type="warning" icon="el-icon-coin">收费</el-button>
          <el-button @click="finish" type="warning" icon="el-icon-switch-button">结束问诊</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.select_blod {
  width: 120px;
}

.select_blod /deep/ input::placeholder,
.select_blod /deep/ input::-webkit-input-placeholder {
  color: #000 !important;
  font-weight: 400 !important;
}

.body-header .el-tag {
  cursor: pointer;
}

.body-header .el-tag:hover, .active {
  background-color: #e8f4ff;
  border-color: #d1e9ff;
  color: #1890ff;
}

.body-center .left {
  width: 0;
  flex: 65%;
}

.prescription {
  box-shadow: 0 0 6px rgba(102, 110, 232, 0.4);
}

.body-center .item_list {
  margin-left: 10px;
  width: 0;
  flex: 35%;
}

.item_list .opt {
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
}

.prescription .footer {
  display: flex;
  justify-content: space-around;
  align-items: center;
  align-content: center;
  padding: 8px 0;
  color: #000;
}

.item_list .footer {
  padding: 8px 0;
  justify-content: flex-end;
}

.item_list .footer .el-button {
  font-weight: bold;
}

/deep/ .el-input__inner {
  text-align: center;
}

/deep/ .el-select .el-input__inner {
  text-align: left;
}
</style>
