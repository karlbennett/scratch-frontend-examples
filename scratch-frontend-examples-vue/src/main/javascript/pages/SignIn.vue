<template>
    <page-layout heading="Sign In">
        <p>
            Sign into your account.
        </p>

        <form class="form" v-on:submit="signIn">
            <div class="input">
                <label for="username">Username</label><input id="username" v-model="username">
            </div>
            <div class="input">
                <label for="password">Password</label><input id="password" type="password" v-model="password">
            </div>
            <div class="input">
                <input type="submit" value="Sign In">
            </div>
        </form>
    </page-layout>
</template>

<script>
  import PageLayout from '../layouts/PageLayout/PageLayout.vue'
  import { signInUser } from '../services/UserService';
  import page from 'page';

  export default {
    name: 'registration',
    data: () => ({
      username: '',
      password: ''
    }),
    components: { PageLayout },
    methods: {
      signIn(event) {
        event.preventDefault();
        signInUser(
          this.username, this.password,
          () => {
            this.$store.dispatch('requestUser');
            page('/');
          },
          () => {
          }
        )
      }
    }
  }
</script>

<style></style>