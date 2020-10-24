describe('Dashboard page Tests', function () {
  it('should display the application title', () => {
    cy.visit('/');
    cy.get('h6').contains('GeoAdmin').should('be.visible');
  });
});
