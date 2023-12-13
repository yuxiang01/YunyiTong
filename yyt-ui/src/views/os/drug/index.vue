<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="药品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入药品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处方类型" prop="preType">
        <el-select v-model="queryParams.preType" placeholder="请选择处方类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_prescription_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="药品状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择药品状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="药品分类" prop="drugType">
        <el-select v-model="queryParams.drugType" placeholder="请选择药品分类" clearable>
          <el-option
            v-for="dict in dict.type.sys_drug_classification"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['os:drug:add']"
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
          v-hasPermi="['os:drug:edit']"
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
          v-hasPermi="['os:drug:remove']"
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
          v-hasPermi="['os:drug:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" height="500" :data="drugList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="药品编号" align="center" prop="drugId"/>
      <el-table-column label="药品名称" align="center" prop="name"/>
      <el-table-column label="规格" align="center" prop="specification"/>
      <el-table-column label="处方类型" align="center" prop="preType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_prescription_type" :value="scope.row.preType"/>
        </template>
      </el-table-column>
      <el-table-column label="采购价" align="center" prop="purchasePrice"/>
      <el-table-column label="售药价" align="center" prop="drugPrice"/>
      <el-table-column label="药品状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="拼音码" align="center" prop="pinyinCode"/>
      <el-table-column label="药品分类" align="center" prop="drugType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_drug_classification" :value="scope.row.drugType"/>
        </template>
      </el-table-column>
      <el-table-column label="药品剂型" align="center" prop="drugDosageForm">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_drug_dosage" :value="scope.row.drugDosageForm"/>
        </template>
      </el-table-column>
      <el-table-column label="是否OTC" align="center" prop="isOtc">
        <template slot-scope="scope">
          {{ scope.row.isOtc == 0 ? '处方药' : '非处方药'}}
        </template>
      </el-table-column>
      <el-table-column label="用法" align="center" prop="usage">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_usage_usage" :value="scope.row.usage"/>
        </template>
      </el-table-column>
      <el-table-column label="单次用量" align="center" prop="dosage"/>
      <el-table-column label="频率" align="center" prop="frequency">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_frequentness_frequentness" :value="scope.row.frequency"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['os:drug:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['os:drug:remove']"
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

    <!-- 添加或修改药品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="660px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="药品名称" prop="name">
            <el-input v-model="form.name" @blur="createPy" placeholder="请输入药品名称"/>
          </el-form-item>
          <el-form-item label="拼音码" prop="pinyinCode">
            <el-input v-model="form.pinyinCode" placeholder="请输入拼音码"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="采购价" prop="purchasePrice">
            <el-input v-model="form.purchasePrice" placeholder="请输入采购价"/>
          </el-form-item>
          <el-form-item label="售药价" prop="drugPrice">
            <el-input v-model="form.drugPrice" placeholder="请输入售药价"/>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="规格" prop="specification">
            <el-input v-model="form.specification" placeholder="请输入规格"/>
          </el-form-item>
          <el-form-item label="处方类型" prop="preType">
            <el-select v-model="form.preType" placeholder="请选择处方类型">
              <el-option
                v-for="dict in dict.type.sys_prescription_type"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="药品状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择药品状态">
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="药品分类" prop="drugType">
            <el-select v-model="form.drugType" placeholder="请选择药品分类">
              <el-option
                v-for="dict in dict.type.sys_drug_classification"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="药品剂型" prop="drugDosageForm">
            <el-select v-model="form.drugDosageForm" placeholder="请选择药品剂型">
              <el-option
                v-for="dict in dict.type.sys_drug_dosage"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用法" prop="usage">
            <el-select v-model="form.usage" placeholder="请选择用法">
              <el-option
                v-for="dict in dict.type.sys_usage_usage"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row type="flex" justify="left" align="top" :gutter="15">
          <el-form-item label="单次用量" prop="dosage">
            <el-input v-model="form.dosage" placeholder="请输入单次用量"/>
          </el-form-item>
          <el-form-item label="频率" prop="frequency">
            <el-select v-model="form.frequency" placeholder="请输入频率">
              <el-option
                v-for="dict in dict.type.sys_frequentness_frequentness"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-form-item label="是否OTC" prop="isOtc">
          <el-switch v-model="form.isOtc"
                     active-text="处方药"
                     :active-value="0"
                     :inactive-value="1"
                     inactive-text="非处方药"/>
        </el-form-item>
        <el-form-item label="药品备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入药品备注"/>
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
import {listDrug, getDrug, delDrug, addDrug, updateDrug} from "@/api/os/drug";
import {makePy} from "@/utils/web-utils";

export default {
  name: "Drug",
  dicts: ['sys_drug_classification', 'sys_drug_dosage', 'sys_prescription_type', 'sys_usage_usage', 'sys_frequentness_frequentness', 'sys_normal_disable'],
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
      // 药品表格数据
      drugList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        preType: null,
        status: null,
        drugType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "药品名称不能为空", trigger: "blur"}
        ],
        specification: [
          {required: true, message: "规格不能为空", trigger: "blur"}
        ],
        preType: [
          {required: true, message: "处方类型不能为空", trigger: "change"}
        ],
        purchasePrice: [
          {required: true, message: "采购价不能为空", trigger: "blur"}
        ],
        drugPrice: [
          {required: true, message: "售药价不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "药品状态不能为空", trigger: "change"}
        ],
        pinyinCode: [
          {required: true, message: "拼音码不能为空", trigger: "blur"}
        ],
        drugType: [
          {required: true, message: "药品分类不能为空", trigger: "change"}
        ],
        drugDosageForm: [
          {required: true, message: "药品剂型不能为空", trigger: "change"}
        ],
        isOtc: [
          {required: true, message: "是否OTC不能为空", trigger: "blur"}
        ],
        usage: [
          {required: true, message: "用法不能为空", trigger: "change"}
        ],
        dosage: [
          {required: true, message: "单次用量不能为空", trigger: "blur"}
        ],
        frequency: [
          {required: true, message: "频率不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询药品列表 */
    getList() {
      this.loading = true;
      listDrug(this.queryParams).then(response => {
        this.drugList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    createPy() {
      this.form.pinyinCode = makePy(this.form.name)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        drugId: null,
        name: null,
        specification: null,
        preType: null,
        purchasePrice: null,
        drugPrice: null,
        status: null,
        pinyinCode: null,
        drugType: null,
        drugDosageForm: null,
        isOtc: 0,
        usage: null,
        dosage: null,
        frequency: null,
        remark: null,
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
      this.ids = selection.map(item => item.drugId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加药品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const drugId = row.drugId || this.ids
      getDrug(drugId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改药品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.drugId != null) {
            updateDrug(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDrug(this.form).then(response => {
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
      const drugIds = row.drugId || this.ids;
      this.$modal.confirm('是否确认删除药品编号为"' + drugIds + '"的数据项？').then(function () {
        return delDrug(drugIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('os/drug/export', {
        ...this.queryParams
      }, `drug_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
/deep/ .el-row--flex {
  justify-content: space-between;
}
</style>
