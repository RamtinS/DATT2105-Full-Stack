<script setup>

import {ref, watch} from "vue";
import {useFormStore} from "@/stores/form.js";
import axios from "axios";

const userStore = useFormStore(); //Initialize the store.
const firstName = ref(userStore.getFirstName());
const lastName = ref(userStore.getLastName());
const email = ref(userStore.getEmail());
const message = ref("");
const isSubmitted = ref(false);
const conformationMessage = ref("");

/**
 * Watches for changes in the variables name and email, and updates the value in the store upon change.
 */
watch([firstName, email], () => {
  userStore.updateUserInfo(firstName.value, lastName.value, email.value);
});

async function handleSubmit() {
  try {
    // await is used to wait for post to complete before proceeding to the next line of code.
    await axios.post("http://localhost:3000/feedbacks", {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      message: message.value,
    });

    isSubmitted.value = true;
    conformationMessage.value = "Form successfully submitted!";

    // Hide message after 5 seconds.
    setTimeout(() => {
      isSubmitted.value = false;
      conformationMessage.value = "";},
      5000);

    message.value = "";

  } catch(error) {
    conformationMessage.value = "An error occurred during form submission.";
    console.error(conformationMessage.value, error);
  }
}

</script>

<template>
  <div id="contact-form">
    <h1>Contact form</h1>
    <p>We appreciate your Feedback!</p>

    <form @submit.prevent="handleSubmit" v-show="!isSubmitted">

      <div class="name-container">

        <div class="input-container">
          <label for="first-name">First name</label>
          <input type="text" v-model="firstName" id="first-name" placeholder="First name" pattern="[A-Za-z]+" title="Please enter only letters." required>
        </div>

        <div class="input-container">
          <label for="last-name">Last name</label>
          <input type="text" v-model="lastName" id="last-name" placeholder="Last name" pattern="[A-Za-z]+" title="Please enter only letters." required>
        </div>

      </div>

      <label for="email">Email address</label>
      <input type="email" v-model="email" id="email" placeholder="Email" required>

      <label for="message">Message</label>
      <textarea id="message" v-model="message" placeholder="Message" required></textarea>

      <button type="submit" :disabled="firstName === '' || lastName === '' || email === '' || message === '' ">Submit</button>
    </form>

    <div v-if="isSubmitted" class="conformation-message"> {{conformationMessage}} </div>

  </div>
</template>

<style scoped>

#contact-form {
  margin: auto;
  width: 60%;
  padding: 0 15%;
  text-align: center;
  color: white;
  font-weight: bold;
}

.conformation-message {
  margin-top: 25px;
  font-size: 26px;
}

p {
  margin-top: 8px;
}

.name-container {
  display: flex;
  justify-content: space-between; /*Adds space between the components in the flex. */
}

.input-container {
  width: 48%;
}

label {
  display: block;
  margin-bottom: 5px;
  margin-top: 30px;
}

input {
  width: 100%;
  height: 25px;
  text-align: center;
  background-color: white;
  border: 1px solid black;
  border-radius: 8px;
}

textarea {
  min-width: 100%;
  width: 100%;
  height: 100px;
  background-color: white;
  resize: none;
}

button {
  background-color: dodgerblue;
  color: white;
  margin-top: 20px;
  padding: 6px 10px;
  border-radius: 20px;
  cursor: pointer;
  font-size: 100%;
  font-weight: bold;
  transition: opacity 0.15s;
}

button[disabled] {
  background-color: #ccc;
  color: #666;
  cursor: not-allowed;
}

button:hover {
  opacity: 0.8;
  box-shadow: 5px 5px 5px gray;
}

button:active {
  background-color: green;
}

button:active[disabled] {
  background-color: #ccc;
}

@media (max-width: 700px) {
  h1 {
    margin-top: 20px;
    font-size: 130%;
  }
}

</style>