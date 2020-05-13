import Axios, { AxiosInstance } from 'axios'

import EventBus from '@/components/event/event-bus'

export class AxiosFactory {
  public static create (requestPath: string): AxiosInstance {
    const apiUrl: string = process.env.VUE_APP_API_URL || 'http://localhost:8080'

    const axios: AxiosInstance = Axios.create({
      baseURL: `${apiUrl}/${requestPath}`,
      headers: {
        'Content-type': 'application/json',
        Authorization: 'Bearer 64a9e2a4-4d94-4306-99fc-2b2a8ac05a1a'
      }
    })

    this.applyInterceptors(axios)

    return axios
  }

  private static applyInterceptors(axios: AxiosInstance): void {
    axios.interceptors.response.use(
      success => { return Promise.resolve(success) },
      error => {
        EventBus.$emit('errorCaught', error)
        return Promise.reject(error)
      }
    )
  }
}
