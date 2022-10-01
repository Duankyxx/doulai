import { createRouter, RouteRecordRaw, createWebHashHistory } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
  },
  {
    path: '/search_home',
    name: 'Search_home',
    component: () => import('@/views/Search_home.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
  },
  {
    path: '/registration',
    name: 'Registration',
    component: () => import('@/views/Registration.vue'),
  },
  {
    path: '/my',
    name: 'My',
    component: () => import('@/views/My.vue'),
  },
  {
    path: '/mySongs',
    name: 'MySongs',
    component: () => import('@/views/MySongs.vue'),
  },
  {
    path: '/uploadSongs',
    name: 'UploadSongs',
    component: () => import('@/views/UploadSongs.vue'),
  },
]

const router = createRouter({
  // @ts-ignore
  // mode: 'hash',
  history: createWebHashHistory(process.env.BASE_URL),
  // base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next('/home');
    return;
  }
  next();
})

export default router
