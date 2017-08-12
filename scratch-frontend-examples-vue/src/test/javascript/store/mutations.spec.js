import mutations from '../../../main/javascript/store/mutations';

describe('src/test/javascript/store/mutations.spec.js', () => {

  it('Can mutate the store with a callback', () => {

    // Given
    const state = {some: 'random state'};
    const action = jasmine.createSpy('action');

    // When
    mutations.callback(state, action);

    // Then
    expect(action).toHaveBeenCalledWith(state);
  });
});