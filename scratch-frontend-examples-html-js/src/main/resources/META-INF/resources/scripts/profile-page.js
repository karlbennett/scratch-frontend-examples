var usernameElementFactory = new UsernameElementFactory(document);
var usernameService = new UsernameService(new UsernameHandlerFactory(), new HttpClient(new XMLHttpRequestFactory()));
new UsernameDisplay(usernameElementFactory, new TitleReplacerFactory(document), usernameService).show();
new UsernameDisplay(usernameElementFactory, new ProfileHeadingReplacerFactory(document), usernameService).show();