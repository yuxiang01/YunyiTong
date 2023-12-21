<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入项目名称"
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
          v-hasPermi="['os:checkItem:add']"
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
          v-hasPermi="['os:checkItem:edit']"
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
          v-hasPermi="['os:checkItem:remove']"
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
          v-hasPermi="['os:checkItem:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table height="500" v-loading="loading" :data="checkItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="项目编号" align="center" prop="checkId"/>
      <el-table-column label="项目名称" align="center" prop="name"/>
      <el-table-column label="项目类别" align="center" prop="type">
        <template #default="scope">
          <dict-tag :options="dict.type.sys_project_classification" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="部位" align="center" prop="part"/>
      <el-table-column label="零售价" align="center" prop="retailPrice">
        <template #default="scope">￥{{ scope.row.retailPrice.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unit">
        <template #default="scope">
          <dict-tag :options="dict.type.sys_project_unit" :value="scope.row.unit"/>
        </template>
      </el-table-column>
      <el-table-column label="项目状态" align="center" prop="status">
        <template #default="scope">{{ scope.row.status === 0 ? '启用' : '禁用' }}</template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:checkItem:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:checkItem:remove']"
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

    <!-- 添加或修改检查项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="部位" prop="part">
          <el-input v-model="form.part" placeholder="请输入部位"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select style="width: 100%;" v-model="form.type" placeholder="请选择项目类型">
            <el-option
              v-for="dict in dict.type.sys_project_classification"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="零售价" prop="retailPrice">
          <el-input v-model="form.retailPrice" placeholder="请输入零售价"/>
        </el-form-item>
        <el-form-item label="项目状态" prop="status">
          <el-switch v-model="form.status"
                     active-text="启用"
                     :active-value="0"
                     :inactive-value="1"
                     inactive-text="禁用"/>
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select style="width: 100%" v-model="form.unit" placeholder="请选择单位">
            <el-option
              v-for="dict in dict.type.sys_project_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {listCheckItem, getCheckItem, delCheckItem, addCheckItem, updateCheckItem} from "@/api/os/checkItem";

export default {
  name: "CheckItem",
  dicts: ['sys_project_classification', 'sys_project_unit'],
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
      // 检查项目表格数据
      checkItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "项目名称不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "项目类别不能为空", trigger: "change"}
        ],
        retailPrice: [
          {required: true, message: "零售价不能为空", trigger: "blur"}
        ],
        unit: [
          {required: true, message: "单位不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "项目状态不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询检查项目列表 */
    getList() {
      this.loading = true;
      listCheckItem(this.queryParams).then(response => {
        this.checkItemList = response.rows;
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
        checkId: null,
        name: null,
        type: null,
        part: null,
        retailPrice: null,
        unit: null,
        status: 0,
        remark: null,
        createTime: null,
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
      this.ids = selection.map(item => item.checkId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加检查项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const checkId = row.checkId || this.ids
      getCheckItem(checkId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改检查项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.checkId != null) {
            updateCheckItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckItem(this.form).then(response => {
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
      const checkIds = row.checkId || this.ids;
      this.$modal.confirm('是否确认删除检查项目编号为"' + checkIds + '"的数据项？').then(function () {
        return delCheckItem(checkIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('os/checkItem/export', {
        ...this.queryParams
      }, `checkItem_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
