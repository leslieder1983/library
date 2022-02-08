<template>
  <div>
    <div class="block">
      <el-page-header
        title="返回"
        content="通知公告"
        @back="this.$router.back()"
        class="pageHeader"
      />
      <el-divider></el-divider>
      <el-table :data="blogs" stripe style="width: 100%" :highlight-current-row="true">
        <el-table-column label="标题" align="center">
          <template #default="scope">{{ scope.row.title }} </template>
        </el-table-column>
        <el-table-column label="详情" prop="desription" align="center"></el-table-column>
        <el-table-column label="所缺教材名称" prop="content" align="center">
          <template #default="scope">
            <el-tag type="warning">{{ scope.row.content }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="登记时间">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <el-icon><timer /></el-icon>
              <span style="margin-left: 10px">{{
                scope.row.created?.replace("T", " ")
              }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column width="120">
          <template #default="scope">
            <el-button size="large" type="danger" @click="handleRead(scope.row.id)"
              >标为已读</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-divider></el-divider>
    <el-row :gutter="20">
      <el-col :span="4"></el-col>
      <el-col :span="16" style="text-align: center"
        ><el-pagination
          background
          layout="prev,pager,  next, jumper,->,  total"
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
import { list, del } from "../api/blog";
import { ElMessage } from "element-plus";
export default {
  name: "Blogs",
  created() {
    this.handleChange();
  },
  setup() {
    const blogs = ref();
    return {
      blogs,
    };
  },
  data() {
    return {
      state: {
        unread: [],
      },
      currentPage: 1,
      pageSize: 4,
      total: 10,
    };
  },
  props: {},
  methods: {
    handleRead(id) {
      del(id).then(() => {
        ElMessage({
          type: "success",
          message: "成功标记为已读！",
        });
        this.handleChange();
      });
    },
    toDetail(item) {
      //跳转至详情
      this.$router.push({
        name: "Content",
        params: {
          blogId: item.id,
        },
      });
    },
    handleEdit(id) {
      //跳转至修改界面
      this.$router.push({
        name: "Editor",
        params: {
          blogId: id,
        },
      });
    },
    handleDelete(id) {
      //删除单个数据
      del(id).then((res) => {
        ElMessage({
          message: "删除成功",
          type: "success",
        });
        this.handleChange();
      });
    },
    //分页处理
    handleChange() {
      list(this.currentPage, this.pageSize).then((res) => {
        const data = res.data.data;
        this.total = data.total;
        this.currentPage = data.current;
        this.pageSize = data.size;
        this.blogs = data.records;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.block {
  text-align: center;
}
.card {
  text-align: center;
  cursor: pointer;
}
.card::active {
  background-color: aquamarine;
}
</style>
