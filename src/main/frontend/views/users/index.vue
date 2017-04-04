<template>
  <table class="table">
    <thead>
      <tr>
        <th>
          <abbr title="Index">Index</abbr>
        </th>
        <th v-for="header in headers">
          <abbr :title="header.title">{{header.text}}</abbr>
        </th>
      </tr>
    </thead>
    <tbody v-for="(row, x) in rows">
      <tr>
        <th>{{x + 1}}</th>
        <td v-for="r in row">{{r}}</td>
      </tr>
    </tbody>
  </table>
</template>

<script>

  export default {
    data () {
      return {
        headers: [
          {title: 'Username', text: 'Username'},
          {title: 'Password', text: 'Password'}
        ],
        rows: [
          {username: 'Karen', password: 'abc123'},
          {username: 'Edith', password: 'abc123'},
          {username: 'Sykes', password: 'abc123'},
          {username: 'Solis', password: 'abc123'},
          {username: 'Shannon', password: 'abc123'},
          {username: 'Rollins', password: 'abc123'},
          {username: 'Shields', password: 'abc123'},
          {username: 'Ryan', password: 'abc123'}
        ]
      }
    }
  }

</script>
