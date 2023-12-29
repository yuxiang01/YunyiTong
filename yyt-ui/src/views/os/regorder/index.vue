<script>
import {listRegorder, getRegorder, delRegorder, addRegorder, updateRegorder} from "@/api/os/regorder";
import {listDoctor} from "@/api/system/doctor";
import {doctorsToMap} from "@/utils/web-utils";
import {listPatient} from "@/api/os/patient";
import PatentSelected from "@/components/PatentSelected/index.vue";

export default {
  name: "Regorder",
  dicts: ['sys_visit_status', 'sys_reception_type', 'sys_user_sex'],
  data() {
    return {
      // 医生列表
      doctorList: [],
      // 科室-医生列表
      cascadeList: [],
      // 患者列表
      patientList: [],
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
      // 挂号订单表格数据
      regorderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        doctorId: null,
        patient: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientId: [
          {required: true, message: "就诊患者不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "接诊类型不能为空", trigger: "change"}
        ],
        orderFee: [
          {required: true, message: "挂单费用不能为空", trigger: "blur"}
        ],
        doctorId: [
          {required: true, message: "接诊医生不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "就诊状态不能为空", trigger: "change"}
        ]
      },
    };
  },
  created() {
    this.getList();
    listPatient().then(res => this.patientList = res.rows)
  },
  methods: {
    handleChange(value) {
      let deptId = value[0]
      let doctorId = value[1]
      // 赋值该医生的挂号费
      this.form.deptId = deptId
      this.form.doctorId = doctorId
      this.form.orderFee = this.doctorList.filter(i => i.doctorId === doctorId)[0].cost
    },
    queryChange(value) {
      this.queryParams.deptId = value[0]
      this.queryParams.doctorId = value[1]
    },
    /** 查询挂号订单列表 */
    async getList() {
      this.loading = true;
      listRegorder(this.queryParams).then(response => {
        this.regorderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      let {rows} = await listDoctor()
      this.doctorList = rows
      this.cascadeList = doctorsToMap(rows)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        regId: null,
        patientId: null,
        type: null,
        orderFee: null,
        deptId: null,
        doctorId: null,
        receTime: null,
        status: null
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
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        doctorId: null,
        patient: null
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.regId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.status = '0'
      this.title = "添加挂号订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const regId = row.regId || this.ids
      getRegorder(regId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改挂号订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.regId != null) {
            updateRegorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegorder(this.form).then(response => {
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
      const regIds = row.regId || this.ids;
      this.$modal.confirm('是否确认删除挂号订单编号为"' + regIds + '"的数据项？').then(function () {
        return delRegorder(regIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('os/regorder/export', {
        ...this.queryParams
      }, `regorder_${new Date().getTime()}.xlsx`)
    }
  },
  components: {PatentSelected}
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="接诊医生" prop="doctorId">
        <el-cascader v-model="queryParams.doctorId" clearable
                     :options="cascadeList"
                     :props="{ expandTrigger: 'hover' }"
                     @change="queryChange" placeholder="请选择接诊医生"/>
      </el-form-item>
      <el-form-item label="患者名称" prop="patient">
        <el-input
          v-model="queryParams.patient"
          placeholder="请输入患者名称"
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
          v-hasPermi="['os:regorder:add']"
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
          v-hasPermi="['os:regorder:edit']"
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
          v-hasPermi="['os:regorder:remove']"
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
          v-hasPermi="['os:regorder:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" height="450" :data="regorderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="挂号单号" align="center" prop="regId"/>
      <el-table-column label="患者" align="center" prop="patient"/>
      <el-table-column label="接诊类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_reception_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="科室" align="center" prop="deptName"/>
      <el-table-column label="接诊医生" align="center" prop="doctor"/>
      <el-table-column label="就诊时间" align="center" prop="receTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_visit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:regorder:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:regorder:remove']"
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

    <!-- 添加或修改挂号订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="就诊患者" prop="patientId">
          <el-select v-model="form.patientId" :popper-append-to-body="false" placeholder="请选择患者">
            <el-option
              v-for="item in patientList"
              :key="item.patientId"
              :label="item.name"
              :value="item.patientId">
              <PatentSelected :item="item"/>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接诊类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择接诊类型">
            <el-option
              v-for="dict in dict.type.sys_reception_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接诊医生" prop="doctorId">
          <el-cascader v-model="form.doctorId"
                       :options="cascadeList"
                       :props="{ expandTrigger: 'hover' }"
                       @change="handleChange" placeholder="请选择接诊医生"/>
        </el-form-item>
        <el-form-item label="挂单费用" prop="orderFee">
          <el-input v-model="form.orderFee" readonly placeholder="请先选择接诊医生"/>
        </el-form-item>
        <el-form-item label="就诊时间" prop="receTime">
          <el-date-picker clearable
                          v-model="form.receTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择就诊时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="就诊状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择就诊状态">
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
/deep/ .el-select-dropdown__item {
  height: 70px !important;
  line-height: 70px !important;
}

/deep/ .el-form .el-input {
  width: 225px;
}
</style>

