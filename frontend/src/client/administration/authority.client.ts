import { AxiosInstance } from 'axios'

import { AxiosFactory } from '../axios.factory'

import { Authority } from '@/model/administration/authority'

export class AuthorityClient {
  private axiosClient: AxiosInstance

  constructor() {
    this.axiosClient = AxiosFactory.create('api/authorities')
  }

  public async findAll(): Promise<Array<Authority>> {
    try {
      const response = await this.axiosClient.get<Array<Authority>>('/')
      return response.data
    } catch (error) {
      return Promise.reject(error.response)
    }
  }
}
