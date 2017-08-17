import * as UserService from '../../../main/javascript/services/UserService';
import actions from '../../../main/javascript/store/actions';

describe('src/test/javascript/store/actions.spec.js', () => {

  it('Can request a user', () => {

    const username = 'some username';
    const context = {
      commit: () => {
      }
    };
    const state = {};

    // Given
    spyOn(UserService, 'requestUsername').and.callFake(success => success(username));
    spyOn(context, 'commit').and.callFake((name, callback) => callback(state));

    // When
    actions.requestUser(context);

    // Then
    expect(context.commit).toHaveBeenCalledWith('callback', jasmine.any(Function));
    expect(state).toEqual({ username, loaded: true });
  });

  it('Can fail to request a user', () => {

    const context = {
      commit: () => {
      }
    };
    const state = {};

    // Given
    spyOn(UserService, 'requestUsername').and.callFake((success, failure) => failure());
    spyOn(context, 'commit').and.callFake((name, callback) => callback(state));

    // When
    actions.requestUser(context);

    // Then
    expect(context.commit).toHaveBeenCalledWith('callback', jasmine.any(Function));
    expect(state).toEqual({ username: '', loaded: true });
  });

  it('Can set the page heading', () => {

    const context = {
      commit: () => {
      }
    };
    const heading = 'some heading';
    const state = {};

    // Given
    spyOn(context, 'commit').and.callFake((name, callback) => callback(state));

    // When
    actions.updateHeading(context, { heading });

    // Then
    expect(context.commit).toHaveBeenCalledWith('callback', jasmine.any(Function));
    expect(state).toEqual({ heading });
  });
});