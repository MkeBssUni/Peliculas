import Vue from 'vue'
import App from './App.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//axios
import axios from 'axios'
import VueAxios from 'vue-axios'

import '@asika32764/vue-animate/dist/vue-animate.css';

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
//Axios
Vue.use(VueAxios, axios)

import './assets/main.css'

new Vue({
  render: (h) => h(App)
}).$mount('#app')
