<template>
  <default-layout :enable-page-menu="false">
    <template v-slot:content>
      <fade-transition :duration="200" origin="center top" mode="out-in">
        <router-view />
      </fade-transition>
    </template>
  </default-layout>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

import { FadeTransition } from 'vue2-transitions';

import DefaultLayout from './DefaultLayout.vue';

@Component({
  components: {
    FadeTransition,
    DefaultLayout,
  },
})
export default class HomeLayout extends Vue { }
</script>
