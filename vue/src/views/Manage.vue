<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="isCollapse ? '64px' : '200px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
      <!--注意组件此处需要传值-->
      <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow"/>
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid#ccc;">
        <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse"/>

      </el-header>

      <el-main>
<!--        表示当前页面的子路由会在router-view里面展示-->
        <router-view/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Home',
  components: {
    Header,
    Aside,
    HelloWorld
  },
  data() {

    return {


      collapseBtnClass:'el-icon-s-fold',
      isCollapse: false,
      /*sidewith: 200,*/
      logoTextShow:true,
      headerBG:'headerBG'
    }
  },
    created() {

    },


  methods: {
    collapse(){//点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){   //收缩
        /*this.sidewith = 64*/
        this.collapseBtnClass='el-icon-s-unfold'
        this.logoTextShow = false
      }else{    //展开
        /*this.sidewith = 200*/
        this.collapseBtnClass='el-icon-s-fold'
        this.logoTextShow = true
      }
    },



  }

}
</script>

<style>
.headerBG{
  background: #eee!important;
}
</style>
