<template>
  <section class="hero is-success is-fullheight">
    <div class="hero-body">
      <div class="container has-text-centered">
        <div class="column is-4 is-offset-4">
          <div class="box">
            <figure class="avatar">
              <img src="@/assets/images/logo.png" />
            </figure>
            <fade-transition :duration="200" origin="center top" mode="out-in">
              <router-view />
            </fade-transition>
          </div>
        </div>
      </div>
    </div>
    <error-handler></error-handler>
  </section>
</template>

<script lang="ts">
import { FadeTransition } from 'vue2-transitions'
import { Component, Vue } from 'vue-property-decorator'

import ErrorHandler from '@/components/base/ErrorHandler.vue'

@Component({
  components: {
    ErrorHandler,
    FadeTransition
  }
})
export default class LoginLayout extends Vue {}
</script>

<style>
html,body {
  font-size: 14px;
  font-weight: 300;
}
.hero.is-success {
  background: #F2F6FA !important;
}
.hero .nav, .hero.is-success .nav {
  -webkit-box-shadow: none;
  box-shadow: none;
}
.box {
  margin-top: 5rem;
}
.avatar {
  margin-top: -70px;
  padding-bottom: 20px;
}
.avatar img {
  padding: 5px;
  background: #fff;
  border-radius: 50%;
  -webkit-box-shadow: 0 2px 3px rgba(10,10,10,.1), 0 0 0 1px rgba(10,10,10,.1);
  box-shadow: 0 2px 3px rgba(10,10,10,.1), 0 0 0 1px rgba(10,10,10,.1);
}
input {
  font-weight: 300;
}
p {
  font-weight: 700;
}
p.subtitle {
  padding-top: 1rem;
}
.login-hr{
  border-bottom: 1px solid black;
}
.has-text-black{
  color: black;
}
.field{
  padding-bottom: 10px;
}
.fa{
  margin-left: 5px;
}
</style>
