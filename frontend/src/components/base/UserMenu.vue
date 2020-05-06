<template>
  <b-dropdown position="is-bottom-left" append-to-body aria-role="menu" trap-focus>
    <button class="button" slot="trigger" slot-scope="{ active }">
      <span>Administrator</span>
      <b-icon pack='fas' size="is-small" :icon="active ? 'angle-up' : 'angle-down'"></b-icon>
    </button>
    <b-dropdown-item aria-role="listitem">
      <b-icon pack='fas' size="is-small" icon="cog"></b-icon>
      {{ $t('user-menu.items.my-profile') }}
    </b-dropdown-item>
    <b-dropdown-item aria-role="listitem" @click="doLogout">
      <b-icon pack='fas' size="is-small" icon="sign-out-alt"></b-icon>
      {{ $t('user-menu.items.logout') }}
    </b-dropdown-item>
  </b-dropdown>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class UserMenu extends Vue {
  public doLogout (): void {
    this.$router.push({ name: 'login' })
  }
}
</script>
