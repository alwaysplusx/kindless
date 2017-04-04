<template>
  <div id="app">
    <nprogress-container></nprogress-container>
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
    <!-- <footer-bar class="hero-footer"></footer-bar> -->
  </div>
</template>

<script>
import NprogressContainer from 'vue-nprogress/src/NprogressContainer'
import { FooterBar, Navbar, Sidebar } from '@/components/layout/'
import login from '@/views/login'

export default {
  name: 'app',
  components: {
    NprogressContainer,
    FooterBar,
    Navbar,
    Sidebar,
    login
  }
}
</script>

<style>
.page-container {
  padding-top: 20px;
}
</style>
