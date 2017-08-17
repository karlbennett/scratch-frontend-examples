import Vue from 'vue';
import Vuex, { mapState } from 'vuex';
import VueRouter from 'vue-router';
import mutations from './store/mutations';
import actions from './store/actions';

import PageMenu from './components/PageMenu.vue';
import PageHeading from './components/PageHeading.vue';
import Home from './pages/Home.vue';
import SignIn from './pages/SignIn.vue';
import Registration from './pages/Registration.vue';
import RegistrationSuccess from './pages/RegistrationSuccess.vue';
import Profile from './pages/Profile.vue';
import NotFound from './pages/NotFound.vue';

import './main.scss';

Vue.use(Vuex);
Vue.use(VueRouter);

// We register this component globally so that it can be used within all the page components.
Vue.component('page-heading', PageHeading);

const store = new Vuex.Store({
  state: {
    heading: '',
    username: '',
    loaded: false
  },
  mutations,
  actions
});

const router = new VueRouter({
  mode: 'history',
  routes: [
    { path: '/', component: Home },
    { path: '/signIn', component: SignIn },
    { path: '/registration', component: Registration },
    { path: '/registrationSuccess', component: RegistrationSuccess },
    { path: '/profile', component: Profile },
    { path: '*', component: NotFound },
  ]
});

// eslint-disable-next-line no-new
new Vue({
  el: '#vue-app',
  store,
  router,
  computed: mapState([
    'heading'
  ]),
  components: { PageMenu }
});

store.dispatch('requestUser');
