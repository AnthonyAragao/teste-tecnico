import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/Index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Index,
    },
    {
      path: '/motos',
      name: 'motos',
      component: () => import('../views/Motos/Index.vue'),
    },
    {
      path: '/motos/create',
      name: 'motos-create',
      component: () => import('../views/Motos/Form.vue'),
    },
    {
      path: '/motos/:id/edit',
      name: 'motos-edit',
      component: () => import('../views/Motos/Form.vue'),
    },
    {
      path: '/carros',
      name: 'carros',
      component: () => import('../views/Carros/Index.vue'),
    },
    {
      path: '/carros/create',
      name: 'carros-create',
      component: () => import('../views/Carros/Form.vue'),
    },
    {
      path: '/carros/:id/edit',
      name: 'carros-edit',
      component: () => import('../views/Carros/Form.vue'),
    }
  ],
})

export default router
