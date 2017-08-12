import Vue from 'vue';
import Vuex from 'vuex';
import page from 'page';
import mutations from './store/mutations';
import actions from './store/actions';
import Home from './pages/Home.vue';
import SignIn from './pages/SignIn.vue';
import Registration from './pages/Registration.vue';
import RegistrationSuccess from './pages/RegistrationSuccess.vue';
import Profile from './pages/Profile.vue';
import NotFound from './pages/NotFound.vue';

import './main.scss';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    username: '',
    loaded: false
  },
  mutations,
  actions
});

// eslint-disable-next-line no-new
const app = new Vue({
  el: '#vue-app',
  store,
  data: {
    ViewComponent: { render: h => h('div', 'loading...') }
  },
  render(h) {
    return h(this.ViewComponent);
  }
});

/* eslint-disable no-return-assign */
page('/', () => app.ViewComponent = Home);
page('/signIn', () => app.ViewComponent = SignIn);
page('/registration', () => app.ViewComponent = Registration);
page('/registrationSuccess', () => app.ViewComponent = RegistrationSuccess);
page('/profile', () => app.ViewComponent = Profile);
page('*', () => app.ViewComponent = NotFound);
/* eslint-enable no-return-assign */
page();

store.dispatch('requestUser');
