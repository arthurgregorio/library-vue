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
      <div class="column is-four-fifths">
        <b-field>
          <b-input type="search" :placeholder="$t('filters.users')" expanded></b-input>
          <p class="control">
            <b-button type="is-primary" >{{ $t('actions.search') }}</b-button>
          </p>
        </b-field>
      </div>
      <div class="column has-text-right">
        <b-button type="is-primary">
          {{ $t('actions.add') }}
        </b-button>
      </div>
      <div class="column is-full">
        <b-table
            :data="tableData"
            :loading="loading"
            hoverable>
            <template slot-scope="props">
              <b-table-column field="name" :label="$t('users.list.name')">
                {{ props.row.name }}
              </b-table-column>
              <b-table-column field="username" :label="$t('users.list.username')">
                {{ props.row.username }}
              </b-table-column>
              <b-table-column field="email" :label="$t('users.list.email')">
                {{ props.row.email }}
              </b-table-column>
              <b-table-column :label="$t('commons.list.actions')" width="10">
                <b-button
                  icon-pack="fas"
                  icon-left="edit"
                  type="is-primary"
                  @click.prevent="changeToEdit(props.row.id)"/>
                <b-button
                  type="is-danger"
                  icon-pack="fas"
                  icon-left="trash"
                  class="has-margin-left-7"
                  @click.prevent="changeToEdit(props.row.id)"/>
              </b-table-column>
            </template>
            <template slot="empty">
              <section class="section">
                <div class="content has-text-grey has-text-centered">
                  <p><b-icon pack="far" icon="sad-tear" size="is-large"/></p>
                  <p>{{ $t('commons.list.empty') }}</p>
                </div>
              </section>
            </template>
        </b-table>
      </div>
    </div>
  </main>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

import { User } from '@/model/administration/user'
import { PageRequest } from '@/model/page-request'

@Component
export default class UsersList extends Vue {
  private loading = false
  private selectedValue!: User;
  private tableData: User[] = []
  private pageRequest: PageRequest = new PageRequest()

  public onPageChange(page: number): void {
    console.log(page)
  }

  public changeToEdit(userId: string): void {
    this.$router.push({ name: 'users.edit', params: { id: userId } })
  }

  public changeToDelete(userId: string): void {
    this.$router.push({ name: 'users.delete', params: { id: userId } })
  }
}
</script>
