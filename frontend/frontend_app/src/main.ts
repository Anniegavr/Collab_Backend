import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router";
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
// import { createPinia } from 'pinia';
// @ts-ignore
import vClickOutside from "click-outside-vue3";
import VCalendar, {Calendar} from "v-calendar";


const app = createApp(App).use(router).use(vClickOutside)
// const pinia = createPinia();
// app.use(pinia);
app.use(VCalendar, {})
app.use(Toast);
app.component('VCalendar', Calendar)
app.mount('#app')
