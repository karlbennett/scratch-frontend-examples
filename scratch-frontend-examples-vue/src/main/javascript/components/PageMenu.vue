<template>
    <div class="header-container">
        <div class="signin" v-if="loaded">
            <template v-if="username">
                <router-link to="/profile">{{username}}</router-link>
                <router-link to="/" v-on:click.native="signOut">Sign Out</router-link>
            </template>
            <template v-else>
                <router-link to="/registration">Register</router-link>
                <router-link to="/signIn">Sign In</router-link>
            </template>
        </div>
        <div class="menu">
            <router-link to="/">Home</router-link>
        </div>
    </div>
</template>

<script>
  import { signOutUser } from '../services/UserService';
  import { mapState } from 'vuex'

  export default {
    name: 'page-menu',
    computed: mapState([
      'username',
      'loaded'
    ]),
    methods: {
      signOut() {
        signOutUser(() => this.$store.dispatch('requestUser'), () => {});
      }
    }
  }
</script>