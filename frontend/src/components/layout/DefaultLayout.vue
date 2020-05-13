<template>
  <div>
    <main-menu />
    <div class="section">
      <router-view/>
    </div>
    <error-handler></error-handler>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'

import MainMenu from '@/components/base/MainMenu.vue'
import ErrorHandler from '@/components/base/ErrorHandler.vue'

@Component({
  components: {
    MainMenu,
    ErrorHandler
  }
})
export default class DefaultLayout extends Vue { }
</script>
