<template>
    <page-layout heading="Registration">
        <p>
            Register a new account with your choice of username and password.
        </p>

        <form class="form" v-on:submit="register">
            <div class="input">
                <label for="username">Username</label><input id="username" v-model="username">
            </div>
            <div class="input">
                <label for="password">Password</label><input id="password" type="password" v-model="password">
            </div>
            <div class="input">
                <input type="submit" value="Register">
            </div>
        </form>
    </page-layout>
</template>

<script>
  import PageLayout from '../layouts/PageLayout/PageLayout.vue'
  import { registerUser } from '../services/UserService';
  import page from 'page';

  export default {
    name: 'registration',
    data: () => ({
      username: '',
      password: ''
    }),
    components: { PageLayout },
    methods: {
      register(event) {
        event.preventDefault();
        registerUser(
          { username: this.username, password: this.password },
          () => page('/registrationSuccess'),
          () => {
          }
        )
      }
    }
  }
</script>