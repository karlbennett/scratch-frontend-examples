<script>
  /**
   * This components is just used to set the 'heading' attribute in the global store. This will then set the heading of
   * the current page.
   */
  export default {
    name: 'page-heading',
    created() {
      this.$store.dispatch('updateHeading', { heading: this.$slots.default[0].text });
    },
    // We don't actually want this component to render anything.
    render: () => {
    }
  }
</script>