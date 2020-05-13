<template>
  <div></div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

import { AxiosError } from 'axios'

import EventBus from '@/components/event/event-bus'

@Component
export default class ErrorHandler extends Vue {
  public mounted(): void {
    EventBus.$on('errorCaught', this.displayError)
  }

  public displayError(event: AxiosError): void {
    const response = event?.response
    const status = response?.status
    const data = response?.data

    if (status === 400) {
      if (!data) {
        this.showToast(this.translate('commons.feedback.bad-request'))
      } else {
        this.showToast(data?.message, 'is-warning')
      }
    } else if (status === 500) {
      this.showToast(this.translate('commons.feedback.server-error'))
    }
  }

  private showToast(message: string, type = 'is-danger'): void {
    this.$buefy.toast.open({ message: message, type: type, duration: 3500 })
  }

  private translate(message: string): string {
    return this.$t(message).toString()
  }
}
</script>
