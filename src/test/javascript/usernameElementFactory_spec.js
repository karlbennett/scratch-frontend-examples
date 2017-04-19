describe('Create the username element', function () {

  it('Can create a username element', function () {

    var document = mock(Document);

    var usernameAnchor = mock(Element);
    var signOutAnchor = mock(Element);
    var text = mock(Node);
    var div = mock(Element);

    // Given
    when(document).createElement('a').thenReturn(usernameAnchor, signOutAnchor);
    when(document).createTextNode('Sign Out').thenReturn(text);
    when(document).createElement('div').thenReturn(div);

    // When
    var actual = new UsernameElementFactory(document).create();

    // Then
    assertThat(actual, is(div));
    verify(usernameAnchor).setAttribute('class', 'username');
    verify(signOutAnchor).setAttribute('href', '/signOut');
    verify(signOutAnchor).appendChild(text);
    verify(div).setAttribute('class', 'signin');
    verify(div).appendChild(usernameAnchor);
    verify(div).appendChild(signOutAnchor);
  });
});