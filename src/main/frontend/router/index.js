import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
  path: '/home',
  name: 'home',
  components: {
    fullView: require('@/views/home')
  },
  meta: {
    title: '主页',
    auth: true
  }
}, {
  path: '/login',
  name: 'login',
  components: {
    fullView: require('@/views/login')
  }
}, {
  path: '',
  redirect: '/home'
}, {
  path: '*',
  name: '404',
  components: {
    fullView: require('@/views/error/404')
  }
}]

const router = new VueRouter({
  mode: 'history', // history, hash, abstract
  routes,
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }
})

export default router
