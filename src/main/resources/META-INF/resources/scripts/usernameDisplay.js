function UsernameDisplay(usernameElementFactory, usernameReplacerFactory, usernameService) {
  this.usernameElementFactory = usernameElementFactory;
  this.usernameReplacerFactory = usernameReplacerFactory;
  this.usernameService = usernameService;
}

UsernameDisplay.prototype.show = function () {
  this.usernameService.getUsername(this.usernameReplacerFactory.create(this.usernameElementFactory.create()));
};

function UsernameElementFactory(document) {
  this.document = document;
}

UsernameElementFactory.prototype.create = function () {
  var username = this.document.createElement('a');
  username.setAttribute('class', 'username');

  var signOut = this.document.createElement('a');
  signOut.setAttribute('href', '/signOut');
  signOut.appendChild(this.document.createTextNode('Sign Out'));

  var div = this.document.createElement('div');
  div.setAttribute('class', 'signin');
  div.appendChild(username);
  div.appendChild(signOut);

  return div;
};

function UsernameReplacerFactory(document) {
  this.document = document;
}

UsernameReplacerFactory.prototype.create = function (usernameElement) {
  var document = this.document;
  return function (username) {
    var anchor = usernameElement.getElementsByTagName('a')[0];
    anchor.setAttribute('href', '/profile');
    anchor.appendChild(document.createTextNode(username));

    var signin = document.getElementsByClassName('signin')[0];
    signin.parentElement.replaceChild(usernameElement, signin);
  };
};

function UsernameService(usernameHandlerFactory, client) {
  this.usernameHandlerFactory = usernameHandlerFactory;
  this.client = client;
}

UsernameService.prototype.getUsername = function (usernameReplacer) {
  this.client.path('/username').accept('application/json').get(this.usernameHandlerFactory.create(usernameReplacer));
};

function UsernameHandlerFactory() {
}

UsernameHandlerFactory.prototype.create = function (usernameReplacer) {
  return function (response) {
    var body = response.body();
    if (body.username) {
      usernameReplacer(body.username)
    }
  };
};

function HttpClient(xmlHttpRequestFactory) {
  this.xmlHttpRequestFactory = xmlHttpRequestFactory;
}

HttpClient.prototype.path = function (path) {
  this._path = path;
  return this;
};

HttpClient.prototype.accept = function (accept) {
  this._accept = accept;
  return this;
};

HttpClient.prototype.get = function (responseHandler) {
  var request = this.xmlHttpRequestFactory.create();
  if (request.overrideMimeType) {
    request.overrideMimeType(this._accept);
  }
  var path = this._path;
  request.onreadystatechange = function () {
    function isRedirect(request) {
      var responseURL = request.responseURL;
      return responseURL !== undefined && responseURL !== null && !responseURL.endsWith(path)
    }

    if (request.readyState !== 4 || isRedirect(request)) {
      return;
    }

    function parse(body) {
      var contentType = request.getResponseHeader('Content-Type');
      if (contentType && contentType.indexOf('application/json') > -1) {
        return JSON.parse(request.responseText);
      }
      return body;
    }

    if (request.status === 200) {
      responseHandler(new Response(parse(request.responseText)));
    }
  };
  request.open('GET', this._path, true);
  request.send();
};

function Response(body) {
  this._body = body;
}

Response.prototype.body = function () {
  return this._body;
};

function XMLHttpRequestFactory() {
}

XMLHttpRequestFactory.prototype.create = function () {
  return new XMLHttpRequest();
};