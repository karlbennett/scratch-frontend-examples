describe('Make HTTP requests', function () {

  it('Can make a json request', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);
    var mimeType = 'application/json';
    var username = 'some username';
    var text = '{"username" : "' + username + '"}';

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    when(request).getResponseHeader('Content-Type').thenReturn('application/json;some=thing');
    when(responseHandler)(anything()).then(function (response) {
      assertThat(response.body(), hasMember('username', username))
    });
    request.readyState = 4;
    request.status = 200;
    request.responseText = text;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).accept(mimeType).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler)(instanceOf(Response));
    verify(request).overrideMimeType(mimeType);
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Will not attempt to override the accept type if it is not supported', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    request.readyState = 4;
    request.status = 200;
    request.overrideMimeType = undefined;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler)(instanceOf(Response));
    verify(request, never()).overrideMimeType(anything());
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Will not try to handle the json request too early', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    request.readyState = 3;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler, never())(anything());
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Will not try to deserialise non json response', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);
    var text = 'some text';

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    when(request).getResponseHeader('Content-Type').thenReturn('something/else');
    when(responseHandler)(anything()).then(function (response) {
      assertThat(response.body(), is(text));
    });
    request.readyState = 4;
    request.responseURL = undefined;
    request.status = 200;
    request.responseText = text;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler)(instanceOf(Response));
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Will not attempt to handle a failed json request', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);
    var text = 'some text';

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    request.readyState = 4;
    request.responseURL = null;
    request.status = 400;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler, never())(anything());
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Will not attempt to handle a redirect', function () {

    var xmlHttpRequestFactory = mock(XMLHttpRequestFactory);

    var path = '/a/path';
    var responseHandler = mockFunction();

    var request = mock(XMLHttpRequest);
    var text = 'some text';

    // Given
    when(xmlHttpRequestFactory).create().thenReturn(request);
    request.readyState = 4;
    request.responseURL = 'http://so.me/where/else';
    request.status = 200;

    // When
    new HttpClient(xmlHttpRequestFactory).path(path).get(responseHandler);
    request.onreadystatechange();

    // Then
    verify(responseHandler, never())(anything());
    verify(request).open('GET', path, true);
    verify(request).send();
  });

  it('Can create an XMLHttpRequest', function () {

    // When
    var actual = new XMLHttpRequestFactory().create();

    // Then
    assertThat(actual, typeOf('object'));
  });

  it('Can create a response', function () {

    // Given
    var body = 'some body';

    // When
    var actual = new Response(body).body();

    // Then
    assertThat(actual, is(body));
  });
});