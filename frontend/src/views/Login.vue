<template>
  <div>
    <h3 class="title has-text-black">{{ $t('login.title') }}</h3>
    <p class="subtitle has-text-black">{{ $t('login.subtitle') }}</p>
    <form>
      <div class="field">
        <div class="control has-icons-left">
          <input class="input is-large" type="text" tabindex="1"
            :placeholder="$t('login.form.username')" autofocus/>
            <span class="icon is-large is-left">
              <i class="fas fa-user"></i>
            </span>
        </div>
      </div>
      <div class="field">
        <div class="control has-icons-left">
          <input class="input is-large" type="password"
            tabindex="2" :placeholder="$t('login.form.password')" />
            <span class="icon is-large is-left">
              <i class="fas fa-lock"></i>
            </span>
        </div>
      </div>
      <button tabindex="3" class="button is-block is-large is-fullwidth is-primary"
        @click.prevent="doLogin">
        {{ $t('login.actions.login') }}
      </button>
      <p class="has-text-grey has-margin-top-4">
        <router-link :to="{ name: 'recover-password' }">
          {{ $t('login.actions.forgot-password') }}
        </router-link>
      </p>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Login extends Vue {
  public doLogin(): void {
    this.$router.push({ name: 'home' });
  }
}
</script>
