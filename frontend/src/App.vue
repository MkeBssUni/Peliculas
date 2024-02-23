<script setup>
import Movies from "./modules/movies/views/Movies.vue";
//import FormCaptcha from "./components/FormCaptcha.vue";

components: { Movies}

</script>

<template>
  <div id="app">
    <main>
      <movies /> 
      <!-- <form-captcha /> -->
    </main>
  </div>
</template>

<style scoped></style>
