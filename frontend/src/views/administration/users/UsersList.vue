<template>
  <main class="column">
    <div class="level">
      <div class="level-left">
        <div class="level-item">
          <div class="title">{{ $t('users.page-title.listing') }}</div>
        </div>
      </div>
      <div class="level-right">
        <div class="level-item">
          <nav class="breadcrumb" aria-label="breadcrumbs">
            <ul>
              <li>
                <router-link :to="{ name: 'home' }">
                  <b-icon pack='fas' size="is-small" icon="home"/>
                  {{ $t('breadcrumb.home') }}
                </router-link>
              </li>
              <li>
                <router-link :to="{ name: 'administration' }">
                  {{ $t('breadcrumb.administration') }}
                </router-link>
              </li>
              <li class="is-active">
                <a href="#" aria-current="page">
                  {{ $t('breadcrumb.users.listing') }}
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <div class="columns is-multiline">
      <div class="column is-four-fifths">
        <b-field>
          <b-input
            expanded
            type="search"
            v-model="pageRequest.filter"
            @keypress.native.enter="loadUsers"
            :placeholder="$t('filters.users')"/>
          <p class="control">
            <b-button
              type="is-primary"
              @click.prevent="loadUsers">
              {{ $t('actions.search') }}
            </b-button>
          </p>
        </b-field>
      </div>
      <div class="column has-text-right">
        <b-button type="is-primary" @click.prevent="changeToAdd">
          {{ $t('actions.add') }}
        </b-button>
      </div>
      <div class="column is-full">
        <b-table
            hoverable
            :loading="loading"
            @select="onRowSelected"

            paginated
            backend-pagination
            :data="pageResponse.content"
            :per-page="pageRequest.pageSize"
            :total="pageResponse.totalElements"
            @page-change="onPageChange"

            backend-sorting
            @sort="onSort"

            aria-page-label="Página"
            aria-next-label="Próxima página"
            aria-current-label="Página atual"
            aria-previous-label="Página anterior">
            <template slot-scope="props">
              <b-table-column field="active" :label="$t('commons.list.status')" width="5%" sortable>
                <b-tag v-if="props.row.active" type="is-success">{{ $t('commons.list.active') }}</b-tag>
                <b-tag v-else type="is-warning">{{ $t('commons.list.inactive') }}</b-tag>
              </b-table-column>
              <b-table-column field="name" :label="$t('users.list.name')" sortable>
                {{ props.row.name }}
              </b-table-column>
              <b-table-column field="username" :label="$t('users.list.username')" sortable>
                {{ props.row.username }}
              </b-table-column>
              <b-table-column field="email" :label="$t('users.list.email')" sortable>
                {{ props.row.email }}
              </b-table-column>
              <b-table-column :label="$t('commons.list.actions')" width="10%">
                <b-tooltip type="is-info" :label="$t('actions.edit')">
                  <b-button
                    type="is-primary"
                    icon-left="file-edit"
                    @click.prevent="changeToEdit(props.row.id)"/>
                </b-tooltip>
                <b-tooltip type="is-info" :label="$t('actions.delete')">
                  <b-button
                    type="is-danger"
                    class="has-margin-left-7"
                    icon-left="trash-can-outline"
                    @click.prevent="changeToDelete(props.row.id)"/>
                </b-tooltip>
              </b-table-column>
            </template>
            <template slot="bottom-left">
              <span class="has-margin-right-7">{{ $t('commons.list.rows-per-page') }}</span>
              <b-select v-model="pageRequest.pageSize">
                <option value="5">5</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <option value="100">100</option>
              </b-select>
            </template>
            <template slot="empty" v-if="!loading">
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
import { Component, Mixins } from 'vue-property-decorator'

import FormUtilities from '@/mixins/form-utilities.mixin'

import { UserClient } from '@/client/administration/user.client'

import { User } from '@/model/administration/user'

import { PageRequest } from '@/model/page-request'
import { PageResponse } from '@/model/page-response'

@Component
export default class UsersList extends Mixins(FormUtilities) {
  private pageRequest: PageRequest = new PageRequest()
  private pageResponse: PageResponse<User> = new PageResponse()

  private userClient!: UserClient

  public created(): void {
    this.userClient = new UserClient()
  }

  public mounted(): void {
    this.loadUsers()
  }

  private loadUsers(): void {
    this.loading = true
    this.userClient.find(this.pageRequest)
      .then(
        success => {
          this.pageResponse = success
        }, error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.loading = false
      })
  }

  private onRowSelected(row: User): void {
    this.$router.push({ name: 'users.detail', params: { id: row.id.toString() } })
  }

  private onPageChange(page: number): void {
    this.pageRequest.currentPage = page - 1
    this.loadUsers()
  }

  private onSort(field: string, order: string): void {
    this.pageRequest.sortField = field
    this.pageRequest.direction = order
    this.loadUsers()
  }

  private changeToAdd(): void {
    this.$router.push({ name: 'users.add' })
  }

  private changeToEdit(userId: string): void {
    this.$router.push({ name: 'users.edit', params: { id: userId } })
  }

  private changeToDelete(userId: string): void {
    this.$router.push({ name: 'users.delete', params: { id: userId } })
  }
}
</script>
