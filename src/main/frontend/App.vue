<template>
  <div id="app">
    <nprogress-container></nprogress-container>
    <div class="app-container" v-if="isAuthorized">
      <navbar></navbar>
      <div class="container page-container">
        <div class="columns">
          <sidebar class="column page-menu is-2"></sidebar>
          <div class="column page-context">
            <keep-alive>
              <router-view></router-view>
            </keep-alive>
          </div>
        </div>
      </div>
    </div>
    <login v-if="!isAuthorized"></login>
    <!-- <footer-bar class="hero-footer"></footer-bar> -->
  </div>
</template>

<script>
import NprogressContainer from 'vue-nprogress/src/NprogressContainer'
import { FooterBar, Navbar, Sidebar } from '@/components/layout/'
import login from '@/views/login'
import store from '@/store'

export default {
  name: 'app',
  store: store,
  components: {
    NprogressContainer,
    FooterBar,
    Navbar,
    Sidebar,
    login
  },
  computed: {
    isAuthorized () {
      console.log(this)
      return this.$store.getters.isAuthorized
    }
  }
}
</script>

<style>
.page-container {
  padding-top: 20px;
}
</style>
