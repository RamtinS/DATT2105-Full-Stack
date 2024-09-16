<script setup>

import {ref} from "vue";
import {useTokenStore} from '@/stores/token.js'
import axios from 'axios'
import { RouterLink } from 'vue-router'
import router from '@/router/index.js'

const tokenStore = useTokenStore();
const email = ref("");
const password = ref("");

/**
 * Handles the login process by sending a POST request to the server with the email and password.
 * If the login is successful, the token is stored and the user is redirected to the calculator page.
 * If there is an error, it logs the error.
 *
 * @async
 * @function handleLogin
 * @returns {Promise<void>}
 */
async function handleLogin() {

  const loginData = {
    email: email.value,
    password: password.value
  };

  try {
    // Sending POST request to the server
    const response = await axios.post("http://localhost:8080/api/v1/auth/login", loginData);

    if (response.status === 200) {
      tokenStore.setToken(response.data.token);
      await router.push("/calculator");
    }

  } catch (error) {
    console.error(error);
  }
}

</script>

<template>
  <div id="login-page">

    <h1 id="login-title">Login</h1>

    <form @submit.prevent="handleLogin">
      <div class="input-box">
        <!-- <i class="bx bxs-user"></i> -->
        <input type="email" v-model="email" placeholder="Email">
      </div>
      <div class="input-box">
        <input type="password" v-model="password" placeholder="Password">
      </div>

      <button type="submit" id="login-btn">Login</button>
    </form>

    <!-- Rout to user registration page -->
    <RouterLink :to="{ name: 'Registration-form'}">Sign up</RouterLink>

  </div>
</template>

<style scoped>

#login-page {
  margin: auto;
  width: 60%;
  padding: 0 15%;
  text-align: center;
}

</style>