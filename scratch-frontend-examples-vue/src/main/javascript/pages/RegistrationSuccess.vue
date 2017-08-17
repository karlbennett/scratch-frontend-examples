<template>
    <p class="registration-success-message">
        <page-heading>Welcome</page-heading>

        Your account has been setup. You can now <router-link to="/signIn">Sign In</router-link>.
    </p>
</template>

<script>
  export default {
    name: 'registration-success'
  }
</script>