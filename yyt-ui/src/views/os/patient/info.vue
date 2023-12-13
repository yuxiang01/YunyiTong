<template>
  <div class="patient_info">
    <div class="base_info">
      <h1>基本信息</h1>
      <div class="form">
        <el-form ref="elForm" :model="form" size="medium" label-width="150px" label-position="top">
          <el-row type="flex" justify="left" align="top" :gutter="15">
            <el-form-item label="患者姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入患者姓名" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
            <el-form-item label="患者性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择患者性别" clearable :style="{width: '100%'}">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="患者年龄" prop="age">
              <el-input v-model="form.age" placeholder="请输入患者年龄" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
            <el-form-item label="证件号码" prop="card">
              <el-input v-model="form.card" placeholder="请输入证件号码" :maxlength="18" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-row>
          <el-row type="flex" justify="left" align="top" :gutter="15">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址" :maxlength="18" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
            <el-form-item label="详细地址" prop="detailsAddress">
              <el-input v-model="form.detailsAddress" placeholder="请输入详细地址" :maxlength="18" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" :maxlength="18" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </div>
    </div>
    <div class="association">
      <h1>关联家庭成员</h1>
      <div class="table">
        <el-table :data="tableData" height="100" style="width: 100%">
          <template #empty>
            <el-button type="success" icon="el-icon-edit" @click="handleAdd" style="margin: 20px">
              添加家庭成员
            </el-button>
          </template>
          <el-table-column align="center" prop="date" label="序号" width="80"/>
          <el-table-column align="center" prop="name" label="家庭关系" width="100"/>
          <el-table-column align="center" prop="name" label="姓名" width="180"/>
          <el-table-column align="center" prop="name" label="性别" width="80"/>
          <el-table-column align="center" prop="name" label="手机号码"/>
          <el-table-column align="center" prop="name" label="出生日期"/>
          <el-table-column align="center" prop="name" label="创建时间"/>
          <el-table-column align="center" prop="address" label="操作"/>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "patientInfo",
  dicts: ['sys_user_sex'],
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
      tableData: []
    }
  },
  created() {
    const patientId = this.$route.params && this.$route.params.patientId;
    console.log("patientId = " + patientId);
  },
  methods: {
    handleAdd() {
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

.base_info > .form,
.association > .table {
  padding: 0 60px;
}

/deep/ .el-form-item {
  flex: 1;
  margin-right: 30px;
}

.association {
  margin-top: 10px;
}
</style>

