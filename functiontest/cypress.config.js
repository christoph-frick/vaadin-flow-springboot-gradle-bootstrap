module.exports = {
    e2e: {
        setupNodeEvents(on, config) {
            return require('./cypress/plugins/index.js')(on, config)
        },
        baseUrl: 'http://localhost:8080',
        includeShadowDom: true
    },
}
