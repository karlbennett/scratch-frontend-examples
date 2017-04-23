describe('Set the page title to contain the username', function () {

  it('Can create a title replacer', function () {

    // Given
    var document = mock(Document);
    var username = 'some username';

    // When
    new TitleReplacerFactory(document).create()(username);

    // Then
    assertThat(document.title, equalTo('Simpe Webapp (' + username + ')'))
  });
});