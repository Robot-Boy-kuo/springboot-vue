<template>
<div style="font-size: 12px;line-height: 60px;display: flex">
  <div style="flex: 1;font-size: 20px">
    <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
  </div>



  <el-dropdown style="width: 140px;cursor: pointer">
    <div style="display: inline-block">
      <img :src="user.avatarUrl" alt=""
            style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
      <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left:5px"></i>
    </div>
    <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
        <router-link to="/person">个人信息</router-link>
      </el-dropdown-item>
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
        <span style="text-decoration: none" @click="logout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Function,
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName; //需要监听的数据
    }
  },
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    collapse(){

    },
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    },
  }
}
</script>

<style scoped>

</style>