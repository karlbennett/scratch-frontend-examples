new UsernameDisplay(
  new UsernameElementFactory(document),
  new UsernameReplacerFactory(document),
  new UsernameService(new UsernameHandlerFactory(), new HttpClient(new XMLHttpRequestFactory()))
).show();