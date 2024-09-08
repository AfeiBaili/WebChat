import {createApp} from 'vue'
import App from './App.vue'
import '/src/assets/style.css'
import {createPinia} from "pinia";
import {router} from "./router/router.js";
import "/src/assets/iconfont/iconfont.css"

const app = createApp(App);
app.use(router)
app.use(createPinia())
app.mount('#app')