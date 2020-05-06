<template>
  <div class="columns">
    <fade-transition :duration="200" origin="center top" mode="out-in">
      <router-view />
    </fade-transition>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class ErrorLayout extends Vue {}
</script>
