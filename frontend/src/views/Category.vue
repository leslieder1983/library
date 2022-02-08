<template>
  <div>
    <el-page-header
      title="返回"
      content="教材分类管理"
      @back="this.$router.back()"
      class="pageHeader"
    />
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
    <el-divider></el-divider>
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
      <el-table-column property="classes" label="教材分类名称" align="center">
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
            title="确定删除当前教材分类数据?"
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
          layout=" total,prev,pager, next, jumper,->"
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
      :title="editIndex !== '' ? '编辑信息' : '新增教材分类'"
      @close="dialog = {}"
    >
      <el-form :model="dialog" label-width="120px">
        <el-form-item label="教材类型名称" required>
          <el-input v-model="dialog.classes" placeholder="教材类型名称"></el-input>
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
import { list, update, add, del, deleteAll } from "../api/category";
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
      classes: "",
    });
    const multipleTableRef = ref();
    return {
      formInline,
      multipleTableRef,
      detail,
    };
  },

  methods: {
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
      this.formInline.classes = "";
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
