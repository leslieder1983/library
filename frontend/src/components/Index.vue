<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <Header line-height="60px"></Header>
      </el-header>
      <el-container>
        <el-aside width="200px" class="aside">
          <Sidebar></Sidebar>
        </el-aside>
        <el-container>
          <el-main>
            <router-view />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import Header from "@/layout/Header.vue";
import Sidebar from "@/layout/Sidebar.vue";
export default {
  components: { Header, Sidebar },
  created() {},
  data() {
    return {};
  },
  props: {},
  methods: {},
};
</script>
<style lang="scss" scoped>
.common-layout {
  height: 100%;
  .el-header,
  .el-footer {
    background-color: rgba(#409eff, 0.8);
    color: var(--el-text-color-primary);
    text-align: center;
    line-height: 60px;
  }

  .el-footer {
    line-height: 60px;
  }

  .el-aside {
    position: fixed;
    top: 60px;
    height: calc(100vh - 60px);
    z-index: 10;
    background-color: #4b4747;
    // background-color: #545c64;
    color: var(--el-text-color-primary);
    text-align: center;
    overflow: hidden;
  }

  .el-main {
    background-color: #e9eef3;
    color: var(--el-text-color-primary);

    // line-height: 160px;
    height: calc(100vh - 60px);
    margin-left: 200px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
}
</style>
