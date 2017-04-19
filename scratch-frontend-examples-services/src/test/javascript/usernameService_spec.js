describe('Request the username', function () {

  it('Can request the username', function () {

    var client = mock(HttpClient);
    var usernameHandlerFactory = mock(UsernameHandlerFactory);

    var usernameReplacer = mockFunction();

    var pathClient = mock(HttpClient);
    var acceptClient = mock(HttpClient);
    var usernameHandler = mockFunction();

    // Given
    when(usernameHandlerFactory).create(usernameReplacer).thenReturn(usernameHandler);
    when(client).path('/username').thenReturn(pathClient);
    when(pathClient).accept('application/json').thenReturn(acceptClient);

    // When
    new UsernameService(usernameHandlerFactory, client).getUsername(usernameReplacer);

    // Then
    verify(acceptClient).get(usernameHandler);
  });
});