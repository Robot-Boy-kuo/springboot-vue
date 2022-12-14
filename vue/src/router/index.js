import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Manage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    redirect:"/home",
    //子页面，基于/的子路由
    children:[
      {path:'home',name:'首页',component:() => import('../views/Home.vue')},
      {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
    ]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
    //单独界面放在外面
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue'),
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
