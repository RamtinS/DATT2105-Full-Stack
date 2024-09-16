import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useFormStore
  = defineStore('user', () => {

  // The variables to be stored.
  const firstName = ref('');
  const lastName = ref('');
  const email = ref('');

  function updateUserInfo(firstNameValue, lastNameValue, emailValue) {
    firstName.value = firstNameValue;
    lastName.value = lastNameValue;
    email.value  = emailValue;
  }

  function getFirstName() {
    return firstName.value;
  }

  function getLastName() {
    return lastName.value;
  }

  function getEmail() {
    return email.value;
  }

  return {name: firstName, email, updateUserInfo, getFirstName, getLastName, getEmail};
});
