<template>
  <default-layout>
    <template v-slot:page-menu>
      <b-menu-list :label="$t('page-menu.configurations.title')">
        <b-menu-item tag="router-link" :label="$t('page-menu.configurations.users')"
          :to="{ name: 'configurations.users' }"/>
      </b-menu-list>
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
export default class ConfigurationLayout extends Vue { }
</script>
