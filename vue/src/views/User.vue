<template>
<div>
  <div style="margin-bottom: 30px">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{path:'/'}">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

  </div>

  <div style="padding: 10px 0">
    <el-input style="width: 250px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
    <el-input style="width: 250px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email" class="ml-5"></el-input>
    <el-input style="width: 250px" placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address" class="ml-5"></el-input>
    <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
    <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='再想想'
        icon="el-icon-info"
        icon-color="red"
        title="确定删除吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>

    <el-upload
        action="http://localhost:8888/user/import" style="display: inline-block" :show-file-list="false" :on-success="handleExcelImportSuccess" accept=".xlsx">
        <el-button type="primary" class="ml-5" >导入<i class="el-icon-download"></i></el-button>
    </el-upload>


    <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-upload2"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="headerBG" @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="55">
    </el-table-column>
    <el-table-column prop="id" label="ID" width="50"></el-table-column>
    <el-table-column prop="username" label="用户名" width="120"></el-table-column>
    <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
    <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
    <el-table-column prop="phone" label="电话" width="120"></el-table-column>
    <el-table-column prop="address" label="地址"></el-table-column>

    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='再想想'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="handleDelete(scope.row.id)"
        >
          <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>
  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>

<!--//实现点击弹窗外的空白部分不关闭弹窗-->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
    <el-form label-width="80px" size="small" >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" autocomplete="off" show-password></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="save">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>

    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "User",
  data(){

    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      username:"",
      email:"",
      address:"",
      password: "******",

      form:{},

      dialogFormVisible:false,

      multipleSelection:[],
      headerBG:'headerBG',

    }
  },
  created() {
    //请求分页查询数据
    //前端端口是8080，后端端口是8888，产生了跨域问题
    this.load()
  },
  methods:{
    load(){
      //使用axios进行获取
      this.request.get("/user/page", {
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          email:this.email,
          address:this.address,
          password:this.password
        }
      }).then(res => {
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })

      //首先将res字符串转换成json
      //然后在控制太打印json
      /*fetch("http://localhost:8888/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
          .then(res => res.json()).then(res =>{
        console.log(res)
        this.tableData=res.data;
        this.total=res.total;
      })*/
    },
    reset(){
      this.username = ""
      this.email=""
      this.address=""
      this.load()
    },
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}

    },
    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true

    },
    handleDelete(id){
      this.request.delete("/user/" + id).then(res => {
        if(res.data){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection=val
    },
    delBatch(){
      let ids =  this.multipleSelection.map(v => v.id)  //将对象的数组变成纯id的数组，进行扁平化处理
      this.request.post("/user/del/batch", ids).then(res => {
        if(res.data){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    save(){
      this.request.post("/user",this.form).then(res => {
        if(res.data){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
          this.load()
        }
      })
    },
    cancel(){
      this.dialogFormVisible=false
      this.load()
    },

    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:8888/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("文件导入成功！")
      this.load()
    },
  }
}
</script>

<style>
.headerBG{
  background: #eee!important;
}
</style>