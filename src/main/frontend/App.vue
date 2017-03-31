<template>
  <div id="app">
    <nprogress-container style="z-index: 9999"></nprogress-container>
    <transition name="fade" mode="out-in">
      <router-view name="fullView" style="z-index: 200"></router-view>
    </transition>
  </div>
</template>

<script>
import NprogressContainer from 'vue-nprogress/src/NprogressContainer'

export default {
  name: 'app',
  components: {
    NprogressContainer
  }
}
</script>

<style lang="scss" type="text/css" rel="stylesheet/scss">
  @import './assets/scss/main';

  .fade-enter-active,
  .fade-leave-active {
    transition: all .2s ease;
  }

  .fade-enter,
  .fade-leave-active {
    opacity: 0;
  }

  .nprogress-container {
    position: fixed !important;
    top: 0;
    left: 0;
    width: 100%;
    height: 4px;
    z-index: 9999;
  }
</style>
