<template>
    <p>
        <page-heading>Simple Webapp</page-heading>

        Welcome to this Simple Webapp.
    </p>
</template>

<script>
  import PageHeading from '../components/PageHeading.vue';

  export default {
    name: 'home',
    components: { PageHeading }
  }
</script>