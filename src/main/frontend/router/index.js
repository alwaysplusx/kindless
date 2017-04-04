import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
  path: '/',
  name: 'home',
  // component: require('@/views/home')
  components: {
    default: require('@/views/home'),
    animated: require('@/views/error/404')
  }
}, {
  path: '/login',
  name: 'login',
  component: require('@/views/login')
}, {
  path: '/users',
  name: 'users',
  component: require('@/views/users')
}, {
  path: '',
  redirect: '/'
}, {
  path: '*',
  name: '404',
  component: require('@/views/error/404')
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
