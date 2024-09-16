<script setup>

/**
 * Logic for a simple calculator. Handles basic arithmetic operations,
 * keyboard input, and history logging.
 * Dependencies: Vue.js, axios
 */

import {onMounted, onUnmounted, ref, watch} from 'vue';
import axios from "axios";
import {useTokenStore} from '@/stores/token.js'


const current = ref("0");

let error = ref(false);

const numbers = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];

const operators = ['+', '-', '*', '/'];

const currentPage = ref(1);

const tokenStore = useTokenStore();

const pageSize = ref(10); // Number of items per page.

const offset = ref(0);

let historyLog = ref([]);

/**
 * Increments the count.
 * @param {string} number - The number to increment.
 */
function incrementCount(number) {
  if (error.value) {
    return;
  }

  if (current.value.length < 20) {
    current.value === "0" ? current.value = number : current.value += number;
  }
}

/**
 * Appends a decimal point.
 */
function appendDecimal() {
  if (error.value) {
    return;
  }

  const lastCharacter = current.value.slice(-1);
  if (!(lastCharacter === ".") && !(operators.includes(lastCharacter))) {
    current.value += '.';
  }
}

/**
 * Checks if the current value contains an operator.
 * @returns {boolean} - Whether the current value contains an operator.
 */
function containsOperator() {
  return operators.some(operator => current.value.includes(operator));
}

/**
 * Appends an operator to the current value.
 * @param {string} operator - The operator to append.
 */
function appendOperator(operator) {
  if (error.value) {
    return;
  }

  if (!containsOperator()) {
    current.value += operator;
  }
}

/**
 * Clears the current value.
 */
function clearCurrent() {
  current.value = "0";
  error.value = false;
}

/**
 * Deletes the last character from the current value.
 */
function deleteLastCharacter() {
  if (error.value) {
    return;
  }
  current.value.length === 1 ?
    current.value = "0" : current.value = current.value.slice(0,-1);
}

// Set the Authorization header with the Bearer token
axios.defaults.headers.common['Authorization'] = `Bearer ${tokenStore.getToken()}`;

/**
 * Calculates the result of the equation.
 */
async function calculate() {
  if (error.value) {
    return;
  }

  const equation = current.value;

  const sendData = {
    expression: equation
  };

  try {
    const response = await axios.post("http://localhost:8080/calculator/calculate", sendData);
    if (!(/\d/.test(response.data.equationResult))) {
      error.value = true;
    }
    current.value = response.data.equationResult;
    await fetchHistoryLogs();
  } catch (error) {
    if (error.response.status === 401) {
      console.log("Unauthorized user:", error)
    } else {
      console.log("Error: ", error);
    }
  }
}

onMounted(() => {
  fetchHistoryLogs()
});

/**
 * Fetches paginated history logs from the server.
 */
async function fetchHistoryLogs() {
  try {

    const sendData = {
      limit: pageSize.value,
      offset: offset.value
    };

    // TODO change post to get request.
    const response = await axios.post("http://localhost:8080/calculator/log", sendData);

    if (response.status === 200) {
      historyLog.value = response.data.log;
      console.log(historyLog.value);
    }

  } catch (error) {
    console.error("Error fetching history logs:", error);
  }
}


/**
 * Loads the previous page of history logs.
 */
function loadPreviousPage() {
  if (currentPage.value > 1) {
    currentPage.value -= 1;
    offset.value = (currentPage.value - 1) * pageSize.value;
    fetchHistoryLogs();
  }
}

/**
 * Loads the next page of history logs.
 */
function loadNextPage() {
  currentPage.value += 1;
  offset.value = (currentPage.value - 1) * pageSize.value;
  fetchHistoryLogs();
}

/**
 * Updates the font size of the display based on the content length.
 */
function updateFontSize() {
  const displayElement = document.getElementById("display");
  const maxLength = 15;
  const contentLength = current.value.length;

  if (contentLength > maxLength) {
    const fontSize = 30 - (contentLength - maxLength) * 2;
    displayElement.style.fontSize = fontSize + "px";
  } else {
    displayElement.style.fontSize = "30px";
  }
}

// Calls updateFontSize function whenever the value of current changes.
watch(() => current.value, updateFontSize);

/**
 * Handles keyboard input events.
 * @param {KeyboardEvent} event - The keyboard event.
 */
function handleKeyboardInput(event) {
  const key = event.key;

  if (/\d/.test(key)) { // \d - numbers from 0 to 9.
    incrementCount(key);
  } else if (key === '.' || key === ',') {
    appendDecimal();
  } else if (['+', '-', '*', '/'].includes(key)) {
    appendOperator(key);
  } else if (key === 'Enter') {
    calculate();
  } else if (key === 'Delete') {
    clearCurrent();
  } else if (key === 'Backspace') {
    deleteLastCharacter();
  }
}

// Add event listener for keyboard input.
onMounted(() => {
  document.addEventListener('keydown', handleKeyboardInput);
});

// Remove event listener when component is unmounted for cleanup.
onUnmounted(() => {
  document.removeEventListener('keydown', handleKeyboardInput);
});

</script>

<template>
  <div id="calculator-grid">
    <div id="display">{{current}}</div>

    <button v-for="number in numbers" :key="number" :id="'button-' + number" class="numeric-values" v-on:click="incrementCount(number)">
      {{ number }}
    </button>

    <button id="button-divide" class="operator" v-on:click="appendOperator('/')"> / </button>
    <button id="button-multiply" class="operator" v-on:click="appendOperator('*')"> * </button>
    <button id="button-subtraction" class="operator" v-on:click="appendOperator('-')"> - </button>
    <button id="button-addition" class="operator" v-on:click="appendOperator('+')"> + </button>

    <button id="button-C" class="additional" v-on:click="clearCurrent"> C </button>
    <button id="button-DEL" class="additional" v-on:click="deleteLastCharacter"> DEL </button>
    <button id="button-decimal" class="numeric-values" v-on:click="appendDecimal()"> . </button>
    <button id="button-calculate" class="operator" v-on:click="calculate"> = </button>

    <div id="log">
      <h4>History: </h4>
      <ul id="log-list">

        <li v-for="item in historyLog" :key="item.id">{{ item }}</li>

      </ul>
    </div>
  </div>

  <div id="pagination-buttons">
    <button @click="loadPreviousPage">Previous Page</button>
    <button @click="loadNextPage">Next Page</button>
  </div>

</template>

<style scoped>

#calculator-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  width: 400px;
  margin: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: lightgray;
}

#display {
  grid-column: 1 / 5;
  color: white;
  background-color: gray;
  padding: 20px;
  height: 80px;
  text-align: center;
  font-size: 30px;
}

button {
  cursor: pointer;
  padding: 20px;
  font-size: 25px;;
  border: 1px solid #ccc;
}

.numeric-values {
  background-color: lightgray;
}

.numeric-values:hover {
  background-color: hsl(0, 0%, 60%);
}

.numeric-values:active {
  background-color: hsl(0, 0%, 50%);
}

.operator {
  background-color: orange;
}

.operator:hover {
  background-color: hsl(30, 70%, 50%);
}

.operator:active {
  background-color: hsl(30, 70%, 40%);
}

.additional {
  background-color: #a4a3a3;
}

.additional:hover {
  background-color: hsl(0, 0%, 50%);;
}

.additional:active {
  background-color: hsl(0, 0%, 40%);;
}

#button-C {
  grid-column: 1 / 3;
  grid-row: 2 / 2;
}

#button-DEL {
  grid-column: 3 / 3;
  grid-row: 2 / 2;
}

#button-divide {
  grid-column: 4 / 4;
  grid-row: 2 / 2;
}

#button-multiply {
  grid-column: 4 / 4;
  grid-row: 3 / 3;
}

#button-subtraction {
  grid-column: 4 / 4;
  grid-row: 4 / 4;
}

#button-addition {
  grid-column: 4 / 4;
  grid-row: 5 / 5;
}

#button-decimal {
  grid-column: 3 / 4;
  grid-row: 6 / 6;
}

#button-calculate {
  grid-column: 4 / 4;
  grid-row: 6 / 6;
}

#button-0 {
  grid-column: 1 / 3;
  grid-row: 6 / 6;
}

#button-1 {
  grid-column: 1 / 1;
  grid-row: 5 / 5;
}

#button-2 {
  grid-column: 2 / 2;
  grid-row: 5 / 5;
}

#button-3 {
  grid-column: 3 / 3;
  grid-row: 5 / 5;
}

#button-4 {
  grid-column: 1 / 1;
  grid-row: 4 / 4;
}

#button-5 {
  grid-column: 2 / 2;
  grid-row: 4 / 4;
}

#button-6 {
  grid-column: 3 / 3;
  grid-row: 4 / 4;
}

#button-7 {
  grid-column: 1 / 1;
  grid-row: 3 / 3;
}

#button-8 {
  grid-column: 2 / 2;
  grid-row: 3 / 3;
}

#button-9 {
  grid-column: 3 / 3;
  grid-row: 3 / 3;
}

#log {
  grid-column: 1 / 5;
  background-color: white;
  font-size: 20px;
  border: 1px solid #ccc;
  padding-left: 20px;
  padding-top: 10px;
  padding-bottom: 10px;
  height: 150px;
  overflow: auto; /*Enables scrolling when content overflow.*/
}

#pagination-buttons {

}

</style>