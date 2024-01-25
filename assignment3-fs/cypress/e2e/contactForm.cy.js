// https://on.cypress.io/api

describe('Form test', () => {
  beforeEach(() => {
    cy.visit("http://localhost:5173/contact-form")
  })

  it("Button should be deactivated when input fields are empty", () => {

    cy.get('#first-name').type(' ');
    cy.get('#last-name').type(' ');
    cy.get('#email').type(' ');
    cy.get('#message').type(' ')

    cy.get('button[type="submit"]').should('be.disabled');
  })

  it("Button should be enabled when input fields are filled", () => {

    cy.get('#first-name').type('Maria');
    cy.get('#last-name').type("Scott");
    cy.get('#email').type("marias@ntnu.com");
    cy.get('#message').type("Test message")

    cy.get('button[type="submit"]').should('not.be.disabled');
  })

  it("Successful submission display test", () => {
    cy.get('#first-name').type("Maria");
    cy.get('#last-name').type("Scott");
    cy.get('#email').type("marias@ntnu.com");
    cy.get('#message').type("Test message")

    cy.get('form').submit();

    cy.get('form').should('not.be.visible');
    cy.contains('.conformation-message', 'Form successfully submitted!').should('be.visible');
  })
})
