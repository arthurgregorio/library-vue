<template>
  <div>
    <h3 class="title has-text-black">{{ $t('recover-password.title') }}</h3>
    <p class="subtitle has-text-black">{{ $t('recover-password.subtitle') }}</p>
    <validation-observer ref="observer" v-slot="{ passes, invalid }">
      <validation-provider
        rules="required|email"
        v-slot="{ errors, valid }"
        :name="$t('recover-password.form.email')" >
        <b-field
          :message="errors"
          :type="{ 'is-danger': errors[0], 'is-success': valid }">
          <b-input
            type="text"
            tabindex="1"
            icon="envelope"
            size="is-large"
            icon-pack="fas"
            v-model="email"
            :placeholder="$t('recover-password.form.email')"/>
        </b-field>
      </validation-provider>
      <b-button
        tabindex="2"
        size="is-large"
        type="is-primary"
        :loading="loading"
        :disabled="invalid"
        class="is-block is-fullwidth"
        @click.prevent="passes(doRecoverPassword)">
        {{ $t('recover-password.actions.recover') }}
      </b-button>
    </validation-observer>
    <p class="has-text-grey has-margin-top-4">
      <router-link :to="{ name: 'login' }">
        {{ $t('recover-password.actions.back-to-login') }}
      </router-link>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class RecoverPassword extends Vue {
  private email = ''
  private loading = false

  public doRecoverPassword() {
    console.log(this.email)
  }
}
</script>
