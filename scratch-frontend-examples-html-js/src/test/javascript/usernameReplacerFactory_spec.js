describe('Replace the sign in with the username', function () {

  it('Can create a username replacer', function () {

    var document = mock(Document);

    var usernameElement = mock(Element);
    var username = 'some username';

    var text = mock(Node);
    var anchor = mock(Element);
    var signin = mock(Element);
    var signinParent = mock(Element);

    // Given
    when(document).createTextNode(username).thenReturn(text);
    when(usernameElement).getElementsByTagName('a').thenReturn([anchor]);
    when(document).getElementsByClassName('signin').thenReturn([signin]);
    signin.parentElement = signinParent;

    // When
    new UsernameReplacerFactory(document).create(usernameElement)(username);

    // Then
    verify(anchor).setAttribute('href', '/profile');
    verify(anchor).appendChild(text);
    verify(signinParent).replaceChild(usernameElement, signin);
  });
});