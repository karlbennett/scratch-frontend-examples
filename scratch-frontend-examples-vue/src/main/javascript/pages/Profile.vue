<template>
    <page-layout :heading="username">
        <p>
            This is your profile page. Here you can edit your account settings.
        </p>
    </page-layout>
</template>

<script>
  import { mapState } from 'vuex'
  import PageLayout from '../layouts/PageLayout/PageLayout.vue'

  export default {
    name: 'profile',
    computed: mapState([
      'username'
    ]),
    components: { PageLayout }
  }
</script>

<style></style>