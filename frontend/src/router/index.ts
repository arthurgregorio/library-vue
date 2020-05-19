import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'

import { tokenKey } from '@/model/utilities/configurations'

import DefaultLayout from '@/components/layout/DefaultLayout.vue'

import HomeLayout from '@/components/layout/HomeLayout.vue'
import LoginLayout from '@/components/layout/LoginLayout.vue'
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
    redirect: 'administration',
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
          },
          {
            props: true,
            path: ':id/detail',
            name: 'users.detail',
            meta: { authenticated: true },
            component: () => import(/* webpackChunkName: "users" */ '@/views/administration/users/UserDetail.vue')
          },
          {
            path: ':id/edit',
            name: 'users.edit',
            meta: { authenticated: true },
            props: (route) => ({ id: route.params.id, editing: true }),
            component: () => import(/* webpackChunkName: "users" */ '@/views/administration/users/UserForm.vue')
          },
          {
            path: ':id/delete',
            name: 'users.delete',
            meta: { authenticated: true },
            props: (route) => ({ id: route.params.id, deleting: true }),
            component: () => import(/* webpackChunkName: "users" */ '@/views/administration/users/UserDetail.vue')
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
        path: '/403', // TODO when the user has no permissions, improve the feedback
        name: '403',
        component: () => import(/* webpackChunkName: "403" */ '@/views/errors/403.vue')
      },
      {
        path: '/401', // TODO we really need this?
        name: '401',
        component: () => import(/* webpackChunkName: "401" */ '@/views/errors/401.vue')
      },
      {
        path: '/404',
        name: '404',
        component: () => import(/* webpackChunkName: "404" */ '@/views/errors/404.vue')
      },
      {
        path: '/500', // TODO enable this page to receive some details about the error
        name: '500',
        component: () => import(/* webpackChunkName: "500" */ '@/views/errors/500.vue')
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

router.beforeEach((to, from, next) => {
  const token = Vue.prototype.$cookies.get(tokenKey)
  if (to.matched.some(route => route.meta.authenticated) && token === null) {
    next({ name: 'login', params: { redirect: to.path } })
  } else {
    next()
  }
})

export default router
