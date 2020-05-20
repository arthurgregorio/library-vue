<template>
  <header class="hero is-dark">
    <div class="hero-head">
      <nav class="navbar">
        <div class="navbar-brand">
          <a href="/home" class="navbar-item">
            <img src="@/assets/images/library-vue.png" width="112" height="28" />
          </a>
          <a class="navbar-item is-hidden-desktop"></a>
          <a class="navbar-item is-hidden-desktop"></a>
          <div class="navbar-burger burger" data-target="main-menu">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
        <div id="main-menu" class="navbar-menu">
          <div class="navbar-start">
            <router-link class="navbar-item" :to="{ name: 'dashboard' }">
              {{ $t('main-menu.items.dashboard') }}
            </router-link>
            <router-link  v-if="hasAuthority(['ADMINISTRATOR', 'LIBRARIAN'])"
              class="navbar-item" :to="{ name: 'registration' }">
              {{ $t('main-menu.items.registration') }}
            </router-link>
            <router-link v-if="hasAuthority('ADMINISTRATOR')"
              class="navbar-item" :to="{ name: 'administration' }">
              {{ $t('main-menu.items.administration') }}
            </router-link>
          </div>
          <div class="navbar-end">
            <div class="navbar-item">
              <user-menu />
            </div>
          </div>
        </div>
      </nav>
    </div>
  </header>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { getModule } from 'vuex-module-decorators'

import UserMenu from '@/components/base/UserMenu.vue'

import AuthenticationModule from '@/store/authentication.module'

@Component({
  components: {
    UserMenu
  }
})
export default class MainMenu extends Vue {
  private authenticationModule!: AuthenticationModule

  private mounted(): void {
    this.authenticationModule.load()
  }

  private created(): void {
    this.authenticationModule = getModule(AuthenticationModule, this.$store)
  }

  private hasAuthority(authorities: string[]): boolean {
    const userAuthorities = this.authenticationModule.authorities
    if (userAuthorities) {
      return userAuthorities.some(authority => authorities.includes(authority))
    }
    return false
  }
}
</script>
