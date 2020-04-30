<template>
  <default-layout>
    <template v-slot:page-menu>
    </template>
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
export default class RegistrationLayout extends Vue { }
</script>
