import axios from 'axios'
import Vue from 'vue'

let base = 'http://localhost:8080'

const login = params => { return axios.post(`${base}/login`, params).then(res => res.data) }

// const login = params => { return Vue.http.get(`${base}/login`, params).then(res => res.data) }

const meuns = params => { return axios.get(`${base}/menus`, params).then(res => res.data) }

export default { login, meuns }
