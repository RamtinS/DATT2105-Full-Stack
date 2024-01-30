import { createRouter, createWebHistory } from 'vue-router'
import CalculatorView from "@/views/CalculatorView.vue";
import ContactFormView from "@/views/ContactFormView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Calculator',
      component: CalculatorView
    },
    {
      path: '/contact-form',
      name: 'Contact-form',
      component: ContactFormView

      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      //component: () => import('../views/AboutView.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/contact-form') {
    document.body.style.backgroundImage = 'url("/src/assets/background.jpg")';
    document.body.style.backgroundSize = 'cover';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundAttachment = 'fixed';
  } else {
    // Reset background styles for other routes.
    document.body.style.backgroundImage = '';
    document.body.style.backgroundSize = '';
    document.body.style.backgroundRepeat = '';
    document.body.style.backgroundAttachment = '';
  }
  next();
});

export default router
