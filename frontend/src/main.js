// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import VModal from 'vue-js-modal'
import { store } from './store/store'


/* Vue-js-modal */
Vue.use(VModal, {dynamic: true, injectModalsContainer: true, dialog: true})

/* vue-moment */
const moment = require('moment');
require('moment/locale/ko');
Vue.use(require('vue-moment'), {
  moment
})

/* axios */
window.axios = require('axios')

axios.interceptors.response.use((response) => {
  return response;
}, (error) => {
  return Promise.reject(error);
})

/* Vuex */
Vue.use(Vuex)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
