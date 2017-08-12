<template>
    <div class="header-container">
        <div class="signin" v-if="loaded">
            <template v-if="username">
                <a href="/profile">{{username}}</a>
                <a href="/" v-on:click="signOut">Sign Out</a>
            </template>
            <template v-else>
                <a href="/registration">Register</a>
                <a href="/signIn">Sign In</a>
            </template>
        </div>
        <div class="menu">
            <a href="/">Home</a>
        </div>
    </div>
</template>

<script>
  import { signOutUser } from '../../services/UserService';
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