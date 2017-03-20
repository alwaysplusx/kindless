<template>
  <div class="page">
    <h1>{{ msg }}</h1>
    当前第{{page.page + 1}}页, 共{{page.total}}条数据
    <ul>
      <li v-for="v in content">{{ v.username }} - {{v.password}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'page',
  data () {
    return {
      msg: 'This is user page!',
      page: {}
    }
  },
  computed: {
    content: function () {
      return this.page.content || {}
    }
  },
  created: function () {
    this.reload()
  },
  methods: {
    reload: function () {
      fetch('http://localhost:8080/kindless/users/page', {
        mode: 'cors'
      }).then((response) => {
        return response.json()
      }).then((json) => {
        this.page = json
      })
    }
  }
}
</script>
