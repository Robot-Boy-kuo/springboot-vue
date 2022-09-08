<template>
<div style="font-size: 12px;line-height: 60px;display: flex">
  <div style="flex: 1;font-size: 20px">
    <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
  </div>



  <el-dropdown style="width: 140px;cursor: pointer">
    <div style="display: inline-block">
      <img src="https://img1.baidu.com/it/u=2908330017,2329821371&fm=253&fmt=auto?w=500&h=500" alt=""
            style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
      <span>RobotBoy</span><i class="el-icon-arrow-down" style="margin-left:5px"></i>
    </div>
    <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">个人信息</el-dropdown-item>
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
        <router-link to="/login" style="text-decoration: none">退出</router-link>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "header",
  props:{
    collapseBtnClass:String,
    collapse:Function,

  }
}
</script>

<style scoped>

</style>