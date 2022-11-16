describe('Happy path', function() {
	beforeEach(() => {
	    // preserve, of re-visit
		// Cypress.Cookies.preserveOnce('JSESSIONID')
		cy.visit("/")
	})
	it('Homepage has content', () => {
		cy.contains("Greeting Service")
	})
	it('Greet the world', () => {
		cy.get('#name-input')
			.find("input")
			.type("World")
		cy.get('#greet-button')
			.click()
		cy.contains('Hello, World')
	})
})
