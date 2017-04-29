describe('Set the page title to contain the username', function () {

  var window;
  var document;
  var factory;

  beforeEach(function () {
    window = {};
    document = mock(Document);
    factory = new TitleReplacerFactory(window, document);
  });

  it('Can create a title replacer', function () {

    var username = 'some username';

    // Given
    window.location = {};
    window.location.pathname = '/profile';
    document.title = 'old title';

    // When
    factory.create()(username);

    // Then
    assertThat(document.title, equalTo('Simpe Webapp (' + username + ')'))
  });

  it('The title will not be replaced unless on the profile page.', function () {

    var username = 'some username';

    var oldTitle = 'old title';

    // Given
    window.location = {};
    window.location.pathname = 'some string';
    document.title = oldTitle;

    // When
    factory.create()(username);

    // Then
    assertThat(document.title, equalTo(oldTitle))
  });
});