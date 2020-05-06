<template>
  <main class="column">
    <div class="level">
      <div class="level-left">
        <div class="level-item">
          <div class="title">{{ $t('administration.users.page-title') }}</div>
        </div>
      </div>
      <div class="level-right">
        <div class="level-item">
          <nav class="breadcrumb" aria-label="breadcrumbs">
            <ul>
              <li>
                <router-link :to="{ name: 'home' }">
                  <b-icon pack='fas' size="is-small" icon="home"></b-icon>
                  {{ $t('breadcrumb.home') }}
                </router-link>
              </li>
              <li>
                <router-link :to="{ name: 'administration' }">
                  {{ $t('breadcrumb.administration.root') }}
                </router-link>
              </li>
              <li class="is-active">
                <a href="#" aria-current="page">{{ $t('breadcrumb.administration.users') }}</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <div class="columns is-multiline">
    </div>
  </main>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class UsersList extends Vue { }
</script>
