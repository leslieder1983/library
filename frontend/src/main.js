import {
    createApp
    
} from 'vue'
import App from './App.vue'
import router from './router'
/**登录拦截 */
import "./router/permission";
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
/**图标 */
import Icons from "@/components/icon";



const app= createApp(App).use(store).use(router).use(ElementPlus).use(Icons);

app.mount('#app');

