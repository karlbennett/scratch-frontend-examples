<template>
    <p>
        <page-heading>{{username}}</page-heading>

        This is your profile page. Here you can edit your account settings.
    </p>
</template>

<script>
  import PageHeading from '../components/PageHeading.vue';
  import { mapState } from 'vuex'

  export default {
    name: 'profile',
    components: { PageHeading },
    computed: mapState([
      'username'
    ])
  }
</script>