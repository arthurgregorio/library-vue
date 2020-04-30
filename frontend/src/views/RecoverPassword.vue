<template>
  <div>
    <h3 class="title has-text-black">
      {{ $t('recover-password.title') }}
    </h3>
    <p class="subtitle has-text-black">
      {{ $t('recover-password.subtitle') }}
    </p>
    <form>
      <div class="field">
        <div class="control has-icons-left">
          <input class="input is-large" type="email" tabindex="1"
            :placeholder="$t('recover-password.form.email')" autofocus />
            <span class="icon is-large is-left">
              <i class="fas fa-envelope"></i>
            </span>
        </div>
      </div>
      <button tabindex="3" class="button is-block is-large is-fullwidth is-primary">
        {{ $t('recover-password.actions.recover') }}
      </button>
      <p class="has-text-grey has-margin-top-4">
        <router-link :to="{ name: 'login' }">
          {{ $t('recover-password.actions.back-to-login') }}
        </router-link>
      </p>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class RecoverPassword extends Vue {}
</script>
