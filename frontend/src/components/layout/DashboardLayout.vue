<template>
  <div class="columns">
    <aside class="column is-2">
      <b-menu>
        <b-menu-list :label="$t('page-menu.dashboard.title')">
        </b-menu-list>
      </b-menu>
    </aside>
    <fade-transition :duration="200" origin="center top" mode="out-in">
      <router-view />
    </fade-transition>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

import { FadeTransition } from 'vue2-transitions'

@Component({
  components: {
    FadeTransition
  }
})
export default class DashboardLayout extends Vue { }
</script>
