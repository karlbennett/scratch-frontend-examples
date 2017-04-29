var usernameElementFactory = new UsernameElementFactory(document);
var usernameService = new UsernameService(new UsernameHandlerFactory(), new HttpClient(new XMLHttpRequestFactory()));
new UsernameDisplay(usernameElementFactory, new TitleReplacerFactory(window, document), usernameService).show();
new UsernameDisplay(usernameElementFactory, new ProfileHeadingReplacerFactory(window, document), usernameService).show();