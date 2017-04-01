<template>
  <div id="app">
    <nprogress-container></nprogress-container>
    <router-view></router-view>
    <!-- <navbar></navbar> -->
    <!-- <sidebar></sidebar> -->
    <!-- <app-main></app-main> -->
    <!-- <footer-bar class="footer"></footer-bar> -->
  </div>
</template>

<script>
import NprogressContainer from 'vue-nprogress/src/NprogressContainer'
import { AppMain, FooterBar, Navbar, Sidebar } from '@/components/layout/'

export default {
  name: 'app',
  components: {
    NprogressContainer,
    AppMain,
    FooterBar,
    Navbar,
    Sidebar
  }
}
</script>
