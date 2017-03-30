import Vue from 'vue'
import App from './App'
import router from './router'
import NProgress from 'vue-nprogress'

Vue.config.productionTip = false

Vue.use(NProgress)

const app = new Vue({
  router,
  nprogress: new NProgress({ parent: '.nprogress-container' }),
  template: '<App/>',
  components: { App }
})

app.$mount('#app')
