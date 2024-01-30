<script setup>
import {onMounted, onUnmounted, ref, watch} from 'vue'

const current = ref("0");

const numbers = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];

const operators = ['+', '-', '*', '/'];

function incrementCount(number) {
  if (current.value.length < 20) {
    current.value === "0" ? current.value = number : current.value += number;
  }
}

function appendDecimal() {
  const lastCharacter = current.value.slice(-1);
  if (!(lastCharacter === ".") && !(operators.includes(lastCharacter))) {
    current.value += '.';
  }
}

function appendOperator(operator) {
  if ((operator === '-' || operator === '+') &&  current.value === "0") {
    current.value = operator;
  }

  // Get last character in the equation.
  const lastChar = current.value.slice(-1);

  // Check if the last character is an operator.
  if (!operators.includes(lastChar)) {
    // If not, append operator.
    current.value += operator;
  } else {
    // If it is, replace the old with the new operator.
    current.value = current.value.slice(0, -1) + operator;
  }
}

function changeSign() {
  const numericRegex = /^[0-9]/;

  const firstChar = current.value.charAt(0);

  if (firstChar === "0") {
    current.value = "0";
  } else if (firstChar === '+') {
    current.value = "-" + current.value.slice(1);
  } else if (firstChar === '-') {
    current.value = "+" + current.value.slice(1);
  } else if (numericRegex.test(firstChar)) {
    current.value = "-" + current.value;
  }
}

function clearCurrent() {
  current.value = "0";
}

function deleteLastCharacter() {
  current.value.length === 1 ? current.value = "0" : current.value = current.value.slice(0,-1);
}

function calculate() {
  const equation = current.value;

  try {
    const result = new Function('return ' + equation)(); // Alternative to use eval() for calculations.
    if (result.toString() === "Infinity" || result.toString() === "NaN") {
      alert("Cannot divide by zero.");
    } else {
      current.value = result.toString();
      appendToHistoryLog(equation, current.value);
    }
  } catch (error) {
    alert("Error syntax.");
  }
}

function appendToHistoryLog(equation, answer) {
  const list = document.getElementById("log-list");
  const listItem = document.createElement("li");
  listItem.textContent = equation + " = " + answer;
  list.appendChild(listItem);
}

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
    <button id="button-change-sign" class="additional" v-on:click="changeSign"> +/- </button>
    <button id="button-DEL" class="additional" v-on:click="deleteLastCharacter"> DEL </button>
    <button id="button-decimal" class="numeric-values" v-on:click="appendDecimal()"> . </button>
    <button id="button-calculate" class="operator" v-on:click="calculate"> = </button>

    <div id="log">
      <h4>History: </h4>
      <ul id="log-list"></ul>
    </div>
  </div>

</template>

<style scoped>

#calculator-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  width: 400px;
  margin: 3% auto;
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
  grid-column: 1 / 1;
  grid-row: 2 / 2;
}

#button-change-sign {
  grid-column: 2 / 2;
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

</style>
