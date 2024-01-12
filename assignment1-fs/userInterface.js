/**
 * Represents the current number.
 * @type {number}
 */
let currentNumber = 0;

/**
 * Increases the current number and updates the display.
 */
function increaseNumber() {
  currentNumber++
  // Sets the text in the number-display span to the value of the currentNumber variable.
  document.getElementById("number-display").innerText = currentNumber;
}

/**
 * Toggles the visibility of the footer.
 */
function toggleFooter() {
  const footer = document.getElementById("footer");
  if (footer.style.display === "none") {
    footer.style.display = "block";
  } else {
    footer.style.display = "none";
  }
}

/**
 * Array containing various fruits.
 * @type {string[]}
 */
const fruits = ["Dragon fruit", "Persimmon", "Kiwi", "Starfruit",
  "Mango", "Blackberry", "Guava", "Passion fruit", "Pineapple", "Papaya"];

/**
 * Creates an array of randomly selected fruits.
 * @returns {string[]} - Random array of fruits.
 */
function createRandomFruitArray() {
  const randomFruitArray = [];

  for (let i = 0; i < 5; i++) {
    let randomIndex = Math.floor(Math.random() * fruits.length);
    randomFruitArray.push(fruits[randomIndex]);
  }
  return randomFruitArray;
}

/**
 * Updates the content of the random words list with random fruits.
 */
function getRandomFruits() {
  const randomFruits = createRandomFruitArray();
  let randomList = document.getElementById("random-words-list")

  randomList.innerHTML = ""; // Clear the existing content of the list.

  for (let i = 0; i < randomFruits.length; i++) {
    let listItem = document.createElement("li");
    listItem.textContent = randomFruits[i];
    randomList.appendChild(listItem)
  }
}