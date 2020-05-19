import Vue from 'vue'
import Vuex from 'vuex'

import TokenModule from './token.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    TokenModule
  }
})
