import fetchMock from 'fetch-mock';
import { registerUser, requestUsername, signInUser, signOutUser } from '../../../main/javascript/services/UserService';

describe('src/test/javascript/services/UserService.spec.js', () => {

  beforeEach(function () {
    fetchMock.restore()
  });

  afterEach(function () {
    fetchMock.restore()
  });

  it('Can request the username', (done) => {

    const success = jasmine.createSpy('success');
    const username = 'some username';

    // Given
    fetchMock.get('glob:**/api/user', { status: 200, body: { username } });

    // When
    requestUsername(success).then(() => {
      // This is an async action so we must also carry out the verify as an async callback.

      // Then
      expect(success).toHaveBeenCalledWith(username);
      done(); // Indicate that the async test has successfully completed.
    });
  });

  it('Can fail to make request a username', (done) => {

    const failure = jasmine.createSpy('failure');
    const error = 'some service error text';

    // Given
    fetchMock.get('glob:**/api/user', { status: 400, body: { error } });

    // When
    requestUsername(undefined, failure).then(() => {

      // Then
      expect(failure).toHaveBeenCalledWith({ status: 400, body: { error }, errorMessage: 'Username request failed.' });
      done();
    });
  });

  it('Can register a new user', (done) => {

    const success = jasmine.createSpy('success');
    const username = 'some username';
    const password = 'some password';

    // Given
    fetchMock.post(
      (url, opts) => {
        return url.endsWith('/api/register') && opts.body === `username=${username}&password=${password}`;
      },
      { status: 200 }
    );

    // When
    registerUser({ username, password }, success).then(() => {

      // Then
      expect(success).toHaveBeenCalled();
      done(); // Indicate that the async test has successfully completed.
    });
  });

  it('Can fail to register a new user', (done) => {

    const failure = jasmine.createSpy('failure');
    const username = 'some username';
    const password = 'some password';
    const error = 'some service error text';

    // Given
    fetchMock.post('glob:**/api/register', { status: 400, body: { error } });

    // When
    registerUser({ username, password }, undefined, failure).then(() => {

      // Then
      expect(failure).toHaveBeenCalledWith({ status: 400, body: { error }, errorMessage: 'Registration failed.' });
      done();
    });
  });

  it('Can sign in a user', (done) => {

    const success = jasmine.createSpy('success');
    const username = 'some username';
    const password = 'some password';

    // Given
    fetchMock.post(
      (url, opts) => {
        return url.endsWith('/api/signIn') && opts.body === `username=${username}&password=${password}`;
      },
      { status: 200 }
    );

    // When
    signInUser(username, password, success).then(() => {

      // Then
      expect(success).toHaveBeenCalled();
      done(); // Indicate that the async test has successfully completed.
    });
  });

  it('Can fail to sign in a user', (done) => {

    const failure = jasmine.createSpy('failure');
    const username = 'some username';
    const password = 'some password';
    const error = 'some service error text';

    // Given
    fetchMock.post('glob:**/api/signIn', { status: 400, body: { error } });

    // When
    signInUser(username, password, undefined, failure).then(() => {

      // Then
      expect(failure).toHaveBeenCalledWith({ status: 400, body: { error }, errorMessage: 'Sign in failed.' });
      done();
    });
  });

  it('Can sign out a user', (done) => {

    const success = jasmine.createSpy('success');

    // Given
    fetchMock.get('glob:**/api/signOut', { status: 200 });


    // When
    signOutUser(success).then(() => {

      // Then
      expect(success).toHaveBeenCalled();
      done(); // Indicate that the async test has successfully completed.
    });
  });

  it('Can fail to sign out a user', (done) => {

    const failure = jasmine.createSpy('failure');
    const error = 'some service error text';

    // Given
    fetchMock.get('glob:**/api/signOut', { status: 400, body: { error } });

    // When
    signOutUser(undefined, failure).then(() => {

      // Then
      expect(failure).toHaveBeenCalledWith({ status: 400, body: { error }, errorMessage: 'Sign out failed.' });
      done();
    });
  });
});