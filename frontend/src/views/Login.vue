<template>
  <div class="login">
    <el-form
      ref="loginRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      label-width="70px"
      status-icon
    >
      <h2 class="title">高校教材订购管理系统</h2>
      <el-divider style="border-color: cadetblue"></el-divider>
      <el-form-item prop="username" label="用户名">
        <el-input
          v-model="loginForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input
          v-model="loginForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter="handleLogin"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="" style="position: relative">
        <el-checkbox
          v-model="loginForm.rememberMe"
          label="记住密码"
          size="large"
        ></el-checkbox>
        <router-link style="position: absolute; right: 0px" :to="'/register'"
          >注册账号</router-link
        >
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          style="width: 100%"
          @click.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 </span>
    </div>
  </div>
  >
</template>

<script>
import { ref, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { login } from "../api/user";
import { ElMessage } from "element-plus";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const { proxy } = getCurrentInstance();
    const loginRef = ref();
    const loginForm = ref({
      username: localStorage.getItem("username"),
      password: localStorage.getItem("password"),
      rememberMe: true,
      code: "",
    });

    const loginRules = {
      username: [{ required: true, trigger: "blur", message: "请输入正确的账号" }],
      password: [{ required: true, trigger: "blur", message: "请输入正确的密码" }],
    };
    const loading = ref(false);
    // 注册开关
    const register = ref(false);

    function handleLogin() {
      proxy.$refs.loginRef.validate((valid) => {
        if (valid) {
          loading.value = true;
          // 勾选了需要记住密码设置在cookie中设置记住用户明和名命
          if (loginForm.value.rememberMe) {
            store.commit("remember", loginForm.value);
          } else {
            // 否则移除
            store.commit("unRemember");
          }

          login(loginForm.value)
            .then((res) => {
              const jwt = res.headers["authorization"];
              const userInfo = res.data.data;
              store.commit("SET_Token", jwt);
              store.commit("SET_UserInfo", userInfo);
              console.log(userInfo);
              ElMessage({
                message: "登录成功",
                type: "success",
              });
              router.push({ path: "/main/library" });
            })
            .catch((e) => {
              loading.value = false;
              ElMessage({
                message: e,
                type: "warning",
              });
            });
        }
      });
    }
    return { loginForm, loginRules, handleLogin, loginRef, loading, register };
  },
};
</script>

<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: black;
  opacity: 0.9;
}

.login-form {
  border-radius: 10px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 40px;
    input {
      height: 40px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
