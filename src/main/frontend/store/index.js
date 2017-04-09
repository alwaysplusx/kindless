import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  authorized: false,
  accessToken: ''
}

const getters = {
  isAuthorized: state => { return state.authorized },
  getAccessToken: state => { return state.accessToken }
}

const mutations = {
  authorized (state, status) {
    state.authorized = status
  },
  accessToken (state, token) {
    state.accessToken = token
  }
}

export default new Vuex.Store({
  state,
  getters,
  mutations
})
