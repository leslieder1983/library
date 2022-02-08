<template>
  <div>
    <div class="content">
      <el-page-header
        title="返回"
        content="缺书登记"
        @back="this.$router.back()"
        class="pageHeader"
      />
      <el-divider></el-divider>
      <el-form
        ref="ruleFormRef"
        :model="blog"
        :rules="rules"
        label-width="120px"
        :size="formSize"
      >
        <el-form-item label="标题" style="width: 70%" prop="title">
          <el-input v-model="blog.title"></el-input>
        </el-form-item>

        <el-form-item label="详情" style="width: 70%" prop="desription">
          <el-input v-model="blog.desription" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="选择缺书书名" style="width: 70%" prop="desription">
          <el-select v-model="blog.content" placeholder="Select">
            <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookName"
              :value="item.bookName"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="publish(ruleFormRef)">
            <span v-if="this.blog.userId !== undefined">修改</span>
            <span v-else> 登记</span>
          </el-button>
          <el-button @click="blog = {}">清空</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from "element-plus";
import { reactive, ref } from "vue";
import { detail, edit } from "../api/blog";
import { all } from "../api/book";
export default {
  name: "BlogDetail",

  created() {
    all().then((res) => {
      this.books = res.data.data;
    });
    const blogId = this.$route.params.blogId;
    if (blogId) {
      detail(blogId).then((res) => {
        const blog = res.data.data;
        this.blog.id = blog.id;
        this.blog.title = blog.title;
        this.blog.desription = blog.desription;
        this.blog.content = blog.content;
        this.blog.userId = this.$store.getters.getUser?.id;
      });
    }
  },
  data() {
    return {
      books: [],
    };
  },
  setup() {
    const ruleFormRef = ref();
    const blog = reactive({
      title: "",
      content: "",
      desription: "",
      userId: undefined,
    });
    const rules = reactive({
      title: [
        {
          required: true,
          message: "请输入标题",
          trigger: "blur",
        },
        {
          min: 2,
          message: "长度应大于3",
          trigger: "blur",
        },
      ],

      desription: [
        {
          required: true,
          message: "详情不能为空",
          trigger: "blur",
        },
      ],
    });
    return {
      blog,
      rules,
      ruleFormRef,
    };
  },
  props: {},
  methods: {
    publish(submitForm) {
      submitForm.validate((valid) => {
        if (valid) {
          if (this.blog.userId === undefined) {
            this.blog.userId = this.$store.getters.getUser?.id;
          }
          edit(this.blog)
            .then((res) => {
              console.log(res);
              ElMessageBox.confirm("信息发表成功,跳转还是留在当前页面？", "回应", {
                confirmButtonText: "跳转至消息列表",
                cancelButtonText: "留在当前页面",
              }).then(() => {
                this.$router.push({ name: "Messages" });
              });
            })
            .catch((e) => {
              console.log(e);
            });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.content {
  text-align: center;
}

.pageHeader {
  font-size: large;
  font-weight: 600;
}
</style>
