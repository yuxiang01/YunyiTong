<script>
import PatentSelected from "@/components/PatentSelected/index.vue";
import {commonDictFormat} from "@/utils/web-utils";

export default {
  name: "patientBaseInfo",
  methods: {commonDictFormat},
  components: {PatentSelected},
  dicts: ['sys_user_sex'],
  props: {
    form: {
      name: String,
      sex: String,
      age: Number,
      phone: String,
      card: String,
      address: String,
      detailsAddress: String,
      remark: String,
    }
  }
}
</script>

<template>
  <el-form ref="elForm" :model="form" size="medium" label-width="150px" label-position="top">
    <el-row type="flex" justify="left" align="top" :gutter="15">
      <el-form-item label="患者姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入患者姓名" :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="患者性别" prop="sex">
        <el-input :value="commonDictFormat('sys_user_sex',form.sex)" placeholder="请输入患者姓名"
                  :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="患者年龄" prop="age">
        <el-input v-model="form.age" placeholder="请输入患者年龄" :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="证件号码" prop="card">
        <el-input v-model="form.card" placeholder="请输入证件号码" :maxlength="18"
                  :style="{width: '100%'}" readonly/>
      </el-form-item>
    </el-row>
    <el-row type="flex" justify="left" align="top" :gutter="15">
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号码" :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="请输入地址" :maxlength="18"
                  :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="详细地址" prop="detailsAddress">
        <el-input v-model="form.detailsAddress" placeholder="请输入详细地址" :maxlength="18"
                  :style="{width: '100%'}" readonly/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入备注" :maxlength="18"
                  :style="{width: '100%'}" readonly/>
      </el-form-item>
    </el-row>
  </el-form>
</template>

<style scoped>
/deep/ .el-form-item {
  flex: 1;
  margin-right: 30px;
  margin-bottom: 5px;
}
</style>
