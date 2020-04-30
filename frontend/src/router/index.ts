import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

import HomeLayout from '@/components/HomeLayout.vue'
import LoginLayout from '@/components/LoginLayout.vue'
import ErrorLayout from '@/components/ErrorLayout.vue'
import DashboardLayout from '@/components/DashboardLayout.vue'
import RegistrationLayout from '@/components/RegistrationLayout.vue'
import ConfigurationLayout from '@/components/ConfigurationLayout.vue'

Vue.use(VueRouter)

// const checkPermission = (to: any, from: any, next: any) => {
//   const userGrants = store.getters['auth/userGrants']
//   const requiredAuthorizations = to.meta.authorizations
//   const authorized = userGrants.some(grant => requiredAuthorizations.includes(grant))
//   if (authorized) {
//     next()
//   } else {
//     next({ name: '403' })
//   }
// }

const routes: Array<RouteConfig> = [
  {
    path: '/',
    redirect: 'home',
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'home',
        meta: { authenticated: true },
        component: () => import(/* webpackChunkName: "home" */ '@/views/Home.vue')
      }
    ]
  },
  {
    path: '/dashboards',
    redirect: 'dashboards',
    component: DashboardLayout,
    children: [
      {
        path: '',
        name: 'dashboards',
        meta: { authenticated: true },
        component: () => import(/* webpackChunkName: "dashboards" */ '@/views/dashboards/Dashboards.vue')
      }
    ]
  },
  {
    path: '/registrations',
    redirect: 'registrations',
    component: RegistrationLayout,
    children: [
      {
        path: '',
        name: 'registrations',
        meta: { authenticated: true },
        component: () => import(/* webpackChunkName: "registrations" */ '@/views/registrations/Registrations.vue')
      },
    ]
  },
  {
    path: '/configurations',
    redirect: 'configurations',
    component: ConfigurationLayout,
    children: [
      {
        path: '',
        name: 'configurations',
        meta: { authenticated: true },
        component: () => import(/* webpackChunkName: "configurations" */ '@/views/configurations/Configurations.vue')
      },
      {
        path: 'users',
        name: 'configurations.users',
        meta: { authenticated: true },
        component: () => import(/* webpackChunkName: "configurations" */ '@/views/configurations/Users.vue')
      }
    ]
  },
  {
    path: '/login',
    redirect: 'login',
    component: LoginLayout,
    children: [
      {
        path: '',
        name: 'login',
        component: () => import(/* webpackChunkName: "external" */ '@/views/Login.vue')
      },
      {
        path: '/recover-password',
        name: 'recover-password',
        component: () => import(/* webpackChunkName: "external" */ '@/views/RecoverPassword.vue')
      }
    ]
  },
  {
    path: '/error',
    component: ErrorLayout,
    children: [
      {
        path: '/403',
        name: '403',
        component: () => import(/* webpackChunkName: "error" */ '@/views/errors/403.vue')
      },
      {
        path: '/404',
        name: '404',
        component: () => import(/* webpackChunkName: "error" */ '@/views/errors/404.vue')
      }
    ]
  },
  {
    path: '*',
    redirect: '404'
  }
]

const router = new VueRouter({
  routes,
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'is-active',
  linkExactActiveClass: 'is-active'
})

// router.beforeEach((to, from, next) => {
//   const authenticated = store.getters['auth/isAuthenticated']
//   if (to.matched.some(route => route.meta.authenticated) && !authenticated) {
//     next({ name: 'login', params: { redirect: to.path } })
//   } else {
//     next()
//   }
// })

export default router
