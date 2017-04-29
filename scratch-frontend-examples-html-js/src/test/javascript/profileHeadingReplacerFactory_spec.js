describe('Set the profile heading to the username', function () {

  var window = {};
  var document;
  var factory;

  beforeEach(function () {
    window = {};
    document = mock(Document);
    factory = new ProfileHeadingReplacerFactory(window, document);
  });

  it('Can create a profile heading replacer', function () {

    var username = 'some username';

    var text = mock(Node);
    var usernameHeading = mock(Element);

    // Given
    window.location = {};
    window.location.pathname = '/profile';
    when(document).createTextNode(username).thenReturn(text);
    when(document).getElementsByClassName('main-heading').thenReturn([usernameHeading]);

    // When
    factory.create()(username);

    // Then
    verify(usernameHeading).appendChild(text);
  });

  it('The heading will not be replaced unless on the profile page.', function () {

    var username = 'some username';

    // Given
    window.location = {};
    window.location.pathname = 'some string';

    // When
    factory.create()(username);

    // Then
    verifyZeroInteractions(document);
  });
});