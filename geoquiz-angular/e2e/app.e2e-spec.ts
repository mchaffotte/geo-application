import { GeoquizAngularPage } from './app.po';

describe('geoquiz-angular App', () => {
  let page: GeoquizAngularPage;

  beforeEach(() => {
    page = new GeoquizAngularPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
