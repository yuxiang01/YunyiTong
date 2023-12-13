<template>
  <div class="workbench">
    <div class="content">
      <el-form ref="elForm" :model="form" size="medium" label-width="100px" label-position="right">
        <el-row type="flex" justify="start" align="top" :gutter="15">
          <el-form-item label="创建时间" prop="createTime">
            <el-date-picker type="daterange" v-model="form.createTime" format="yyyy-MM-dd"
                            value-format="yyyy-MM-dd" :style="{width: '100%'}"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            unlink-panels
                            :picker-options="pickerOptions"
                            range-separator="至" clearable/>
          </el-form-item>
          <el-form-item label="就诊状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择就诊状态" clearable>
              <el-option
                v-for="dict in dict.type.sys_visit_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="patient">
            <el-input placeholder="请输入患者姓名" v-model="form.patient" class="input-with-select">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <div class="cart_list">
        <div v-for="item in 20" :key="item" class="cart">
          <div class="cart_content flex-center">
            <div class="left">
              <img src="@/assets/images/man.png" alt="man"/>
            </div>
            <div class="right" style="flex: 80%">
              <p class="right-col-1 flex-center">
                <strong style="color: #000">李进</strong>
                <span v-html="'男 &nbsp;&nbsp;&nbsp;25'"></span>
                <span class="tag">待接诊</span>
              </p>
              <p>创建时间：2019-11-19 22:22:23</p>
              <p>
                <span>科室：骨科</span>
                <span style="margin-left: 30px">医生：王冕</span>
              </p>
              <p>手机号码：18879857899</p>
            </div>
          </div>
          <div class="cart_footer">
            <div class="left strong"><strong>接诊</strong></div>
            <div class="right">查看患者信息</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  dicts: ['sys_visit_status'],
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime())
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '昨天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 3)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }
          , {
            text: '最近两周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 14);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }
        ]
      },
      form: {
        createTime: null,
        status: undefined,
        patient: undefined,
      },
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>

<style scoped>
.workbench {
  height: 100%;
  padding: 10px 20px;
  font-size: 14px;
  color: #999;
  background-color: #F2F2F2;
}

.content {
  height: 100%;
  padding: 20px;
  background-color: #FEFEFE;
  border-radius: .4rem;
}

.cart_list {
  padding: 10px 0 120px 0;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.cart {
  margin-bottom: 20px;
  width: 325px;
  background-color: #fff;
  border: 1px solid #fcfcfc;
  border-radius: .6rem;
  box-shadow: 0 0 30px rgba(242, 242, 242, 1);
}

.cart_content {
  justify-content: space-around;
  padding: 10px;
}

.cart_content p {
  margin: 8px 0;
  height: 20px;
}

.cart_content .left {
  flex: 20%;
  padding: 0 10px;
}

.cart_content .right-col-1 {
  justify-content: space-between;
  align-items: center
}

.tag {
  padding: 5px 8px;
  border-radius: 1rem;
  background-color: rgba(255, 98, 98, 1);
  color: #FFF;
  font-size: 12px;
}

.cart_footer {
  display: flex;
  height: 50px;
  line-height: 50px;
  cursor: pointer;
  border-top: 1px solid #DEDEDE;
}

.cart_footer > .left,
.cart_footer > .right {
  flex: 50%;
  text-align: center;
}

.cart_footer > .left {
  border-right: 1px solid #DEDEDE;
}

.cart_footer > .left:hover,
.cart_footer > .right:hover {
  color: #FFFFFF;
  background-color: rgba(102, 110, 232, 0.8);
}

.cart_footer > .left:hover {
  border-radius: 0 0 0 .6rem;
}

.cart_footer > .right:hover {
  border-radius: 0 0 .6rem 0;
}

.strong {
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 14px;
  color: rgba(102, 110, 232, 0.8);
}
</style>




