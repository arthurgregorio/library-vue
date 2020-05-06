<template>
  <main class="column">
    <section class="hero is-medium">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">
            {{ $t('error.403.title') }}
          </h1>
          <h2 class="subtitle">
            {{ $t('error.403.message') }}
          </h2>
          <h3 class="subtitle">
            <a @click.prevent="goBack">
              {{ $t('actions.go-back') }}
            </a>
          </h3>
        </div>
      </div>
    </section>
  </main>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  methods: {
    goBack() {
      this.$router.go(-1)
    }
  }
})
</script>
