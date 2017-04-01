<template>
  <nav class="nav">
    <div class="nav-left">
      <a class="nav-item">
        <a class="fa fa-ravelry"> Kindless</a>
      </a>
    </div>

    <!-- This "nav-toggle" hamburger menu is only visible on mobile -->
    <!-- You need JavaScript to toggle the "is-active" class on "nav-menu" -->
    <span class="nav-toggle">
      <span></span>
      <span></span>
      <span></span>
    </span>

    <!-- This "nav-menu" is hidden on mobile -->
    <!-- Add the modifier "is-active" to display it on mobile -->
    <div class="nav-right nav-menu">
      <a class="nav-item">
        Home
      </a>
      <a class="nav-item">
        Documentation
      </a>
      <a class="nav-item">
        Blog
      </a>

      <span class="nav-item">
        <a class="button" >
          <span class="icon">
            <i class="fa fa-twitter"></i>
          </span>
          <span>Tweet</span>
        </a>
        <a class="button is-primary">
          <span class="icon">
            <i class="fa fa-download"></i>
          </span>
          <span>Download</span>
        </a>
      </span>
    </div>
  </nav>
</template>

<style>
  .nav {
    margin-bottom: 5px;
    box-shadow: 1px 1px 1px #888888;
  }
  .fa-ravelry {
    font-weight: bold
  }
</style>
