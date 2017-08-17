import { requestUsername } from '../services/UserService';

export default {
  requestUser(context) {
    requestUsername(
      username => context.commit('callback', (state) => {
        state.username = username;
        state.loaded = true;
      }),
      () => context.commit('callback', (state) => {
        state.username = '';
        state.loaded = true;
      })
    );
  },
  updateHeading(context, { heading }) {
    // eslint-disable-next-line no-return-assign
    context.commit('callback', state => state.heading = heading);
  }
};
