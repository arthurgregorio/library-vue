<template>
  <main class="column">
    <section class="hero is-medium">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">
            {{ $t('error.401.title') }}
          </h1>
          <h2 class="subtitle">
            {{ $t('error.401.message') }}
          </h2>
          <h3 class="subtitle">
            <a @click.prevent="doLogout">
              {{ $t('actions.logout') }}
            </a>
          </h3>
        </div>
      </div>
    </section>
  </main>
</template>

<script lang="ts">
import Vue from 'vue'

import { getModule } from 'vuex-module-decorators'

import TokenModule from '@/store/token.module'

export default Vue.extend({
  name: 'error-401' as string,
  methods: {
    doLogout() {
      this.tokenModule.destroy()
      this.$router.push({ name: 'login' })
    }
  },
  data() {
    return {
      tokenModule: {} as TokenModule
    }
  },
  created(): void {
    this.tokenModule = getModule(TokenModule, this.$store)
  }
})
</script>
