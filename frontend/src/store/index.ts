import Vue from 'vue'
import Vuex from 'vuex'

import UserSessionModule from './authentication.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    UserSessionModule: UserSessionModule
  }
})
