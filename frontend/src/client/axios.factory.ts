import Vue from 'vue'
import Axios, { AxiosInstance } from 'axios'

import { apiBaseUrl, tokenKey } from '@/model/utilities/configurations'

import { Token } from '@/model/administration/token'

export class AxiosFactory {
  public static create(requestPath: string, requireAuth = true): AxiosInstance {
    const header = requireAuth ? {
      'Content-type': 'application/json',
      Authorization: `Bearer ${this.getAccessToken()}`
    } : {
      'Content-type': 'application/json'
    }
    return this.build(requestPath, header)
  }

  public static build(requestPath: string, header: object): AxiosInstance {
    return Axios.create({
      baseURL: `${apiBaseUrl}/${requestPath}`,
      headers: { ...header }
    })
  }

  public static getAccessToken(): string {
    const token: Token = Vue.prototype.$cookies.get(tokenKey)
    return token !== null ? token.access_token : ''
  }
}
