import { ElMessage } from 'element-plus';
import router from './index.js';
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        const token = localStorage.getItem('token');
        if (token) {
            if (to.path === '/login') {
            } else {
                next();
            }
        } else {
            ElMessage({
                type:"warn",
                message:"未登录，自动跳转至登录界面..."
            })
            next({
                path: '/login'
            })
        }
    } else {
        next() 
    }
})