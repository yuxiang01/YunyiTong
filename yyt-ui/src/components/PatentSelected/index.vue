<template>
  <div class="patient">
    <div class="left">
      <img v-if="item.sex === '0'" src="@/assets/images/man.png" alt="man"/>
      <img v-else src="@/assets/images/woman.png" alt="woman"/>
    </div>
    <div class="info">
      <span style="font-weight: 700;color:#000;">{{ item.name }}</span>
      <span>{{ dictFormat(item.sex) }}</span>
      <span>{{ item.age }}岁</span>
      <span>{{ item.phone }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "PatentSelected",
  dicts: ['sys_user_sex'],
  props: {
    item: {
      name: String,
      sex: String,
      age: Number,
      phone: String
    },
  },
  methods: {
    dictFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_user_sex, row);
    },
  }
}
</script>

<style scoped>
.patient {
  height: 70px;
  display: flex;
}

.info span {
  padding-left: 10px;
  font-family: '思源黑体';
  font-weight: 400;
  font-size: 12px;
  color: #999999;
}
</style>
