<template>
  <div class="tile is-ancestor">
    <div class="tile is-vertical is-8">
      <div class="tile">
        <div class="tile is-parent">
        </div>
        <div class="tile is-parent login-form">
          <article class="tile is-child">
            <p class="title">Sign in Kindless</p>
            <div class="field">
              <label class="label">Username</label>
              <p class="control has-icon has-icon-right">
                <input class="input" type="text" v-model="model.username">
              </p>
            </div>
            <div class="field">
              <label class="label">Password</label>
              <p class="control has-icon has-icon-right">
                <input class="input" type="text" v-model="model.password">
              </p>
            </div>
            <div class="field">
              <p class="control">
                <label class="checkbox">
                  <input type="checkbox">
                  Remember me
                </label>
              </p>
            </div>
            <div class="field is-grouped">
              <p class="control">
                <button class="button is-primary" @click="login">Sign in</button>
              </p>
            </div>
          </article>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import _ from '@/apis'
  export default {
    data () {
      return {
        model: {}
      }
    },
    methods: {
      login () {
        console.log(this.model)
        _.login(this.model).then(data => {
          console.log('login response data:')
          console.log(data)
          this.$store.commit('authorized', true)
        })
      }
    }
  }
</script>

<style>
  .login-form {
    margin-top: 60px;
  }

  .button {
    width: 100%;
  }
</style>
