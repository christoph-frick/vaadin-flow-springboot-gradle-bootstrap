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
		cy.shadowGet('#name-input')
			.shadowFind("input")
			.type("World")
		cy.shadowGet('#greet-button')
			.click()
		cy.contains('Hello, World')
	})
})
