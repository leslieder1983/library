<template>
  <div>
    <el-menu
      active-text-color="#409EFF"
      background-color="#4b4747"
      text-color="#fff"
      default-active="2"
      :router="true"
    >
      <el-menu-item index="/main/detail">
        <el-icon><menu /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="/main/library">
        <el-icon><menu /></el-icon>
        <span>教材管理</span>
      </el-menu-item>
      <el-menu-item index="/main/category">
        <el-icon><menu /></el-icon>
        <span>教材分类管理</span>
      </el-menu-item>
      <el-menu-item index="/main/subscribe">
        <el-icon><menu /></el-icon>
        <span>教材订购</span>
      </el-menu-item>
      <el-menu-item index="/main/orders">
        <el-icon><menu /></el-icon>
        <span>订单查询</span>
      </el-menu-item>
      <el-menu-item index="/main/edit">
        <el-icon><menu /></el-icon>
        <span>缺书登记</span>
      </el-menu-item>
      <el-menu-item index="/main/messages">
        <el-icon><menu /></el-icon>
        <span>通知公告</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  created() {},
  data() {
    return {};
  },
  props: {},
  methods: {},
};
</script>

<style lang="scss" scoped></style>
