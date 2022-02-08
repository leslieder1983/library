import request from '../utils/request';
export function list(current, page) {
    return request({
        url: '/category/list',
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
        url: "/category/add",
        data: {
            ...form
        }
    })
}
/**
 * 
 * @returns all
 */
export function all() {
    return   request({
          url: '/category/all',
          method: 'get',
      })
  }
export function update(form) {
    //需要id
    return request({
        method: "post",
        url: "/category/update",
        data: {
            ...form
        }
    })
}
export function del(id) {
    return request({
        method: "get",
        url: "/category/delete",
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
        url: "/category/deleteAll",
        headers: {
            "Authorization": localStorage.getItem('token'),
        },
        data: arr
    })
}

// export function query(form) {
//     console.log('query',form);
//     return request({
//         method: "post",
//         url: "/category/query",
//         headers: {
//             "Authorization": localStorage.getItem('token'),
//         },
//         data: {
//             ...form
//         }
//     })
// }