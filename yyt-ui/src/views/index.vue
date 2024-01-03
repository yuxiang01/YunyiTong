<template>
  <div class="workbench">
    <div class="content">
      <el-form ref="elForm" :model="form" size="medium" label-width="100px" label-position="right">
        <el-row type="flex" justify="start" align="top" :gutter="15">
          <el-form-item label="创建时间" prop="queryTime">
            <el-date-picker
              v-model="form.queryTime"
              type="datetimerange"
              :style="{width: '100%'}"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              unlink-panel
              :picker-options="pickerOptions"
              :default-time="['00:00:00', '23:59:59']" clearable>
            </el-date-picker>
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
            <el-input placeholder="请输入患者姓名" v-model="form.patient" class="input-with-select" clearable>
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <div class="list">
        <div class="cart_list">
          <div v-for="item in regorderList" :key="item.regId" class="cart">
            <div class="cart_content flex-center">
              <div class="left">
                <img v-if="item.sex === '0'" src="@/assets/images/man.png" alt="man"/>
                <img v-else src="@/assets/images/woman.png" alt="woman"/>
              </div>
              <div class="right" style="flex: 80%">
                <p class="right-col-1 flex-center">
                  <strong style="color: #000">{{ item.patient }}</strong>
                  <span v-html="`${item.sex === '0' ? '男':'女'} &nbsp;&nbsp;&nbsp;${item.age}`">
                </span>
                  <span :class="'tag tag-'+item.status">
                  {{ dictFormat(item.status) }}
                </span>
                </p>
                <p>创建时间：{{ item.createTime }}</p>
                <p>
                  <span>科室：{{ item.deptName }}</span>
                  <span style="margin-left: 30px">医生：{{ item.doctor }}</span>
                </p>
                <p>手机号码：{{ item.phone }}</p>
              </div>
            </div>
            <div class="cart_footer">
              <div class="left strong" @click="receive(item.regId)" v-if="item.status < 2">
                <strong>接诊</strong>
              </div>
              <div @click="queryInfo(item.patientId)" :class="'right '+(item.status > 1 ? 'right-row' : '')">
                查看患者信息
              </div>
            </div>
          </div>
        </div>
      </div>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="form.pageNum"
        :limit.sync="form.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>
<script>
import {listRegorder} from "@/api/os/regorder";
import {calculateTime, getEncode} from "@/utils/web-utils";

export default {
  props: [],
  dicts: ['sys_visit_status'],
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', calculateTime(0))
            }
          },
          {
            text: '昨天',
            onClick(picker) {
              picker.$emit('pick', calculateTime(1, 1))
            }
          },
          {
            text: '最近三天',
            onClick(picker) {
              picker.$emit('pick', calculateTime(2))
            }
          },
          {
            text: '最近一周',
            onClick(picker) {
              picker.$emit('pick', calculateTime(0, 0, 'week'));
            }
          },
          {
            text: '最近两周',
            onClick(picker) {
              picker.$emit('pick', calculateTime(1, 0, 'week'));
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              picker.$emit('pick', calculateTime(0, 0, 'month'));
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              picker.$emit('pick', calculateTime(2, 0, 'month'));
            }
          }
        ]
      },
      form: {
        pageNum: 1,
        pageSize: 10,
        queryTime: null,
        status: undefined,
        patient: undefined,
      },
      // 总条数
      total: 0,
      // 挂号订单表格数据
      regorderList: [],
    }
  },
  created() {
    this.getList()
  },
  watch: {
    form: {
      handler(newVal, oldVal) {
        this.getList()
      },
      // 可以深度检测到 person 对象的属性值的变化
      deep: true
    }
  },
  methods: {
    getList() {
      let doctorId = this.$store.getters.doctorId;
      if (doctorId && doctorId !== '' && doctorId !== null) {
        this.form.doctorId = doctorId
      }
      listRegorder(this.form).then(response => {
        this.regorderList = response.rows;
        this.total = response.total;
      });
    },
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.getList()
      })
    },
    dictFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_visit_status, row);
    },
    receive(regId) {
      this.$router.push("/os/consultation/" + getEncode(regId));
    },
    queryInfo(patientId) {
      this.$router.push("/os/patient-info/" + getEncode(patientId));
    }
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

.list {
  padding: 5px 10px 10px 10px;
  overflow-y: auto;
  height: 75%;
  overflow-x: hidden;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.list::-webkit-scrollbar {
  display: none;
}

.cart_list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
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

.cart_footer > .right-row:hover {
  border-radius: 0 0 .6rem .6rem;
}

.strong {
  font-family: "思源黑体 CN Bold", "思源黑体 CN Regular", "思源黑体 CN";
  font-weight: 700;
  font-style: normal;
  font-size: 14px;
  color: rgba(102, 110, 232, 0.8);
}

/deep/ .el-form-item {
  margin-bottom: 10px;
}
</style>




