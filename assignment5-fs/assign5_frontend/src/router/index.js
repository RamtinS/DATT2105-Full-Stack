import { createRouter, createWebHistory } from 'vue-router'
import CalculatorView from "@/views/CalculatorView.vue";
import ContactFormView from "@/views/ContactFormView.vue";
import LoginPageView from '@/views/LoginPageView.vue'
import RegistrationFormView from '@/views/RegistrationFormView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/calculator',
      name: 'Calculator',
      component: CalculatorView
    },
    {
      path: '/contact-form',
      name: 'Contact-form',
      component: ContactFormView
    },
    {
      path: '/',
      name: 'Login-page',
      component: LoginPageView
    },
    {
      path: '/sign-up',
      name: 'Registration-form',
      component: RegistrationFormView
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
