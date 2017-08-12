import 'isomorphic-fetch';

class FetchError extends Error {
  constructor(message, response) {
    super(message, 'FetchError');
    this.response = response;
  }
}

function validSuccess(response, message) {
  if (!response.ok) {
    throw new FetchError(message, response);
  }
}

function handleFetchFailure(callback) {
  return error => error.response.json().then(
    json => callback({
      status: error.response.status,
      body: json,
      errorMessage: error.message,
    })
  );
}

/**
 * Request the users username and then pass it down into the supplied 'success' callback. Any failures will be
 * passed to the 'failure' callback with an object: { status: INT, body: STRING, errorMessage: STRING }
 */
export const requestUsername = (success, failure) =>
  // We return the promise that results from the async requests to allow users of this method to add further executions
  // to the chain.
  // eslint-disable-next-line no-undef
  fetch('/api/user', { credentials: 'same-origin' }).then((response) => {
    // Fetch doesn't call failure functions for HTTP error codes like other clients.
    validSuccess(response, 'Username request failed.');
    return response.json();
  }).then(json => success(json.username))
    .catch(handleFetchFailure(failure));

/**
 * Register a new users and then run the supplied 'success' callback with no arguments. Any failures will be passed to
 * the 'failure' callback with an object: { status: INT, body: STRING, errorMessage: STRING }
 */
export const registerUser = (user, success, failure) =>
  // eslint-disable-next-line no-undef
  fetch(
    '/api/register',
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded', Accept: 'application/json' },
      body: `username=${user.username}&password=${user.password}`,
      credentials: 'same-origin',
    })
    .then((response) => {
      validSuccess(response, 'Registration failed.');
      return response.text();
    }).then(() => success())
    .catch(handleFetchFailure(failure));

/**
 * Sign in a users and then run the supplied 'success' callback with no arguments. Any failures will be passed to
 * the 'failure' callback with an object: { status: INT, body: STRING, errorMessage: STRING }
 */
export const signInUser = (username, password, success, failure) =>
  // eslint-disable-next-line no-undef
  fetch(
    '/api/signIn',
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded', Accept: 'application/json' },
      body: `username=${username}&password=${password}`,
      credentials: 'same-origin',
    })
    .then((response) => {
      validSuccess(response, 'Sign in failed.');
      return response.text();
    }).then(() => success())
    .catch(handleFetchFailure(failure));

/**
 * Sign out a users and then run the supplied 'success' callback with no arguments. Any failures will be passed to
 * the 'failure' callback with an object: { status: INT, body: STRING, errorMessage: STRING }
 */
export const signOutUser = (success, failure) =>
  // We return the promise that results from the async requests to allow users of this method to add further executions
  // to the chain.
  // eslint-disable-next-line no-undef
  fetch('/api/signOut', { credentials: 'same-origin' }).then((response) => {
    // Fetch doesn't call failure functions for HTTP error codes like other clients.
    validSuccess(response, 'Sign out failed.');
    return response.text();
  }).then(() => success())
    .catch(handleFetchFailure(failure));
