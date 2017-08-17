<template>
    <div class="registration">
        <page-heading>Registration</page-heading>

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
    </div>
</template>

<script>
  import PageHeading from '../components/PageHeading.vue';
  import { registerUser } from '../services/UserService';

  export default {
    name: 'registration',
    components: { PageHeading },
    data: () => ({
      username: '',
      password: ''
    }),
    methods: {
      register(event) {
        event.preventDefault();
        registerUser(
          { username: this.username, password: this.password },
          () => this.$router.push('/registrationSuccess'),
          () => {}
        )
      }
    }
  }
</script>