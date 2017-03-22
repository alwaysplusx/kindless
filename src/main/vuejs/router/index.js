import Vue from 'vue'
import Router from 'vue-router'
import Add from '@/components/Add'
import Page from '@/components/Page'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Page',
      component: Page
    },
    {
      path: '/add',
      name: 'Add',
      component: Add
    }
  ]
})
