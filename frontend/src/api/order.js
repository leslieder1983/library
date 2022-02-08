import request from '../utils/request';
/**
 * 
 * @param {当前页数} current 
 * @param {*} page 页面大小
 * @returns 分页数据
 */
export function list(current,page) {
    return request({
        url: '/order/list',
        method: "get",
        params: {
            current:current,
            size:page
        }
    })
}
export function add(form) {
    return request({
        method: "post",
        url: "/order/add",
        data: {
            ...form
        }
    })
}
// /**
//  * 
//  * @param {当前页数} current 
//  * @param {*} page 页面大小
//  * @returns 分页数据
//  */
// export function page(current, page) {
//     return   request({
//           url: '/order/page',
//           method: 'get',
//           params: {
//               current:current,
//               size:page
//           }
//       })
//   }
export function update(form) {
    //需要id
    return request({
        method: "post",
        url: "/order/update",
        data: {
            ...form
        }
    })
}
export function del(id) {
    return request({
        method: "get",
        url: "/order/delete",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        params: {
            id
        }
    })
}
export function deleteAll(arr) {
    return request({
        method: "post",
        url: "/order/deleteAll",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: arr
    })
}
/**
 * 需要bookname与agent
 * @param {*} form 
 * @returns 
 */
export function query(form) {
    return request({
        method: "post",
        url: "/order/query",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: {
            ...form
        }
    })
}