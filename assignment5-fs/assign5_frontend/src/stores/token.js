import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useTokenStore
  = defineStore('token', () => {

  const token = ref(sessionStorage.getItem('token') || '');

  function setToken(newToken) {
    token.value = newToken;
    sessionStorage.setItem('token', newToken); // Update sessionStorage with new token.
  }

  function getToken() {
    return token.value;
  }

  function clearToken() {
    token.value = '';
    sessionStorage.setItem('token', '');
  }

  return { token, setToken, getToken, clearToken}

});

