<template>
  <div class="columns">
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
export default class HomeLayout extends Vue { }
</script>
