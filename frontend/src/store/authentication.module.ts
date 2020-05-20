import Vue from 'vue'

import { Module, VuexModule, MutationAction, Action, Mutation } from 'vuex-module-decorators'

import { Token } from '@/model/administration/token'
import { Principal } from '@/model/administration/principal'
import { Credential } from '@/model/administration/credential'
import { userSessionKey } from '@/model/utilities/configurations'

import { TokenClient } from '@/client/administration/token.client'
import { UserSessionClient } from '@/client/administration/user-session.client'

@Module({ name: 'UserSessionModule', namespaced: true })
export default class UserSessionModule extends VuexModule {
  public token: Token | null = null
  public principal: Principal | null = null

  @MutationAction({ rawError: true, mutate: ['token', 'principal'] })
  public async login(credential: Credential) {
    const token: Token = await new TokenClient().requestToken(credential)
    Vue.prototype.$cookies.set(userSessionKey, { token: token, principal: {} })

    const principal: Principal = await new UserSessionClient().me()
    Vue.prototype.$cookies.set(userSessionKey, { token: token, principal: principal })

    return { token: token, principal: principal }
  }

  @Action
  public logout(): void {
    this.context.commit('setToken', null)
    this.context.commit('setPrincipal', null)
    Vue.prototype.$cookies.remove(userSessionKey)
  }

  @Action
  public load(): void {
    const userSession = Vue.prototype.$cookies.get(userSessionKey)
    if (userSession !== null) {
      this.context.commit('setToken', userSession.token)
      this.context.commit('setPrincipal', userSession.principal)
    }
  }

  @Mutation
  private setToken(token: Token): void {
    this.token = token
  }

  @Mutation
  private setPrincipal(principal: Principal): void {
    this.principal = principal
  }

  get principalName(): string | undefined {
    return this.principal?.name
  }

  get principalUsername(): string | undefined {
    return this.principal?.username
  }

  get authorities(): string[] | undefined {
    return this.principal?.authorities
  }

  get isAuthenticated(): boolean {
    return this.token !== null && this.principal !== null
  }
}
