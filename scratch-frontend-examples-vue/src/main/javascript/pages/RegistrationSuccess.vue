<template>
    <p class="registration-success-message">
        <page-heading>Welcome</page-heading>

        Your account has been setup. You can now <router-link to="/signIn">Sign In</router-link>.
    </p>
</template>

<script>
  import PageHeading from '../components/PageHeading.vue';

  export default {
    name: 'registration-success',
    components: { PageHeading }
  }
</script>