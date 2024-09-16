<script setup>
import { RouterLink, RouterView } from 'vue-router'

</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink :to="{ name: 'Login-page'}">Login</RouterLink>
        <RouterLink :to="{ name: 'Calculator'}">Calculator</RouterLink>
        <RouterLink :to="{ name: 'Contact-form'}">Contact Form</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView /> <!--This is where the route components are injected.-->
</template>

<style scoped>

nav {
  text-align: center;
  width: 100%;
  font-size: 1rem;
  margin-top: 2rem;
}

nav a {
  color: blue;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;
    padding: 1rem 0;
    margin-top: 1rem;
  }
}

</style>
