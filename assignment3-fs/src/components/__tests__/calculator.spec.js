import {mount} from "@vue/test-utils";
import {describe, it, expect, beforeEach, afterEach} from "vitest";
import Calculator from "../TheCalculator.vue";


describe("Calculator", () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Calculator);
  })

  afterEach(() => {
    wrapper.unmount();
  })

  it("Renders the calculator component correctly", () => {
    // Check the calculator exists.
    expect(wrapper.exists()).toBeTruthy();

    // Check if the display element is present
    expect(wrapper.find('#display').exists()).toBeTruthy();

    // Check if numeric buttons are present.
    for (let i = 0; i <= 9; i++) {
      expect(wrapper.find(`#button-${i}`).exists()).toBe(true);
    }
  })
})
