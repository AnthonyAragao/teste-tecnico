import { createRouter, createWebHistory } from 'vue-router'
import Index from '../views/Index.vue'
import MotosIndex from '../views/Motos/Index.vue'

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
      component: MotosIndex,
    }
  ],
})

export default router
