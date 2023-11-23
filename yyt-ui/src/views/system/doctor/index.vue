<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职位" prop="position">
        <el-select v-model="queryParams.position" placeholder="请选择职位" clearable>
          <el-option
            v-for="dict in postList"
            :key="dict.postId"
            :label="dict.postName"
            :value="dict.postId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="科室" prop="deptId">
        <el-select v-model="queryParams.deptId" placeholder="请选择所属科室" clearable>
          <el-option
            v-for="dict in deptList"
            :key="dict.deptId"
            :label="dict.deptName"
            :value="dict.deptId"
          />
        </el-select>
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
          v-hasPermi="['system:doctor:add']"
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
          v-hasPermi="['system:doctor:edit']"
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
          v-hasPermi="['system:doctor:remove']"
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
          v-hasPermi="['system:doctor:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doctorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="所属科室" align="center" prop="deptName"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="手机号" align="center" prop="phone"/>
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="职位" align="center" prop="postName"/>
      <el-table-column label="年龄" align="center" prop="age"/>
      <el-table-column label="挂号费用" align="center" prop="cost"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="挂号人数" align="center" prop="regNumber"/>
      <el-table-column label="每小时挂号人数" align="center" width="125" prop="regPerHour"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:doctor:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:doctor:remove']"
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

    <!-- 添加或修改医生对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" maxlength="20" show-password placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄"/>
        </el-form-item>
        <el-form-item label="挂号费用" prop="cost">
          <el-input v-model="form.cost" placeholder="请输入挂号费用"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="挂号人数" prop="regNumber">
          <el-input v-model="form.regNumber" placeholder="请输入挂号人数"/>
        </el-form-item>
        <el-form-item label="每小时挂号人数" prop="regPerHour">
          <el-input v-model="form.regPerHour" placeholder="请输入每小时挂号人数"/>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-select v-model="form.position" placeholder="请选择职位" clearable>
            <el-option
              v-for="dict in postList"
              :key="dict.postId"
              :label="dict.postName"
              :value="dict.postId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="科室" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择所属科室" clearable>
            <el-option
              v-for="dict in deptList"
              :key="dict.deptId"
              :label="dict.deptName"
              :value="dict.deptId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="医生简介" prop="intro">
          <el-input v-model="form.intro" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="擅长领域" prop="areasOfExpertise">
          <el-input v-model="form.areasOfExpertise" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="值班信息" prop="dutyInfo">
          <el-input v-model="form.dutyInfo" placeholder="请输入值班信息"/>
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
import {listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor} from "@/api/system/doctor";
import {listDept} from "@/api/system/dept";
import {listPost} from "@/api/system/post";

export default {
  name: "Doctor",
  dicts: ['sys_user_sex', 'sys_normal_disable'],
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
      // 医生表格数据
      doctorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        name: null,
        phone: null,
        sex: null,
        position: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [
          {required: true, message: "科室不能为空", trigger: "change"}
        ],
        name: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "手机号不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "change"}
        ],
        position: [
          {required: true, message: "职位不能为空", trigger: "change"}
        ],
        age: [
          {required: true, message: "年龄不能为空", trigger: "blur"}
        ],
        cost: [
          {required: true, message: "挂号费用不能为空", trigger: "blur"}
        ],
        regNumber: [
          {required: true, message: "挂号人数不能为空", trigger: "blur"}
        ],
        regPerHour: [
          {required: true, message: "每小时挂号人数不能为空", trigger: "blur"}
        ],
        intro: [
          {required: true, message: "医生简介不能为空", trigger: "blur"}
        ],
        areasOfExpertise: [
          {required: true, message: "擅长领域不能为空", trigger: "blur"}
        ],
        dutyInfo: [
          {required: true, message: "值班信息不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "修改人不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ]
      },
      deptList: [],
      postList: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询医生列表 */
    getList() {
      this.loading = true;
      listDoctor(this.queryParams).then(response => {
        this.doctorList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listDept().then(res => this.deptList = res.data)
      listPost().then(res => this.postList = res.rows)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        doctorId: null,
        deptId: null,
        name: null,
        password: null,
        phone: null,
        sex: null,
        position: null,
        age: null,
        cost: null,
        status: null,
        regNumber: null,
        regPerHour: null,
        intro: null,
        areasOfExpertise: null,
        dutyInfo: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.doctorId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医生";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const doctorId = row.doctorId || this.ids
      getDoctor(doctorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医生";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.doctorId != null) {
            updateDoctor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoctor(this.form).then(response => {
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
      const doctorIds = row.doctorId || this.ids;
      this.$modal.confirm('是否确认删除医生编号为"' + doctorIds + '"的数据项？').then(function () {
        return delDoctor(doctorIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/doctor/export', {
        ...this.queryParams
      }, `doctor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
