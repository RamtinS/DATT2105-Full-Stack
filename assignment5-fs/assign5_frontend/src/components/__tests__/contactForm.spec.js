import { mount } from "@vue/test-utils";
import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import ContactForm from "../TheContactForm.vue";
import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from '@/App.vue'

describe("Contact form", () => {

  const pinia = createPinia()
  const app = createApp(App)
  app.use(pinia)

  let wrapper;

  beforeEach(() => {
    wrapper = mount(ContactForm);
  })

  afterEach(() => {
    wrapper.unmount();
  })

  it("Renders the from component correctly", () => {
    // Check if the component is rendered.
    expect(wrapper.exists()).toBeTruthy();

    // Check if the form elements are present.
    expect(wrapper.find('form').exists()).toBeTruthy();
    expect(wrapper.find('input#first-name').exists()).toBeTruthy();
    expect(wrapper.find('input#last-name').exists()).toBeTruthy();
    expect(wrapper.find('input#email').exists()).toBeTruthy();
    expect(wrapper.find('textarea#message').exists()).toBeTruthy();
    expect(wrapper.find('button[type="submit"]').exists()).toBeTruthy();
  });
});
