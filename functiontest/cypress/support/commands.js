// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

// helper to search the whole DOM across shadow DOM borders
Cypress.Commands.add("shadowGet", (toFind) => {
    cy.get(toFind, {includeShadowDom: true})
})

// helper to find something inside a shadow root
Cypress.Commands.add("shadowFind", {prevSubject: true}, (subject, toFind) => {
    cy.wrap(subject).shadow().find(toFind)
})
