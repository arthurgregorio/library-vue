import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

import DefaultLayout from '@/components/layout/DefaultLayout.vue'

import HomeLayout from '@/components/layout/HomeLayout.vue'
import LoginLayout from '@/components/layout/LoginLayout.vue'
import ErrorLayout from '@/components/layout/ErrorLayout.vue'
import DashboardLayout from '@/components/layout/DashboardLayout.vue'
import RegistrationLayout from '@/components/layout/RegistrationLayout.vue'
import AdministrationLayout from '@/components/layout/AdministrationLayout.vue'

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
    component: DefaultLayout,
    children: [
      {
        path: '/home',
        name: 'home',
        meta: { authenticated: true },
        component: HomeLayout
      }
    ]
  },
  {
    path: '/dashboard',
    redirect: 'dashboard',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'dashboard',
        meta: { authenticated: true },
        component: DashboardLayout
      }
    ]
  },
  {
    path: '/registration',
    redirect: 'registration',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'registration',
        meta: { authenticated: true },
        component: RegistrationLayout
      }
    ]
  },
  {
    path: '/administration',
    name: 'administration',
    component: DefaultLayout,
    children: [
      {
        path: '',
        name: 'administration',
        meta: { authenticated: true },
        component: AdministrationLayout
      },
      {
        path: 'users',
        redirect: 'users',
        component: AdministrationLayout,
        children: [
          {
            path: '',
            name: 'users',
            meta: { authenticated: true },
            component: () => import(/* webpackChunkName: "users" */ '@/views/administration/users/UsersList.vue')
          },
          {
            path: 'add',
            name: 'users.add',
            meta: { authenticated: true },
            component: () => import(/* webpackChunkName: "users" */ '@/views/administration/users/UserForm.vue')
          }
        ]
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
    component: DefaultLayout,
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
