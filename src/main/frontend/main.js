import Vue from 'vue'
import App from '@/App'
import router from '@/router'
import NProgress from 'vue-nprogress'

import 'normalize.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import 'font-awesome/scss/font-awesome.scss'

import store from '@/store'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(NProgress, {
  latencyThreshold: 100,
  router: true,
  http: true
})

const app = new Vue({
  router,
  store,
  nprogress: new NProgress({ parent: '.nprogress-container' }),
  ...App
})

app.$mount('#app')
