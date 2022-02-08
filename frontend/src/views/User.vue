<template>
  <div>
    <el-space wrap direction="vertical" style="display: flex">
      <el-card class="box-card">
        <el-tabs type="border-card">
          <el-tab-pane label="基本资料">
            <el-descriptions style="margin-bottom: 20px" :column="2" :size="size" border>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon style="margin-right: 6px">
                      <user />
                    </el-icon>
                    用户名
                  </div>
                </template>
                {{ user.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon style="margin-right: 6px">
                      <location />
                    </el-icon>
                    性别
                  </div>
                </template>
                {{ user.sexy == 1 ? "男" : "女" }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon style="margin-right: 6px">
                      <iphone />
                    </el-icon>
                    电话号码
                  </div>
                </template>
                {{ user.phone }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon style="margin-right: 6px">
                      <location />
                    </el-icon>
                    邮箱地址
                  </div>
                </template>
                {{ user.email }}
              </el-descriptions-item>

              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon style="margin-right: 6px">
                      <office-building />
                    </el-icon>
                    个人简介
                  </div>
                </template>
                {{ user.description }}
              </el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>
          <el-tab-pane label="修改信息">
            <el-form ref="ruleFormRef" label-width="120px">
              <el-form-item label="用户名">
                <el-input v-model="ruleForm.username"></el-input>
              </el-form-item>

              <el-form-item label="性别">
                <el-radio v-model="ruleForm.sexy" label="1" size="large">男</el-radio>
                <el-radio v-model="ruleForm.sexy" label="0" size="large">女</el-radio>
              </el-form-item>
              <el-form-item label="电话号码">
                <el-input v-model="ruleForm.phone"></el-input>
              </el-form-item>
              <el-form-item label="个人简介">
                <el-input v-model="ruleForm.description" type="textarea"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="updateData">修改</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="修改密码">
            <el-form
              :model="passwordForm"
              ref="passwordRef"
              :rules="rules"
              label-width="120px"
            >
              <el-form-item label="旧密码" required>
                <el-input
                  type="password"
                  show-password
                  v-model="passwordForm.oldPassword"
                ></el-input>
              </el-form-item>
              <el-form-item label="新密码" required>
                <el-input
                  type="password"
                  show-password
                  v-model="passwordForm.newPassword"
                ></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="confirm">
                <el-input
                  type="password"
                  show-password
                  v-model="passwordForm.confirmPassword"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="setPassword(passwordRef)"
                  >修改密码</el-button
                >
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </el-space>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { detail, update, updatePassword } from "../api/user";
import { ElMessage } from "element-plus";

export default {
  name: "Users",
  created() {
    const id = this.$store.getters.getUser.id;
    this.id = id;
    this.list();
    detail(id).then((res) => {
      //修改信息的表单赋值
      const data = res.data.data;
      //更新操作需要传入id
      this.ruleForm.id = data.id;
      this.ruleForm.username = data.username;
      this.ruleForm.sexy = data.sexy;
      this.ruleForm.phone = data.phone;
      this.ruleForm.description = data.description;
    });
  },
  data() {
    return {
      size: "default",
      activeName: "",
      id: "",
    };
  },
  setup() {
    const ruleFormRef = ref();
    const passwordRef = ref();
    const ruleForm = reactive({
      username: "",
      phone: "",
      sex: 0,
      description: "",
    });
    const passwordForm = reactive({
      username: "",
      oldPassword: "",
      newPassword: "",
      confirmPassword: "",
    });
    const validatePass = (rule, value, callback) => {
      const confirm = passwordForm.confirmPassword;
      if (confirm === "") {
        callback(new Error("Please input the password again"));
      } else if (confirm !== passwordForm.newPassword) {
        callback(new Error("两次输入的密码不一致!"));
      } else {
        callback();
      }
    };
    const rules = reactive({
      password: [
        {
          min: 6,
          message: "密码最少六位",
          trigger: "blur",
        },
      ],
      confirm: [{ validator: validatePass, trigger: "blur" }],
    });
    const user = ref({});
    return {
      ruleFormRef,
      ruleForm,
      rules,
      passwordForm,
      passwordRef,
      user,
    };
  },
  props: {},
  methods: {
    list() {
      //更新个人信息
      detail(this.id)
        .then((res) => {
          const data = res.data.data;
          this.user.username = data.username;
          this.user.sexy = data.sexy;
          this.user.phone = data.phone;
          this.user.description = data.description;
          this.user.created = data.created;
          this.user.email = data.email;
          // this.user = res.data.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    updateData() {
      //更新数据
      update(this.ruleForm)
        .then(() => {
          ElMessage({
            type: "success",
            message: "修改成功！",
          });
          this.list();
        })
        .catch((e) => {
          console.log(e);
        });
    },
    setPassword(ref) {
      //修改密码
      ref.validate((valid) => {
        if (valid) {
          updatePassword(
            this.id,
            this.passwordForm.oldPassword,
            this.passwordForm.newPassword
          ).then((res) => {
            ElMessage({
              type: "success",
              message: "密码修改成功！",
            });
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.user {
  display: flex;
  justify-content: space-between;
}
.box-card {
  flex: 1;
}
.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}
</style>
