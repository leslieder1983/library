import axios from 'axios';
import {
    ElMessage
} from 'element-plus';
import router from '@/router';
import store from '@/store';

//axios封装

const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
     baseURL: 'http://localhost:8081/',
    timeout: 10000
});
service.interceptors.request.use((config) => {
    return config;
})
service.interceptors.response.use(response => {
    let res = response.data;
    if (res.code === 200) {
        return response;
    } else {
        ElMessage({
            showClose: true,
            message: '请求失败',
            type: 'error',
        });
        return Promise.reject(response.data.msg);
    }
}, err => {
    console.log(err);
    if (err.response.data) {
        err.message = err.response.data.msg;
    }
    // //需要登录
    if (err.response.status === 401) {
        //跳转至登录页面
        store.commit('removeInfo');
        router.push('/login');
    }
    ElMessage({
        showClose: true,
        message: err.message,
        type: 'error',
    });
    return Promise.reject(err);

})
export default service;

