<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="科室" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入科室"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接诊医生" prop="doctorId">
        <el-input
          v-model="queryParams.doctorId"
          placeholder="请输入接诊医生"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="就诊时间" prop="receTime">
        <el-date-picker clearable
          v-model="queryParams.receTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择就诊时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="患者名称" prop="payMethod">
        <el-input
          v-model="queryParams.payMethod"
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
        >新增</el-button>
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
        >修改</el-button>
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['os:regorder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="regorderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="挂号单号" align="center" prop="regId" />
      <el-table-column label="患者编号" align="center" prop="patientId" />
      <el-table-column label="接诊类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_reception_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="诊疗费" align="center" prop="fee" />
      <el-table-column label="科室" align="center" prop="deptId" />
      <el-table-column label="接诊医生" align="center" prop="doctorId" />
      <el-table-column label="就诊时间" align="center" prop="receTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="就诊状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_visit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="实收金额" align="center" prop="money" />
      <el-table-column label="支付方式" align="center" prop="payMethod" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:regorder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:regorder:remove']"
          >删除</el-button>
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
        <el-form-item label="患者编号" prop="patientId">
          <el-input v-model="form.patientId" placeholder="请输入患者编号" />
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
        <el-form-item label="挂单费用" prop="orderFee">
          <el-input v-model="form.orderFee" placeholder="请输入挂单费用" />
        </el-form-item>
        <el-form-item label="诊疗费" prop="fee">
          <el-input v-model="form.fee" placeholder="请输入诊疗费" />
        </el-form-item>
        <el-form-item label="科室" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入科室" />
        </el-form-item>
        <el-form-item label="接诊医生" prop="doctorId">
          <el-input v-model="form.doctorId" placeholder="请输入接诊医生" />
        </el-form-item>
        <el-form-item label="就诊时间" prop="receTime">
          <el-date-picker clearable
            v-model="form.receTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择就诊时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="就诊状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_visit_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="实收金额" prop="money">
          <el-input v-model="form.money" placeholder="请输入实收金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="payMethod">
          <el-input v-model="form.payMethod" placeholder="请输入支付方式" />
        </el-form-item>
        <el-form-item label="收费日期" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择收费日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRegorder, getRegorder, delRegorder, addRegorder, updateRegorder } from "@/api/os/regorder";

export default {
  name: "Regorder",
  dicts: ['sys_visit_status', 'sys_reception_type'],
  data() {
    return {
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
        receTime: null,
        payMethod: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        patientId: [
          { required: true, message: "患者编号不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "接诊类型不能为空", trigger: "change" }
        ],
        orderFee: [
          { required: true, message: "挂单费用不能为空", trigger: "blur" }
        ],
        fee: [
          { required: true, message: "诊疗费不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "科室不能为空", trigger: "blur" }
        ],
        doctorId: [
          { required: true, message: "接诊医生不能为空", trigger: "blur" }
        ],
        receTime: [
          { required: true, message: "就诊时间不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "就诊状态不能为空", trigger: "change" }
        ],
        money: [
          { required: true, message: "实收金额不能为空", trigger: "blur" }
        ],
        payMethod: [
          { required: true, message: "支付方式不能为空", trigger: "blur" }
        ],
        payTime: [
          { required: true, message: "收费日期不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询挂号订单列表 */
    getList() {
      this.loading = true;
      listRegorder(this.queryParams).then(response => {
        this.regorderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        fee: null,
        deptId: null,
        doctorId: null,
        receTime: null,
        status: null,
        money: null,
        payMethod: null,
        payTime: null
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
      this.ids = selection.map(item => item.regId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
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
      this.$modal.confirm('是否确认删除挂号订单编号为"' + regIds + '"的数据项？').then(function() {
        return delRegorder(regIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('os/regorder/export', {
        ...this.queryParams
      }, `regorder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>