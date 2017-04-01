import Vue from 'vue'
import App from '@/App'
import router from '@/router'
import NProgress from 'vue-nprogress'

import 'font-awesome/css/font-awesome.css'
import 'bulma/css/bulma.css'

import store from '@/store'

Vue.config.devtools = true
Vue.config.productionTip = false

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
