import { AxiosInstance } from 'axios'

import { AxiosFactory } from '../axios.factory'

import { Principal } from '@/model/administration/principal'

export class UserSessionClient {
  private axiosClient: AxiosInstance

  constructor() {
    this.axiosClient = AxiosFactory.create('api/me')
  }

  public async me(): Promise<Principal> {
    try {
      const response = await this.axiosClient.get<Principal>('/')
      return response.data
    } catch (error) {
      return Promise.reject(error.response)
    }
  }
}
