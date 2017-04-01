<template>
  <div class="home">
    <navbar></navbar>
    <div class="columns">
      <sidebar class="column is-2"></sidebar>
      <app-main class="column"></app-main>
    </div>
    <!-- <footer-bar class="footer"></footer-bar> -->
  </div>
</template>

<script>
import { AppMain, FooterBar, Navbar, Sidebar } from '@/components/layout/'

export default {
  name: 'home',
  components: {
    AppMain,
    FooterBar,
    Navbar,
    Sidebar
  },
  data () {
    return {
      msg: 'Hello World'
    }
  }
}
</script>
