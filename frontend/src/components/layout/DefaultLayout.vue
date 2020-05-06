<template>
  <div>
    <main-menu />
    <div class="section">
      <router-view/>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'

import MainMenu from '@/components/base/MainMenu.vue'

@Component({
  components: {
    MainMenu
  }
})
export default class DefaultLayout extends Vue { }
</script>
