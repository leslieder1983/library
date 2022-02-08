<template>
  <div class="header">
    <h1>高校教材订购管理系统</h1>
    <div class="user">
      <el-dropdown @command="handleCommand" ref="dropdown">
        <el-button type="info">
          {{ $store.getters.getUser?.username }}
          <el-icon>
            <arrow-down />
          </el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="user">个人中心</el-dropdown-item>
            <el-divider></el-divider>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { ElMessageBox } from "element-plus";
export default {
  name: "Test",
  created() {
    this.user = this.$store.getters.getUser;
  },
  data() {
    return {
      user: {
        username: "",
      },
    };
  },
  setup() {
    const dropdown = ref();
    return {
      dropdown,
    };
  },
  methods: {
    handleCommand(command) {
      //处理下拉菜单
      switch (command) {
        case "user":
          this.$router.push({
            name: "User",
          });
          break;
        case "logout":
          ElMessageBox.alert("确定要退出登录？", "确认", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.$store.commit("removeInfo");
              this.$router.push({
                name: "Login",
              });
            })
            .catch(() => {});

          break;
        default:
          break;
      }
    },
    // open() {
    //   console.log(this.$refs);
    //   this.$refs.dropdown.handleEntryFocus();
    // },
  },
};
</script>

<style lang="scss" scoped>
.header {
  display: inline-flex;
  text-align: center;
  position: relative;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 60px;
  width: 100%;
  overflow: hidden;
  .user {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-25%);
  }
  h1 {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
  }
}
</style>
