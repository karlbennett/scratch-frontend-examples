describe('Set the profile heading to the username', function () {

  it('Can create a profile heading replacer', function () {

    var document = mock(Document);

    var username = 'some username';

    var text = mock(Node);
    var usernameHeading = mock(Element);

    // Given
    when(document).createTextNode(username).thenReturn(text);
    when(document).getElementsByClassName('username-heading').thenReturn([usernameHeading]);

    // When
    new ProfileHeadingReplacerFactory(document).create()(username);

    // Then
    verify(usernameHeading).appendChild(text);
  });
});