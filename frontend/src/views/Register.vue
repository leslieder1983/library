<template>
  <div class="login">
    <el-form
      ref="registerRef"
      :rules="registerRules"
      :model="RegisterForm"
      class="login-form"
      label-width="80px"
      status-icon
    >
      <h2 class="title">账号注册</h2>
      <el-divider style="border-color: cadetblue"></el-divider>

      <el-form-item prop="username" label="用户名">
        <el-input
          v-model="RegisterForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input
          v-model="RegisterForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter="handleRegister"
          show-password
        >
        </el-input>
      </el-form-item>

      <el-form-item prop="confirmPassword" label="确认密码">
        <el-input
          v-model="RegisterForm.confirmPassword"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter="handleRegister"
          show-password
        >
        </el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          style="width: 100%"
          @click.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right">
          <router-link class="link-type" :to="'/login'">回到登录</router-link>
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
import { useRouter } from "vue-router";
import { ElMessageBox } from "element-plus";
import { add } from "../api/user";
export default {
  setup() {
    const router = useRouter();
    const { proxy } = getCurrentInstance();
    const registerRef = ref();
    const RegisterForm = ref({
      username: "",
      password: "",
      confirmPassword: "",
      code: "",
      uuid: "",
    });
    const confirmPasswords = (rule, value, callback) => {
      if (RegisterForm.value.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    const registerRules = {
      username: [
        { required: true, trigger: "blur", message: "请输入您的账号" },
        {
          min: 2,
          max: 20,
          message: "用户账号长度必须介于 2 和 20 之间",
          trigger: "blur",
        },
      ],
      password: [
        { required: true, trigger: "blur", message: "请输入您的密码" },
        {
          min: 3,
          message: "用户密码长度必须大于3",
          trigger: "blur",
        },
      ],
      confirmPassword: [
        { required: true, trigger: "blur", message: "请确认您的密码" },
        { required: true, validator: confirmPasswords, trigger: "blur" },
      ],
    };

    const loading = ref(false);
    function handleRegister() {
      proxy.$refs.registerRef.validate((valid) => {
        if (valid) {
          loading.value = true;
          add(RegisterForm.value)
            .then((res) => {
              const username = RegisterForm.value.username;
              ElMessageBox.alert(
                "<font color='green'>恭喜你，您的账号 " + username + " 注册成功！</font>",
                "注册成功",
                {
                  dangerouslyUseHTMLString: true,
                  type: "success",
                }
              )
                .then(() => {
                  router.push("/login");
                })
                .catch(() => {});
            })
            .catch(() => {
              loading.value = false;
              ElMessageBox.confirm("注册失败. 是否继续?", "警告", {
                confirmButtonText: "继续注册",
                cancelButtonText: "跳转登录",
                type: "warning",
              })
                .then(() => {
                  router.push("/register");
                })
                .catch(() => {
                  router.push("/login");
                  ElMessage({
                    type: "info",
                    message: "跳转成功",
                  });
                });
            });
        }
      });
    }
    return {
      registerRef,
      registerRules,
      handleRegister,
      confirmPasswords,
      RegisterForm,
      loading,
    };
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
