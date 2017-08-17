<template>
    <div class="sign-in">
        <page-heading>Sign In</page-heading>

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
    </div>
</template>

<script>
  import { signInUser } from '../services/UserService';

  export default {
    name: 'registration',
    data: () => ({
      username: '',
      password: ''
    }),
    methods: {
      signIn(event) {
        event.preventDefault();
        signInUser(
          this.username, this.password,
          () => {
            this.$store.dispatch('requestUser');
            this.$router.push('/');
          },
          () => {}
        )
      }
    }
  }
</script>