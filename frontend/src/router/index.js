import {
  createRouter,
  createWebHistory
} from 'vue-router'


const routes = [{
    path: '/',
    name: 'Home',
    redirect: '/login'
  },
  {
    path: '/main',
    component: () => import( /* webpackChunkName: "index" */ '../components/Index.vue'),
    name: 'Index',
    meta: {
      title: '首页',
      icon: 'dashboard',
      affix: true
    },
    children: [
      {
        //用户管理
        path: 'detail',
        component: () => import('@/views/detail.vue'),
        name: 'Detail',
        meta:{
          requiresAuth:true
        }
      },
      {
        //教材管理
        path: 'library',
        component: () => import('@/views/Library.vue'),
        name: 'Library',
        meta:{
          requiresAuth:true
        }
      },
      {
        //教材分类管理
        path: 'category',
        component: () => import('@/views/Category.vue'),
        name: 'Category',
        meta:{
          requiresAuth:true
        }
      },
      {
        //教材订购
        path: 'subscribe',
        component: () => import('@/views/Subscribe.vue'),
        name: 'Subscribe',
        meta:{
          requiresAuth:true
        }
      },
      {
        //订单查询
        path: 'orders',
        component: () => import('@/views/Order.vue'),
        name: 'Order',
        meta:{
          requiresAuth:true
        }
      },
      {//缺书登记
        path: 'edit',
        component: () => import('@/views/Edit.vue'),
        name: 'Edit',
        meta:{
          requiresAuth:true
        }
      },
      {
        path: 'messages',
        component: () => import('@/views/Messages.vue'),
        name: 'Messages',
        meta:{
          requiresAuth:true
        }
      },
       {
         //个人中心
        path: 'user',
        component: () => import( /* webpackChunkName: "user" */'@/views/User.vue'),
        name: 'User',
        meta:{
          requiresAuth:true
        }
      },
      // {
      //   path: 'publish',
      //   component: () => import( /* webpackChunkName: "editor" */'@/views/Editor.vue'),
      //   name: 'Publish',
      //   meta:{
      //     requiresAuth:true
      //   }
      // },
      // {
      //   path: 'editor/:blogId',
      //   component: () => import( /* webpackChunkName: "editor" */'@/views/Editor.vue'),
      //   name: 'Editor',
      //   meta:{
      //     requiresAuth:true
      //   }
      // },
  
      // {
      //   path: 'Content/:blogId',
      //   component: () => import( /* webpackChunkName: "blogDetail" */'@/views/content.vue'),
      //   name: 'Content',
      //   meta:{
      //     requiresAuth:true
      //   }
      // },
    ]
  },
  {
    path: '/login',
    name: 'Login',

    component: () => import( /* webpackChunkName: "login" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import( /* webpackChunkName: "register" */ '../views/Register.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router