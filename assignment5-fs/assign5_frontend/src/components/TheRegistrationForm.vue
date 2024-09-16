<script setup>

import {ref} from "vue";
import axios from 'axios'
import { RouterLink } from 'vue-router'

const email = ref("");
const password = ref("");

/**
 * Handles user creation by sending a POST request to the server with the email and password.
 * If the user is created successfully, an alert is shown.
 * If there is an error, it displays an appropriate alert message based on the error status.
 *
 * @async
 * @function handleCreateUser
 * @returns {Promise<void>}
 */
async function handleCreateUser() {

  const userData = {
    email: email.value,
    password: password.value
  };

  try {
    const response = await axios.post("http://localhost:8080/api/v1/auth/save-user", userData);

    if (response.status === 200) {
      alert("User has been created.");
    }

  } catch (error) {
    const status = error.response?.status;
    if (status !== null) {
      switch (status) {
        case 409:
          alert("User already exists.");
          break;
        case 500:
          alert("Internal server error.");
          break;
      }
    } else {
      alert("Could not connect to server. ")
    }
  }
}


</script>

<template>
  <div id="registration-page">

    <h1 id="registration-title">Sign up</h1>

    <form @submit.prevent="handleCreateUser">
      <div class="input-box">
        <input type="email" v-model="email" placeholder="Email..">
      </div>
      <div class="input-box">
        <input type="password" v-model="password" placeholder="Password..">
      </div>

      <button type="submit" id="registration-btn">Sign up</button>
    </form>

    <!-- Rout to user registration page -->
    <RouterLink :to="{ name: 'Login-page'}">Back to Login</RouterLink>

  </div>

</template>

<style scoped>

#registration-page {
  margin: auto;
  width: 60%;
  padding: 0 15%;
  text-align: center;
}

</style>