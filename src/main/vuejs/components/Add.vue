<template>
  <div class="add">
    username: <input v-model="user.username"><br/>
    password: <input v-model="user.password"><br/>
    <button v-on:click="submit">submit</button> 
  </div>
</template>

<script>
export default {
  name: 'add',
  data () {
    return {
      user: {}
    }
  },
  methods: {
    submit () {
      let data = new FormData()
      data.append('username', this.user.username)
      data.append('password', this.user.password)

      fetch('http://localhost:8080/kindless/users/add', {
        method: 'post',
        body: data
      }).then((response) => {
        return response.json()
      }).then((json) => {
        console.info(json)
      })
    }
  }
}
</script>
