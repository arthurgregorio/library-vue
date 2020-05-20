import { AxiosInstance, AxiosRequestConfig } from 'axios'

import { AxiosFactory } from '../axios.factory'

import { Token } from '@/model/administration/token'
import { Credential } from '@/model/administration/credential'
import { apiClientId, apiClientSecret } from '@/model/utilities/configurations'

export class TokenClient {
  private axios: AxiosInstance

  constructor() {
    this.axios = AxiosFactory.create('oauth', false)
  }

  private configure(): AxiosRequestConfig {
    return {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      auth: {
        username: apiClientId,
        password: apiClientSecret
      }
    }
  }

  public async requestToken(credential: Credential): Promise<Token> {
    const data = new FormData()
    data.append('grant_type', 'password')
    data.append('username', credential.username)
    data.append('password', credential.password)

    try {
      const response = await this.axios.post<Token>('/token', data, this.configure())
      return response.data
    } catch (error) {
      return Promise.reject(error.response)
    }
  }

  public async checkToken(token: Token): Promise<Token> {
    const data = new FormData()
    data.append('token', token.access_token)

    try {
      const response = await this.axios.post<Token>('/check_token', data, this.configure())
      return response.data
    } catch (error) {
      return Promise.reject(error.response)
    }
  }

  public async refreshToken(token: Token, username: string): Promise<Token> {
    const data = new FormData()
    data.append('grant_type', 'refresh_token')
    data.append('username', username)
    data.append('refresh_token', token.refresh_token)

    try {
      const response = await this.axios.post<Token>('/token', data, this.configure())
      return response.data
    } catch (error) {
      return Promise.reject(error.response)
    }
  }
}
