<template>
  <div>
    <el-page-header
      title="返回"
      content="教材管理"
      @back="this.$router.back()"
      class="pageHeader"
    />
    <el-divider></el-divider>
    <el-form :inline="true" :model="formInline" class="form-inline">
      <el-form-item label="教材名称">
        <el-input v-model="formInline.bookName" placeholder="教材名称"></el-input>
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="formInline.isbn" placeholder="输入ISBN号查询"> </el-input>
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
      </el-col>
    </el-row>
    <!-- 表格数据-->
    <el-space wrap>
      <el-card v-for="(item, index) in detail" :key="item.isbn" style="width: calc(40vw)">
        <template #header>
          <div class="card-header">
            <el-row :gutter="20">
              <el-col :span="15">
                <h1>{{ item.bookName }}</h1></el-col
              >
              <el-col
                :span="5"
                style="display: flex; flex-direction: row; justify-content: space-between"
              >
                <el-button @click="handleEdit(index, item)" type="primary"
                  >编辑</el-button
                >

                <el-popconfirm
                  confirm-button-text="是的"
                  cancel-button-text="不"
                  icon-color="red"
                  title="确定删除当前教材数据?"
                  @confirm="handleDelete(item.id)"
                >
                  <template #reference>
                    <el-button type="danger"
                      >删除<el-icon><delete /></el-icon
                    ></el-button>
                  </template> </el-popconfirm
              ></el-col>
            </el-row>
          </div>
        </template>
        <el-descriptions direction="vertical" :column="4" size="large" border>
          <el-descriptions-item label="教材名称" :span="2">{{
            item.bookName
          }}</el-descriptions-item>
          <el-descriptions-item label="教材分类">
            <el-tag size="small"> {{ getCategory(item) }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="教材价格">
            <el-icon><money /></el-icon>&nbsp;{{
              item.bookPrice ?? "0"
            }}￥</el-descriptions-item
          >
          <el-descriptions-item label="作者">
            {{ item.author }}
          </el-descriptions-item>
          <el-descriptions-item label="ISBN号">
            {{ item.isbn }}
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">
            <div style="display: flex; align-items: center">
              <el-icon><timer /></el-icon>
              <span style="margin-left: 10px">{{
                item.createTime?.replace("T", " ")
              }}</span>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="库存">
            {{ item.inventory ?? "0" }}本
          </el-descriptions-item>
          <el-descriptions-item label="出版社">
            <el-tag>{{ item.press }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </el-space>

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
      :title="editIndex !== '' ? '编辑信息' : '新增教材'"
      @close="dialog = {}"
    >
      <el-form :model="dialog" label-width="120px">
        <el-form-item label="教材名称" required>
          <el-input v-model="dialog.bookName" placeholder="教材名称"></el-input>
        </el-form-item>
        <el-form-item label="教材分类" required>
          <el-select v-model="dialog.bookKind" placeholder="选择分类" size="large">
            <el-option
              v-for="item in categories"
              :key="item.id"
              :label="item.classes"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="教材价格" required>
          <el-input-number v-model="dialog.bookPrice" :min="0" />
        </el-form-item>
        <el-form-item label="作者" required>
          <el-input v-model="dialog.author" placeholder="作者"></el-input>
        </el-form-item>
        <el-form-item label="ISBN号">
          <el-input v-model="dialog.isbn" placeholder="ISBN号"></el-input>
        </el-form-item>
        <el-form-item label="库存余数">
          <el-input-number v-model="dialog.inventory" :min="0" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="dialog.press" placeholder="出版社"></el-input>
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
import { list, update, add, del, query } from "../api/book";
import { all as category } from "../api/category";
import { ElMessage } from "element-plus";
export default {
  name: "detail",
  created() {
    category().then((res) => {
      this.categories = res.data.data;
    });
    this.handleChange();
  },
  data() {
    return {
      dialog: {},
      dialogFormVisible: false,
      editIndex: "",
      deleteMap: [],
      currentPage: 1,
      pageSize: 4,
      total: 10,
    };
  },
  setup() {
    const detail = ref();
    const formInline = ref({
      bookName: "",
      isbn: "",
    });
    //分类数据
    const categories = ref([]);

    const getCategory = (item) => {
      const res = categories.value.find((v) => {
        return v.id === item.bookKind;
      });
      if (res == undefined) {
        //如果没有分类，重置为1
        item.bookKind = 1;
        update(item);
      }
      return res?.classes ?? "未知分类";
    };
    const multipleTableRef = ref();
    return {
      formInline,
      multipleTableRef,
      detail,
      getCategory,
      categories,
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
    handleEdit(index, item) {
      this.editIndex = index;
      this.dialog = JSON.parse(JSON.stringify(item));
      this.dialogFormVisible = true;
    },
    handleClose() {},

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
      this.formInline.bookName = "";
      this.formInline.isbn = "";
      this.handleChange();
    },
    //删除所有选中
    deleteAll() {
      // const ids = [];
      // this.deleteMap.forEach((element) => {
      //   ids.push(element.id);
      // });
      // //删除所有
      // deleteAll(ids)
      //   .then((res) => {
      //     ElMessage({
      //       message: "删除成功",
      //       type: "success",
      //     });
      //     this.$refs.multipleTableRef.clearSelection();
      //     this.handleChange();
      //   })
      //   .then((e) => {
      //     console.log(e);
      //   });
    },
    // selectChange(row) {
    //   this.deleteMap = row;
    // },
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
