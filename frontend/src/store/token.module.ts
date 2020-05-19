import Vue from 'vue'

import { Module, VuexModule, MutationAction, Action, Mutation } from 'vuex-module-decorators'

import { Token } from '@/model/administration/token'
import { Credential } from '@/model/administration/credential'

import { TokenClient } from '@/client/administration/token.client'

import { tokenKey } from '@/model/utilities/configurations'

@Module({ name: 'TokenModule', namespaced: true })
export default class TokenModule extends VuexModule {
  public token: Token | null = null

  @MutationAction({ rawError: true, mutate: ['token'] })
  public async request(credential: Credential) {
    const response: Token = await new TokenClient().requestToken(credential)
    Vue.prototype.$cookies.set(tokenKey, response)
    return { token: response }
  }

  @Action
  public destroy(): void {
    this.context.commit('setToken', null)
    if (Vue.prototype.$cookies.isKey(tokenKey)) {
      Vue.prototype.$cookies.remove(tokenKey)
    }
  }

  @Mutation
  private setToken(token: Token): void {
    this.token = token
  }
}
