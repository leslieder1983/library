import * as components from '@element-plus/icons-vue';
export default function icon(app) {
    for (const key in components) {
        const componentConfig = components[key];
        app.component(componentConfig.name, componentConfig);
    }
}