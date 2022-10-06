import './index.scss';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// 1. 需要的组件
import { Button, Overlay, Loading, Divider, ActionSheet, Circle, RadioGroup, Radio, Uploader, CellGroup, Field, Picker, Popup } from 'vant';
// 2. 引入组件样式
import 'vant/lib/index.css';

createApp(App)
    .use(store)
    .use(router)
    .use(Button)
    .use(Overlay)
    .use(Loading)
    .use(Divider)
    .use(ActionSheet)
    .use(Circle)
    .use(RadioGroup)
    .use(Radio)
    .use(Uploader)
    .use(CellGroup)
    .use(Field)
    .use(Picker)
    .use(Popup)
    .mount('#app');
