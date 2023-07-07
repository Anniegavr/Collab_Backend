<template>
  <NavigationBar :title="pageTitle"/>
  <FloatingMenuButton/>
  <router-view />
  <vue-toastification />
</template>

<script>
import NavigationBar from "./components/NavigationBar.vue";
import FloatingMenuButton from "./components/FloatingMenuButton.vue";
import { useRoute } from 'vue-router';
import {computed, createApp} from 'vue';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import router from "./index.ts";

export default {
  name: 'App',
  router,
  components: {
    NavigationBar,
    FloatingMenuButton,
  },
  setup() {
    const route = useRoute();

    const pageTitle = computed(() => {
      switch (route.path) {
        case '/':
          return 'Home';
        case '/login':
          return 'Login';
          // ... rest of the cases

        default:
          return 'Not a course';
      }
    });

    return {
      pageTitle
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
