<template>
  <main class="column">
    <div class="level">
      <div class="level-left">
        <div class="level-item">
          <div class="title" v-if="user && !deleting">
            {{ $t('users.page-title.detailing', { name: user.name }) }}
          </div>
          <div class="title" v-if="user && deleting">
            {{ $t('users.page-title.deleting', { name: user.name }) }}
          </div>
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
              <li>
                <router-link :to="{ name: 'users' }">
                  {{ $t('breadcrumb.users.listing') }}
                </router-link>
              </li>
              <li class="is-active">
                <a v-if="!deleting" href="#" aria-current="page">
                  {{ $t('breadcrumb.users.detailing') }}
                </a>
                <a v-if="deleting" href="#" aria-current="page">
                  {{ $t('breadcrumb.users.deleting') }}
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <div class="columns is-multiline">
      <div class="columns is-multiline has-padding-top-4">
        <div class="column is-12">
          <b-field :label="$t('users.form.status')">
            <b-switch v-model="user.active" :type="user.active ? 'is-success' : ''" size="is-medium">
              {{ user.active ? $t('commons.text.yes') : $t('commons.text.no') }}
            </b-switch>
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.name')">
            <b-input v-model="user.name" />
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.username')">
            <b-input v-model="user.username" />
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.email')">
            <b-input v-model="user.email" />
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.password')">
            <b-input  type="password" v-model="user.password" disabled/>
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.confirmation')">
            <b-input type="password" disabled/>
          </b-field>
        </div>
        <div class="column is-4">
          <b-field :label="$t('users.form.profile')">
            <b-taginput
              autocomplete
              type="is-info"
              :data="authorities"
              v-model="user.authorities"
              :loading="loadingAuthorities"
              :readonly="loadingAuthorities"
              :placeholder="$t('users.form.search-profile')">
              <template slot-scope="props">
                {{ $t(`constants.authorities.${props.option.name}`) }}
              </template>
              <template slot="empty">
                {{ $t('commons.list.empty') }}
              </template>
              <template slot="tag" slot-scope="props">
                {{ $t(`constants.authorities.${props.tag.name}`) }}
              </template>
            </b-taginput>
          </b-field>
        </div>
        <div class="column is-12 has-text-right" v-if="!deleting">
          <b-button
            type="is-success"
            class="has-margin-left-7"
            @click.prevent="changeToEdit">
            {{ $t('actions.edit') }}
          </b-button>
          <b-button
            type="is-danger"
            class="has-margin-left-7"
            @click.prevent="changeToDelete">
            {{ $t('actions.delete') }}
          </b-button>
          <b-button
            type="is-primary"
            @click.prevent="goBack"
            class="has-margin-left-7">
            {{ $t('actions.go-back') }}
          </b-button>
        </div>
        <div class="column is-12 has-text-right" v-else>
          <span class="is-inline has-margin-right-5 has-text-danger delete-message">
            {{ $t('commons.feedback.delete-message') }}
          </span>
          <b-button
            type="is-danger"
            :loading="actionLoading"
            :disabled="actionLoading"
            @click.prevent="doDelete">
            {{ $t('commons.text.yes') }}
          </b-button>
          <b-button
            type="is-success"
            @click.prevent="goBack"
            :disabled="actionLoading"
            class="has-margin-left-7">
            {{ $t('commons.text.no') }}
          </b-button>
        </div>
      </div>
      <b-loading :active.sync="loading" :is-full-page="false"/>
    </div>
  </main>
</template>

<script lang="ts">
import { Component, Prop, Mixins } from 'vue-property-decorator'

import FormUtilities from '@/mixins/form-utilities.mixin'

import { User } from '@/model/administration/user'
import { Authority } from '@/model/administration/authority'

import { UserClient } from '@/client/administration/user.client'
import { AuthorityClient } from '@/client/administration/authority.client'

@Component
export default class UsersDetail extends Mixins(FormUtilities) {
  private loadingAuthorities = false

  private user: User = new User()
  private authorities: Authority[] = []

  private userClient!: UserClient
  private authorityClient!: AuthorityClient

  @Prop({
    required: true,
    type: String
  })
  private readonly id!: string

  @Prop({
    type: Boolean,
    default: false
  })
  private readonly deleting!: boolean

  private created(): void {
    this.userClient = new UserClient()
    this.authorityClient = new AuthorityClient()
  }

  public mounted(): void {
    this.loadAuthorities()
    this.loadUser()
  }

  private loadAuthorities(): void {
    this.loadingAuthorities = true
    this.authorityClient.findAll()
      .then(
        success => {
          this.authorities = success
        },
        error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.loadingAuthorities = false
      })
  }

  private loadUser(): void {
    this.loading = true
    this.userClient.findById(+this.id)
      .then(
        success => {
          this.user = success
        }, error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.loading = false
      })
  }

  public doDelete(): void {
    this.actionLoading = true
    this.userClient.delete(+this.id)
      .then(
        () => {
          this.toastSuccess('commons.feedback.data-deleted')
          this.$router.push({ name: 'users' })
        }, error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.actionLoading = false
      })
  }

  public changeToEdit(): void {
    this.$router.push({ name: 'users.edit', params: { id: this.id } })
  }

  public changeToDelete(): void {
    this.$router.push({ name: 'users.delete', params: { id: this.id } })
  }
}
</script>
