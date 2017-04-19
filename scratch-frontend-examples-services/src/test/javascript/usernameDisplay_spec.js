describe('Display Username', function () {

  it('Can replace the signin with the username', function () {

    var usernameElementFactory = mock(UsernameElementFactory);
    var usernameReplacerFactory = mock(UsernameReplacerFactory);
    var usernameService = mock(UsernameService);

    var usernameElement = mock(Element);
    var usernameReplacer = function () {
    };

    // Given
    when(usernameElementFactory).create().thenReturn(usernameElement);
    when(usernameReplacerFactory).create(usernameElement).thenReturn(usernameReplacer);

    // When
    new UsernameDisplay(usernameElementFactory, usernameReplacerFactory, usernameService).show();

    // Then
    verify(usernameService).getUsername(usernameReplacer);
  });
});