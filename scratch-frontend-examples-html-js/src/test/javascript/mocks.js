function Node() {
}

function Element() {
}

Element.prototype.getElementsByClassName = function () {
};

Element.prototype.getElementsByTagName = function () {
};

Element.prototype.replaceChild = function () {
};

Element.prototype.setAttribute = function () {
};

Element.prototype.appendChild = function () {
};

function Document() {
}

Document.prototype = Element.prototype;

Document.prototype.createElement = function () {
};

Document.prototype.createTextNode = function () {
};

function XMLHttpRequest() {
}

XMLHttpRequest.prototype.overrideMimeType = function () {
};

XMLHttpRequest.prototype.getResponseHeader = function () {
};

XMLHttpRequest.prototype.open = function () {
};

XMLHttpRequest.prototype.send = function () {
};