import Vue from 'vue';

import router from './router';
import store from './store';
import i18n from './i18n';

import '@/plugins/buefy'
import '@/plugins/vee-validate'

import App from './App.vue';

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App),
}).$mount('#app');
