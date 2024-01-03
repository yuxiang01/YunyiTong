<template>
  <div class="patient_info" v-if="labelPosition === 'left'">
    <div class="base_info">
      <div class="top">
        <h1>基本信息</h1>
        <el-radio-group v-model="labelPosition" size="small">
          <el-radio-button label="left">基本信息</el-radio-button>
          <el-radio-button label="right">电子病历</el-radio-button>
          <el-radio-button label="top">缴费记录</el-radio-button>
        </el-radio-group>
      </div>
      <div class="form">
        <PatientBaseInfo :form="form"/>
      </div>
    </div>
    <div class="association">
      <h1 style="margin-bottom: 20px;">关联家庭成员</h1>
      <div class="table">
        <el-table :data="tableData" height="200" style="width: 100%">
          <template #empty>
            <el-button type="success" icon="el-icon-edit" @click="handleAdd" style="margin: 20px">
              添加家庭成员
            </el-button>
          </template>
          <el-table-column align="center" label="序号" width="80">
            <template #default="scope">{{ scope.$index + 1 }}</template>
          </el-table-column>
          <el-table-column label="家庭关系" align="center" width="100" prop="related">
            <template #default="scope">
              <dict-tag :options="dict.type.os_family_relationship" :value="scope.row.related"/>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="patient.name" label="姓名" width="120"/>
          <el-table-column align="center" label="性别" width="80">
            <template #default="scope">
              <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.patient.sex"/>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="patient.phone" label="手机号码"/>
          <el-table-column align="center" label="年龄" width="80">
            <template #default="scope">{{ calculateAge(scope.row.patient.card) }}岁</template>
          </el-table-column>
          <el-table-column align="center" prop="createTime" label="创建时间"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleQuery(scope.row)"
                v-hasPermi="['os:patient:query']"
              >查看
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['os:families:edit']"
              >编辑
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['os:families:remove']"
              >取消关联
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="add flex-center">
        <el-button v-if="tableData.length > 0" type="success" icon="el-icon-edit" @click="handleAdd"
                   style="margin: 20px">
          添加家庭成员
        </el-button>
      </div>
    </div>

    <!-- 添加或修改患者家庭关系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="relatedForm" :model="relatedForm" :rules="rules" label-width="100px">
        <el-form-item label="家庭关系" prop="related">
          <el-select v-model="relatedForm.related" placeholder="请选择家庭关系">
            <el-option
              v-for="dict in dict.type.os_family_relationship"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关联的病人" prop="relatedPatientId">
          <el-select v-model="relatedForm.relatedPatientId" :popper-append-to-body="false"
                     placeholder="请选择关联的患者">
            <el-option
              v-for="item in patientList"
              :key="item.patientId"
              :label="item.name"
              :value="item.patientId">
              <PatentSelected :item="item"/>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
  <CaseOnline :table-data="preList" @switch="switchEvent" v-else-if="labelPosition === 'right'"/>
  <Records :table-data="preList" @switch="switchEvent" v-else/>
</template>

<script>
import {getPatient, listPatient} from "@/api/os/patient";
import {addFamilies, delFamilies, getFamilies, listFamilies, updateFamilies} from "@/api/os/families";
import {calculateAge, getDecode, getEncode} from "@/utils/web-utils";
import PatentSelected from "@/components/PatentSelected/index.vue";
import PatientBaseInfo from "@/components/PatientInfo/index.vue";
import CaseOnline from "@/views/os/patient/caseOnline.vue";
import Records from "@/views/os/patient/records.vue";
import {caseListOrder} from "@/api/os/recipe";

export default {
  name: "patientInfo",
  components: {Records, CaseOnline, PatientBaseInfo, PatentSelected},
  dicts: ['sys_user_sex', 'os_family_relationship'],
  data() {
    return {
      form: {
        name: undefined,
        sex: undefined,
        age: undefined,
        phone: undefined,
        card: undefined,
        address: undefined,
        detailsAddress: undefined,
        remark: undefined,
      },
      relatedForm: {
        relatedId: null,
        related: null,
        patientId: null,
        relatedPatientId: null,
        createTime: null,
        updateTime: null
      },
      tableData: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 患者列表
      patientList: [],
      preList: [],
      patientId: null,
      // 表单校验
      rules: {
        related: [
          {required: true, message: "家庭关系不能为空", trigger: "change"}
        ],
        relatedPatientId: [
          {required: true, message: "关联的病人不能为空", trigger: "blur"}
        ],
      },
      labelPosition: 'left'
    }
  },
  created() {
    this.patientId = getDecode(this.$route.params && this.$route.params.patientId);
    this.getList()
  },
  methods: {
    calculateAge,
    getList() {
      getPatient(this.patientId).then(({data}) => {
        this.form = data
        let other = {path: this.$route.path, title: `患者信息【${this.form.name}】`}
        this.$store.commit("tagsView/updatePatientViewTitle", other)
      })
      listFamilies({"patientId": this.patientId}).then(res => this.tableData = res.rows)
      listPatient().then(res => this.patientList = res.rows)
      caseListOrder({patientId: this.patientId}).then(res => this.preList = res.rows)
    },
    reset() {
      this.relatedForm = {
        relatedId: null,
        related: null,
        patientId: null,
        relatedPatientId: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("relatedForm");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加患者家庭关系";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const relatedId = row.relatedId
      getFamilies(relatedId).then(response => {
        this.relatedForm = response.data;
        this.open = true;
        this.title = "修改患者家庭关系";
      });
    },
    handleQuery(row) {
      this.$router.push("/os/patient-info/" + getEncode(row.patient.patientId));
    },
    submitForm() {
      this.$refs["relatedForm"].validate(valid => {
        if (valid) {
          if (this.relatedForm.relatedId != null) {
            updateFamilies(this.relatedForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.relatedForm.patientId = this.patientId
            addFamilies(this.relatedForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const relatedIds = row.relatedId;
      this.$modal.confirm('是否确认删除与患者【' + row.patient.name + '】关联？').then(function () {
        return delFamilies(relatedIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    // 切换
    switchEvent(newVal) {
      this.labelPosition = newVal
    }
  }
}
</script>

<style scoped>
.patient_info {
  height: 100%;
  padding: 10px 20px;
  font-size: 14px;
  color: #999;
  background-color: #F2F2F2;
}

.base_info, .association {
  padding: 10px 20px;
  background-color: #FEFEFE;
  border-radius: .4rem;
}

.patient_info h1 {
  margin: 10px;
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 24px;
  color: rgb(102, 110, 232);
}

.base_info .form,
.association > .table {
  padding: 0 40px;
}

.base_info .top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.association {
  margin-top: 10px;
}

/deep/ .el-select-dropdown__item {
  height: 70px !important;
  line-height: 70px !important;
}

/deep/ .el-dialog__body {
  padding: 20px;
}
</style>

