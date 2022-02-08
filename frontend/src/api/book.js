import request from '../utils/request';
/**
 * 
 * @param {当前页数} current 
 * @param {*} page 页面大小
 * @returns 分页数据
 */
export function list(current,page) {
    return request({
        url: '/book/list',
        method: "get",
        params: {
            current:current,
            size:page
        }
    })
}

export function all() {
    return request({
        url: '/book/all',
        method: "get",
       
    })
}
export function add(form) {
    return request({
        method: "post",
        url: "/book/add",
        data: {
            ...form
        }
    })
}
export function detail(id) {
    return request({
        method: "get",
        url: "/book/detail",
        params: {
          id
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
//           url: '/book/page',
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
        url: "/book/update",
        data: {
            ...form
        }
    })
}
export function del(id) {
    return request({
        method: "get",
        url: "/book/delete",
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
        url: "/book/deleteAll",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: arr
    })
}
/**
 * 需要bookName与isbn
 * @param {*} form 
 * @returns 
 */
export function query(form) {
    return request({
        method: "post",
        url: "/book/query",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: {
            ...form
        }
    })
}