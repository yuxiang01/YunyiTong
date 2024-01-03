<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="患者姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入患者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['os:patient:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['os:patient:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['os:patient:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:patient:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" height="500" :data="patientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="患者编号" align="center" prop="patientId"/>
      <el-table-column label="患者姓名" align="center" prop="name"/>
      <el-table-column label="患者性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="患者年龄" align="center">
        <template #default="scope">{{ calculateAge(scope.row.card) }}</template>
      </el-table-column>
      <el-table-column label="手机号码" align="center" prop="phone"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleQueryInfo(scope.row)"
            v-hasPermi="['os:patient:query']"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="receive(scope.row)"
            v-hasPermi="['os:regorder:add']"
          >接诊
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:patient:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:patient:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改患者对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="患者姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入患者姓名"/>
        </el-form-item>
        <el-form-item label="证件号码" prop="card">
          <el-input v-model="form.card" @blur="getAge" placeholder="请输入证件号码"/>
        </el-form-item>
        <el-form-item label="患者年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入患者年龄"/>
        </el-form-item>
        <el-form-item label="患者性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-cascader
            size="large"
            :props="{ expandTrigger: 'hover' }"
            :options="options"
            v-model="form.address" clearable>
          </el-cascader>
        </el-form-item>
        <el-form-item label="详细地址" prop="detailsAddress">
          <el-input v-model="form.detailsAddress" placeholder="请输入详细地址"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="挂号" :visible.sync="openReg" width="400px" append-to-body>
      <el-form ref="regForm" :model="regForm" :rules="regRules" label-width="80px">
        <el-form-item label="接诊类型" prop="type">
          <el-select v-model="regForm.type" placeholder="请选择接诊类型">
            <el-option
              v-for="dict in dict.type.sys_reception_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接诊医生" prop="doctorId">
          <el-cascader v-model="regForm.doctorId"
                       :options="cascadeList"
                       :props="{ expandTrigger: 'hover' }"
                       @change="selectDoctor" placeholder="请选择接诊医生"/>
        </el-form-item>
        <el-form-item label="挂单费用" prop="orderFee">
          <el-input style="width: 224px;" v-model="regForm.orderFee" readonly placeholder="请先选择接诊医生"/>
        </el-form-item>
        <el-form-item label="就诊状态" prop="status">
          <el-select v-model="regForm.status" placeholder="请选择就诊状态">
            <el-option
              v-for="dict in dict.type.sys_visit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRegForm">确 定</el-button>
        <el-button @click="regCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listPatient, getPatient, delPatient, addPatient, updatePatient, getAddress} from "@/api/os/patient";
import {calculateAge, doctorsToMap, getEncode} from "@/utils/web-utils";
import {pcaTextArr} from 'element-china-area-data'
import {listDoctor} from "@/api/system/doctor";
import {addRegorder} from "@/api/os/regorder";
import dayjs from "dayjs";

export default {
  name: "Patient",
  dicts: ['sys_user_sex', 'sys_reception_type', 'sys_visit_status'],
  data() {
    return {
      cascadeList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 患者表格数据
      patientList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openReg: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
      },
      // 表单参数
      form: {},
      regForm: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "患者姓名不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "患者性别不能为空", trigger: "change"}
        ],
        age: [
          {required: true, message: "患者年龄不能为空", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "手机号码不能为空", trigger: "blur"}
        ],
        card: [
          {required: true, message: "证件号码不能为空", trigger: "blur"},
          {pattern: /\d{17}[\d|x]|\d{15}/, message: "请填写正确的身份证", trigger: "blur"}
        ],
        address: [
          {required: true, message: "地址不能为空", trigger: "blur"}
        ],
        detailsAddress: [
          {required: true, message: "详细地址不能为空", trigger: "blur"}
        ]
      },
      regRules: {
        doctorId: [
          {required: true, message: "接诊医生不能为空", trigger: "blur"}
        ],
      },
      options: pcaTextArr,
      doctorList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    calculateAge,
    /** 查询患者列表 */
    getList() {
      this.loading = true;
      listPatient(this.queryParams).then(response => {
        this.patientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listDoctor().then(({rows}) => {
        this.doctorList = rows
        this.cascadeList = doctorsToMap(rows)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        patientId: null,
        name: null,
        sex: null,
        age: null,
        phone: null,
        card: null,
        address: null,
        detailsAddress: null,
        remark: null,
        belongToUser: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.patientId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加患者";
    },
    /** 修改按钮操作 */
    async handleUpdate(row) {
      this.reset();
      const patientId = row.patientId || this.ids
      let response = await getPatient(patientId)
      this.form = response.data;
      this.form.address = this.form.address.split('/')
      this.open = true;
      this.title = "修改患者";
      this.getAge()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.address = this.form.address.join('/')
          if (this.form.patientId != null) {
            updatePatient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPatient(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const patientIds = row.patientId || this.ids;
      this.$modal.confirm('是否确认删除患者编号为"' + patientIds + '"的数据项？').then(function () {
        return delPatient(patientIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('os/patient/export', {
        ...this.queryParams
      }, `patient_${new Date().getTime()}.xlsx`)
    },
    getAge() {
      let pattern = /\d{17}[\d|x]|\d{15}/
      let card = this.form.card
      if (pattern.test(card)) {
        this.form.sex = parseInt(card.substr(16, 1)) % 2 === 0 ? '1' : '0'
        this.form.age = calculateAge(card)
      }
    },
    handleQueryInfo(row) {
      const patientId = row.patientId;
      this.$router.push("/os/patient-info/" + getEncode(patientId));
    },
    selectDoctor(value) {
      let deptId = value[0]
      let doctorId = value[1]
      // 赋值该医生的挂号费
      this.regForm.deptId = deptId
      this.regForm.doctorId = doctorId
      this.regForm.orderFee = this.doctorList.filter(i => i.doctorId === doctorId)[0].cost
    },
    receive(row) {
      this.regForm = {
        patientId: row.patientId,
        type: '0',
        orderFee: null,
        deptId: null,
        doctorId: null,
        receTime: dayjs().format('YYYY-MM-DD HH:mm:ss'),
        status: '0'
      }
      this.openReg = true
    },
    submitRegForm() {
      this.$refs["regForm"].validate(valid => {
        if (valid) {
          addRegorder(this.regForm).then(response => {
            this.$modal.msgSuccess("挂号成功");
            this.openReg = false;
            this.getList();
          });
        }
      })
    },
    regCancel() {
      this.openReg = false
    }
  }
};
</script>
