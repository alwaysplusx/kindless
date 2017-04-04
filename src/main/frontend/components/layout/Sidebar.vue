<template>
  <aside class="menu">
    <div class="menu-container" v-for="(m, x) in menu">
      <p class="menu-label">
        <span class="icon is-big"><i :class="'fa fa-' + m.icon "></i></span>
        <span>&nbsp;{{m.label}}</span>
      </p>
      <ul class="menu-list">
        <li v-for="(i, y) in m.items">
          <router-link :to="i.url">
            <a :class="{'is-active': (x + '.' + y) === index}" @click="toggle(x + '.' + y)">
              <span class="icon is-small"><i :class="'fa fa-' + i.icon "></i></span>
              <span>&nbsp;{{i.label}}</span>
            </a>
          </router-link>
        </li>
      </ul>
    </div>
  </aside>
</template>

<script>
  export default {
    data () {
      return {
        index: null,
        menu: [{
          label: 'General',
          icon: 'server',
          items: [
            {label: 'Dashboard', icon: 'diamond', url: '/dashboard'},
            {label: 'Customers', icon: 'user', url: '/customers'}
          ]
        }, {
          label: 'Team Settings',
          icon: 'cubes',
          items: [
            {label: 'Members', icon: 'address-book-o', url: '/users'},
            {label: 'Invitations', icon: 'envelope-o', url: '/invitations'},
            {label: 'Authentication', icon: 'leaf', url: '/authentication'}
          ]
        }, {
          label: 'Transactions',
          icon: 'credit-card',
          items: [
            {label: 'Payments', icon: 'credit-card', url: '/payments'},
            {label: 'Transfers', icon: 'money', url: '/transfers'}
          ]
        }]
      }
    },
    methods: {
      toggle (index) {
        console.log('index > ' + index)
        this.index = (this.index === index) ? '' : index
      },
      isActive () {
        return true
      }
    }
  }

</script>

<style>
.menu-container {
  padding-top: 10px;
}
</style>
