<template>
    <page-layout heading="Welcome">
        <p class="registration-success-message">
            Your account has been setup. You can now <a href="/signIn">Sign In</a>.
        </p>
    </page-layout>
</template>

<script>
  import PageLayout from '../layouts/PageLayout/PageLayout.vue'

  export default {
    name: 'registration-success',
    components: { PageLayout }
  }
</script>

<style></style>