<template>
  <div>
    <el-page-header
      title="返回"
      content="订单管理"
      @back="this.$router.back()"
      class="pageHeader"
    />
    <el-divider></el-divider>
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="订单名称">
        <el-input v-model="formInline.bookname" placeholder="教材名称"></el-input>
      </el-form-item>
      <el-form-item label="订购人">
        <el-input v-model="formInline.agent" placeholder="订购人"> </el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="query"
          >查询&nbsp;<el-icon><search /></el-icon
        ></el-button>
        <el-button @click="clearAll"
          >清空查询&nbsp; <el-icon><delete /></el-icon
        ></el-button>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <el-row style="margin-bottom: 10px">
      <el-col :span="8">
        <el-popconfirm
          confirm-button-text="是的"
          cancel-button-text="不"
          icon-color="red"
          title="确定删除所有选中数据?"
          @confirm="deleteAll"
        >
          <template #reference>
            <el-button type="danger"
              >删除选中<el-icon><delete /></el-icon
            ></el-button>
          </template>
        </el-popconfirm>
      </el-col>
    </el-row>
    <!-- 表格数据-->
    <el-table
      ref="multipleTableRef"
      :data="detail"
      style="width: 100%; text-align: center"
      stripe
      border
      @selection-change="selectChange"
    >
      <el-table-column type="selection" />
      <el-table-column property="id" label="订单号" align="center"></el-table-column>
      <el-table-column
        property="bookname"
        label="教材名称"
        min-width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column property="count" label="订购数量" align="center" />
      <el-table-column property="sum" label="总价" align="center" />
      <el-table-column property="agent" label="订购人" align="center" />
      <el-table-column
        property="createTime"
        label="创建时间"
        align="center"
        show-overflow-tooltip
      >
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-icon><timer /></el-icon>
            <span style="margin-left: 10px">{{
              scope.row.createTime?.replace("T", " ")
            }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-popconfirm
            confirm-button-text="是的"
            cancel-button-text="不"
            icon-color="red"
            title="确定删除当前订单数据?"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button type="danger"
                >删除<el-icon><delete /></el-icon
              ></el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-divider></el-divider>
    <el-row :gutter="20">
      <el-col :span="4"></el-col>
      <el-col :span="16" style="text-align: center"
        ><el-pagination
          background
          layout=" total,prev,pager,  next, jumper,->"
          :total="total"
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          @size-change="handleChange"
          @current-change="handleChange"
        >
        </el-pagination
      ></el-col>
      <el-col :span="4"></el-col>
    </el-row>
  </div>
</template>

<script>
import { ref } from "vue";
import { list, del, deleteAll, query } from "../api/order";
import { ElMessage } from "element-plus";
export default {
  name: "Order",
  created() {
    this.handleChange();
  },
  data() {
    return {
      dialog: {},
      dialogFormVisible: false,
      editIndex: "",
      deleteMap: [],
      currentPage: 1,
      pageSize: 6,
      total: 10,
    };
  },
  setup() {
    const detail = ref();

    const formInline = ref({
      bookname: "",
      agent: "",
    });
    const multipleTableRef = ref();
    return {
      formInline,
      multipleTableRef,
      detail,
    };
  },

  methods: {
    query() {
      query(this.formInline)
        .then((res) => {
          const data = res.data.data;
          this.detail = data;
          this.total = data.length;
          ElMessage({
            message: "查询成功",
            type: "success",
          });
        })
        .then((e) => {
          console.log(e);
        });
    },

    handleDelete(id) {
      //删除单个数据
      del(id)
        .then(() => {
          ElMessage({
            message: "删除成功",
            type: "success",
          });
          this.handleChange();
        })
        .catch((e) => {
          console.log(e);
        });
    },

    add() {
      this.dialogFormVisible = true;
    },
    clearAll() {
      //清除所有查询
      this.formInline.bookname = "";
      this.formInline.agent = "";
      this.handleChange();
    },
    //删除所有选中
    deleteAll() {
      const ids = [];
      this.deleteMap.forEach((element) => {
        ids.push(element.id);
      });
      //删除所有
      deleteAll(ids)
        .then((res) => {
          ElMessage({
            message: "删除成功",
            type: "success",
          });
          this.$refs.multipleTableRef.clearSelection();
          this.handleChange();
        })
        .then((e) => {
          console.log(e);
        });
    },
    selectChange(row) {
      this.deleteMap = row;
    },
    //分页处理
    handleChange() {
      list(this.currentPage, this.pageSize)
        .then((res) => {
          const data = res.data.data;
          this.total = data.total;
          this.currentPage = data.current;
          this.pageSize = data.size;
          this.detail = data.records;
        })
        .catch((err) => console.log(err));
    },
  },
};
</script>

<style lang="scss" scoped>
.form-inline {
  justify-content: flex-start;
}
.el-table {
  text-align: center;
}
</style>
