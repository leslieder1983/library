<template>
  <div>
    <el-card>
      <template #header>
        <el-page-header
          title="返回"
          content="教材订购"
          @back="this.$router.back()"
          class="pageHeader"
        />
      </template>
      <el-form :model="formRef" label-width="120px">
        <el-form-item label="教材名称">
          <el-select
            v-model="formRef.bookId"
            filterable
            placeholder="输入/选择教材"
            @change="findMax"
          >
            <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookName"
              :value="item.id"
            >
            </el-option> </el-select
        ></el-form-item>
        <el-form-item label="教材数量">
          <el-input-number v-model="formRef.count" :min="1" @change="getSum" :max="max" />
        </el-form-item>
        <el-form-item label="总价">
          <el-input-number v-model="formRef.sum" :disabled="true" />
        </el-form-item>
        <el-form-item label="订购人">
          <el-input
            v-model="formRef.agent"
            disabled
            placeholder="订购人"
            style="width: 20%"
          />
        </el-form-item>
        <el-form-item label="发起订购">
          <el-button type="primary" @click="order">订购</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { detail, all } from "../api/book";
import { ref } from "vue";
import { add } from "../api/order.js";
import { ElMessageBox } from "element-plus";
export default {
  name: "Subscribe",
  created() {
    all().then((res) => {
      this.books = res.data.data;
    });
    this.formRef.agent = this.$store.getters.getUser?.username;
  },
  data() {
    return {
      max: 100,
      agent: "",
      book: "",
    };
  },
  setup() {
    const books = ref([]);
    const formRef = ref({
      bookId: "",
      bookname: "",
      agent: "",
      sum: "",
      count: 0,
    });

    return {
      books,
      formRef,
    };
  },
  props: {},
  methods: {
    getSum() {
      this.formRef.sum = this.book.bookPrice * this.formRef.count;
    },
    findMax(id) {
      //最大提交订单数
      detail(id).then((res) => {
        const data = res.data.data;
        this.book = data;
        this.formRef.bookname = data.bookName;
        this.max = res.data.data.inventory;
      });
    },
    order() {
      add(this.formRef).then((res) => {
        ElMessageBox.confirm("教材采购订单登记成功！跳转还是留在当前页面？", "回应", {
          confirmButtonText: "跳转至订单页面",
          cancelButtonText: "留在当前页面",
        }).then(() => {
          this.$router.push({ name: "Order" });
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
