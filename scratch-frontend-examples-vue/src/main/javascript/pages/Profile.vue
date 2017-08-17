<template>
    <p>
        <page-heading>{{username}}</page-heading>

        This is your profile page. Here you can edit your account settings.
    </p>
</template>

<script>
  import { mapState } from 'vuex'

  export default {
    name: 'profile',
    computed: mapState([
      'username'
    ])
  }
</script>