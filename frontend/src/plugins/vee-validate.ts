import Vue from 'vue'
import i18n from '@/i18n'

import {
  required,
  confirmed,
  length,
  email,
  min,
  max
} from 'vee-validate/dist/rules'
import {
  ValidationProvider,
  ValidationObserver,
  extend,
  configure
} from 'vee-validate'

Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)

configure({
  defaultMessage: (field, values) => {
    if (values && values !== undefined) {
      values._field_ = `${field}`.toLowerCase()
    }
    return i18n.t(`validation.${values?._rule_}`, values) as string
  }
})

extend('min', { ...min })
extend('max', { ...max })
extend('email', { ...email })
extend('length', { ...length })
extend('required', { ...required })
extend('confirmed', { ...confirmed })

extend('matchPassword', {
  params: ['target'],
  // eslint-disable-next-line
  validate(value, { target }: Record<string, any>) {
    return value === target
  }
})
