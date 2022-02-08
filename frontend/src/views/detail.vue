<template>
  <div>
    <el-page-header
      title="返回"
      content="用户管理"
      @back="this.$router.back()"
      class="pageHeader"
    />
    <el-divider></el-divider>
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="用户名称">
        <el-input v-model="formInline.username" placeholder="用户名称"></el-input>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="formInline.phone" placeholder="手机号码"> </el-input>
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
      <el-col :span="8"
        ><el-button type="primary" @click="add"
          >新增
          <el-icon><plus /></el-icon>
        </el-button>
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
      <el-table-column property="username" label="用户名称" align="center">
      </el-table-column>
      <el-table-column property="sexy" label="性别" align="center">
        <template #default="scope">
          <span v-if="scope.row.sexy == '0'">
            <el-tag type="success">女</el-tag>
          </span>
          <span v-else>
            <el-tag type="success">男</el-tag>
          </span>
        </template>
      </el-table-column>
      <el-table-column property="phone" label="手机号码" />
      <el-table-column property="email" label="邮箱地址" />
      <el-table-column property="created" label="创建时间" show-overflow-tooltip>
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-icon><timer /></el-icon>
            <span style="margin-left: 10px">{{
              scope.row.created?.replace("T", " ")
            }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button @click="handleEdit(scope.$index, scope)" type="primary"
            >编辑</el-button
          >
          <el-popconfirm
            confirm-button-text="是的"
            cancel-button-text="不"
            icon-color="red"
            title="确定删除当前用户数据?"
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

    <!-- 对话框-->
    <el-dialog
      v-model="dialogFormVisible"
      :title="editIndex !== '' ? '编辑信息' : '新增用户'"
      @close="dialog = {}"
    >
      <el-form :model="dialog" label-width="120px">
        <el-form-item label="用户名称" required>
          <el-input v-model="dialog.username" placeholder="用户名称"></el-input>
        </el-form-item>
        <el-form-item v-if="editIndex === ''" label="密码" required>
          <el-input v-model="dialog.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" required>
          <el-radio-group v-model="dialog.sexy">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="dialog.phone" placeholder="电话号码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址">
          <el-input type="email" v-model="dialog.email" placeholder="邮箱地址"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogConfirm">
            <span v-if="editIndex !== ''">修改</span>
            <span v-else>添加</span></el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref } from "vue";
import { list, update, add, del, deleteAll, query } from "../api/user";
import { ElMessage } from "element-plus";
export default {
  name: "detail",
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
      pageSize: 5,
      total: 10,
    };
  },
  setup() {
    const detail = ref();

    const formInline = ref({
      username: "",
      phone: "",
      deptId: "",
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
          this.detail = res.data.data;
          ElMessage({
            message: "查询成功",
            type: "success",
          });
        })
        .then((e) => {
          console.log(e);
        });
    },
    handleEdit(index, scope) {
      this.editIndex = index;
      this.dialog = JSON.parse(JSON.stringify(scope.row));
      this.dialogFormVisible = true;
    },
    handleClose() {},

    handleDelete(id) {
      //删除单个数据
      del(id)
        .then((res) => {
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
    //对话框，新增或者编辑
    dialogConfirm() {
      if (this.editIndex !== "") {
        console.log("user1", this.dialog);
        update(this.dialog)
          .then(() => {
            this.detail[this.editIndex] = JSON.parse(JSON.stringify(this.dialog));
            this.editIndex = "";
            this.dialogFormVisible = false;
            ElMessage({
              message: "修改成功",
              type: "success",
            });
          })
          .catch((e) => {
            ElMessage({
              message: "修改失败！" + e,
              type: "warning",
            });
          });
      } else {
        //新增
        add(this.dialog)
          .then(() => {
            this.handleChange();
            this.editIndex = "";
            this.dialogFormVisible = false;
            ElMessage({
              message: "添加成功",
              type: "success",
            });
          })
          .catch((e) => {
            ElMessage({
              message: "添加失败！" + e,
              type: "warning",
            });
          });
      }
    },
    add() {
      this.dialogFormVisible = true;
    },
    clearAll() {
      //清除所有查询
      this.formInline.phone = "";
      this.formInline.username = "";
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
