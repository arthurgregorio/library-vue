import { Component, Vue } from 'vue-property-decorator'

import { ValidationObserver } from 'vee-validate'
import { AxiosError } from 'axios'

@Component
class FormUtilities extends Vue {
  protected loading = false
  protected actionLoading = false

  /**
   * Helper method to clear all validations on the form observer
   */
  protected clearValidations(): void {
    this.$nextTick(() => {
      (this.$refs.formObserver as InstanceType<typeof ValidationObserver>).reset()
    })
  }

  /**
   * Helper method to go back with Vue-Router
   */
  protected goBack(): void {
    this.$router.go(-1)
  }

  /**
   * Helper method to define if the message should be displayed on the console
   *
   * It only applies to http-500 messages
   *
   * @param error response from Axios
   */
  protected shouldLog(error: AxiosError): void {
    if (error?.response?.status === 500) {
      console.log(error.response)
    }
  }

  /**
   * Toast a success message
   *
   * @param text for the i18n key to the message
   */
  protected toastSuccess(text: string) {
    this.toast(text, 'is-success')
  }

  /**
   * Toast an info message
   *
   * @param text for the i18n key to the message
   */
  protected toastInfo(text: string) {
    this.toast(text, 'is-info')
  }

  /**
   * Toast a danger message
   *
   * @param text for the i18n key to the message
   */
  protected toastDanger(text: string) {
    this.toast(text, 'is-danger')
  }

  /**
   * Toast a warning message
   *
   * @param text for the i18n key to the message
   */
  protected toastWarning(text: string) {
    this.toast(text, 'is-warning')
  }

  /**
   * Simple toast helper method to display messages
   *
   * @param text the message to be parsed by i18n parser
   * @param style to be used as decoration for this toast
   */
  protected toast(text: string, style: string) {
    this.$buefy.toast.open({ message: this.$t(text).toString(), type: style })
  }
}

export default FormUtilities
