<template>
  <section class="app-main">
    <h2>App Main</h2>
    <div class="container is-fluid is-marginless app-content">
      <transition
        mode="out-in"
        enter-active-class="fadeIn"
        leave-active-class="fadeOut"
        appear>
        <router-view class="animated"></router-view>
      </transition>
    </div>
  </section>
</template>
