<template>
  <div>
    <h3 class="title has-text-black">{{ $t('login.title') }}</h3>
    <p class="subtitle has-text-black">{{ $t('login.subtitle') }}</p>
    <validation-observer ref="observer" v-slot="{ passes, invalid }">
      <validation-provider
        rules="required"
        v-slot="{ errors, valid }"
        :name="$t('login.form.username')" >
        <b-field
          :message="errors"
          :type="{ 'is-danger': errors[0], 'is-success': valid }">
          <b-input
            type="text"
            icon="user"
            tabindex="1"
            size="is-large"
            icon-pack="fas"
            v-model="credential.username"
            :placeholder="$t('login.form.username')"/>
        </b-field>
      </validation-provider>
      <validation-provider
        rules="required"
        v-slot="{ errors, valid }"
        :name="$t('login.form.password')">
        <b-field
          :message="errors"
          :type="{ 'is-danger': errors[0], 'is-success': valid }">
          <b-input
            icon="lock"
            tabindex="2"
            type="password"
            size="is-large"
            icon-pack="fas"
            password-reveal
            v-model="credential.password"
            :placeholder="$t('login.form.password')"/>
        </b-field>
      </validation-provider>
      <b-button
        tabindex="3"
        size="is-large"
        type="is-primary"
        :loading="loading"
        :disabled="invalid || loading"
        class="is-block is-fullwidth"
        @click.prevent="passes(doLogin)">
        {{ $t('login.actions.login') }}
      </b-button>
    </validation-observer>
    <p class="has-text-grey has-margin-top-4">
      <router-link :to="{ name: 'recover-password' }">
        {{ $t('login.actions.forgot-password') }}
      </router-link>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import { getModule } from 'vuex-module-decorators'

import FormUtilities from '@/mixins/form-utilities.mixin'

import { Credential } from '@/model/administration/credential'

import AuthenticationModule from '@/store/authentication.module'

@Component
export default class Login extends Mixins(FormUtilities) {
  private credential = new Credential()

  private authenticationModule!: AuthenticationModule

  private mounted(): void {
    this.authenticationModule.load()
    if (this.authenticationModule.isAuthenticated) {
      this.$router.push({ name: 'home' })
    }
  }

  private created(): void {
    this.authenticationModule = getModule(AuthenticationModule, this.$store)
  }

  private doLogin(): void {
    this.loading = true
    this.authenticationModule.login(this.credential)
      .then(
        () => {
          this.$router.push(this.$router.currentRoute.params.redirect || { name: 'home' })
        }, error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.loading = false
      })
  }
}
</script>
