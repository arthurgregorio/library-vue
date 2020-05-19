<template>
  <main class="column">
    <div class="level">
      <div class="level-left">
        <div class="level-item">
          <div class="title" v-if="!editing">
            {{ $t('users.page-title.adding') }}
          </div>
          <div class="title" v-if="user && editing">
            {{ $t('users.page-title.editing', { name: user.name }) }}
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
                <a href="#" aria-current="page" v-if="editing">
                  {{ $t('breadcrumb.users.editing') }}
                </a>
                <a href="#" aria-current="page" v-else>
                  {{ $t('breadcrumb.users.adding') }}
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <validation-observer ref="formObserver" v-slot="{ passes, invalid }">
      <div class="columns is-multiline has-padding-top-4">
        <div class="column is-12">
          <b-field :label="$t('users.form.status')">
            <b-switch v-model="user.active" :type="user.active ? 'is-success' : ''" size="is-medium">
              {{ user.active ? $t('commons.text.yes') : $t('commons.text.no') }}
            </b-switch>
          </b-field>
        </div>
        <div class="column is-4">
          <validation-provider
            rules="required"
            v-slot="{ errors, valid }"
            :name="$t('users.form.name')" >
            <b-field
              :message="errors"
              :label="$t('users.form.name')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
              <b-input v-model="user.name" />
            </b-field>
          </validation-provider>
        </div>
        <div class="column is-4">
          <validation-provider
            rules="required"
            v-slot="{ errors, valid }"
            :name="$t('users.form.username')" >
            <b-field
              :message="errors"
              :label="$t('users.form.username')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
              <b-input :disabled="editing" v-model="user.username" />
            </b-field>
          </validation-provider>
        </div>
        <div class="column is-4">
          <validation-provider
            rules="required|email"
            v-slot="{ errors, valid }"
            :name="$t('users.form.email')" >
            <b-field
              :message="errors"
              :label="$t('users.form.email')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
              <b-input v-model="user.email" />
            </b-field>
          </validation-provider>
        </div>
        <div class="column is-4">
          <validation-provider
            vid="password"
            v-slot="{ errors, valid }"
            :name="$t('users.form.password')"
            :rules="{ required: !editing, min: 6 }">
            <b-field
              :message="errors"
              :label="$t('users.form.password')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
              <b-input  type="password" v-model="user.password" />
            </b-field>
          </validation-provider>
        </div>
        <div class="column is-4">
          <validation-provider
            v-slot="{ errors, valid }"
            :name="$t('users.form.confirmation')"
            :rules="{ required: !editing, matchPassword: '@password' }">
            <b-field
              :message="errors"
              :label="$t('users.form.confirmation')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
              <b-input type="password" v-model="confirmation" />
            </b-field>
          </validation-provider>
        </div>
        <div class="column is-4">
          <validation-provider
            rules="required"
            v-slot="{ errors, valid }"
            :name="$t('users.form.profile')">
            <b-field
              :message="errors"
              :label="$t('users.form.profile')"
              :type="{ 'is-danger': errors[0], 'is-success': valid }">
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
          </validation-provider>
        </div>
        <div class="column is-12 has-text-right">
          <b-button
            v-if="editing"
            type="is-success"
            :loading="actionLoading"
            @click="passes(doUpdate)"
            :disabled="invalid || actionLoading">
            {{ $t('actions.update') }}
          </b-button>
          <b-button
            v-else
            type="is-success"
            @click="passes(doSave)"
            :loading="actionLoading"
            :disabled="invalid || actionLoading">
            {{ $t('actions.save') }}
          </b-button>
          <b-button
            @click.prevent="goBack"
            :disabled="actionLoading"
            type="is-primary has-margin-left-7">
            {{ $t('actions.go-back') }}
          </b-button>
        </div>
        <b-loading :active.sync="loading" :is-full-page="false"/>
      </div>
    </validation-observer>
  </main>
</template>

<script lang="ts">
import { Component, Mixins, Prop } from 'vue-property-decorator'

import FormUtilities from '@/mixins/form-utilities.mixin'

import { User } from '@/model/administration/user'
import { Authority } from '@/model/administration/authority'

import { UserClient } from '@/client/administration/user.client'
import { AuthorityClient } from '@/client/administration/authority.client'

@Component
export default class UsersForm extends Mixins(FormUtilities) {
  private confirmation = ''
  private loadingAuthorities = false

  private user: User = new User()
  private authorities: Authority[] = []

  private userClient!: UserClient
  private authorityClient!: AuthorityClient

  @Prop({
    type: String,
    required: false
  })
  private readonly id!: string

  @Prop({
    type: Boolean,
    default: false
  })
  private readonly editing!: boolean

  private created(): void {
    this.userClient = new UserClient()
    this.authorityClient = new AuthorityClient()
  }

  private mounted(): void {
    this.loadAuthorities()
    if (this.editing) {
      this.loadUser()
    }
  }

  private doSave(): void {
    this.actionLoading = true
    this.userClient.save(this.user)
      .then(
        () => {
          this.clearForm()
          this.toastSuccess('commons.feedback.data-saved')
        },
        error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.actionLoading = false
      })
  }

  private doUpdate(): void {
    this.actionLoading = true
    this.userClient.update(this.user)
      .then(
        success => {
          this.user = success
          this.toastSuccess('commons.feedback.data-updated')
        },
        error => {
          this.shouldLog(error)
        }
      ).finally(() => {
        this.actionLoading = false
      })
  }

  private clearForm(): void {
    this.confirmation = ''
    this.user = new User()
    this.clearValidations()
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
}
</script>
