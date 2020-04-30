<template>
  <div>
    <main-menu />
    <div class="section">
      <div class="columns">
        <aside class="column is-2" v-if="enablePageMenu">
          <b-menu>
            <slot name="page-menu"></slot>
          </b-menu>
        </aside>
        <slot name="content"></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

import MainMenu from '@/components/MainMenu.vue';

@Component({
  components: {
    MainMenu,
  },
})
export default class DefaultLayout extends Vue {
  @Prop({
    type: Boolean,
    required: false,
    default: true,
  })
  readonly enablePageMenu!: boolean
}
</script>
