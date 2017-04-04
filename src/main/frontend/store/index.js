import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  authorized: true,
  accessToken: ''
}

const getters = {
  getAuthorized: state => { return state.authorized },
  getAccessToken: state => { return state.accessToken }
}

export default new Vuex.Store({
  state,
  getters
})
