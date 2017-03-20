<template>
  <div id="userAdd">
    username: <input v-model="user.username">
    password: <input v-model="user.password">
    <a v-bind:onclick="submit">Submit</a>
  </div>
</template>

<script>
export default {
  name: 'userAdd',
  data () {
    return {}
  }
}
</script>
