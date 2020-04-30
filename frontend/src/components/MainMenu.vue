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
            <router-link class="navbar-item" :to="{ name: 'dashboards' }">
              {{ $t('main-menu.items.dashboards') }}
            </router-link>
            <router-link class="navbar-item" :to="{ name: 'registrations' }">
              {{ $t('main-menu.items.registrations') }}
            </router-link>
            <router-link class="navbar-item" :to="{ name: 'configurations' }">
              {{ $t('main-menu.items.configurations') }}
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
import { Component, Vue } from 'vue-property-decorator';

import UserMenu from '@/components/UserMenu.vue';

@Component({
  components: {
    UserMenu,
  },
})
export default class MainMenu extends Vue { }
</script>
