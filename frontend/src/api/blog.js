import request from '../utils/request';
export function list(current,size) {
    return request({
        url: '/blog/list',
        method: 'get',
        params: {
            current,
            size
        }
    })
}
export function detail(id) {
    return request({
        method: "get",
        url: "/blog/detail",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        params: {
            id
        }
    })
}
export function del(id) {
    return request({
        method: "get",
        url: "/blog/delete",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        params: {
            id
        }
    })
}
export function edit(form) {
    return request({
        method: "post",
        url: "/blog/edit",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: {
            ...form
        }
    })
}