<template>
    <div class="body">
        <page-menu></page-menu>

        <div class="content-container">
            <h1 class="main-heading">{{heading}}</h1>

            <slot></slot>
        </div>
    </div>
</template>

<script>
  import PageMenu from '../../components/PageMenu/PageMenu.vue'

  export default {
    name: 'page-layout',
    props: {
      heading: {
        type: String,
        required: true
      }
    },
    components: { PageMenu }
  }
</script>

<style></style>