import request from '../utils/request';

export function detail(id) {
   return   request({
         url: '/user/detail',
         method: 'get',
         params: {
            id
         }
     })
 }
export function list(current, page) {
  return   request({
        url: '/user/list',
        method: 'get',
        params: {
            current:current,
            size:page
        }
    })
}
export function login(form){
  console.log(form);
  return request({
       method:"post",
       url:"/login",
       data:{
          ...form
       }
   })
}
export function update(form){
   return request({
        method:"post",
        url:"/user/update",
        data:{
           ...form
        }
    })
 }
 //重置密码
 export function updatePassword(id,oldPassword,newPassword){
   return request({
        method:"post",
        url:"/user/password",
        data:{
          id:id ,
         oldPassword,
         newPassword
        
        }
    })
 }
 /**
  * 新增/注册
  * @param {输入参数}} form 
  * @returns Promise
  */
 export function add(form){
   return request({
        method:"post",
        url:"/user/add",
        data:{
           ...form
        }
    })
 }

  export function del(id){
   return request({
        method:"get",
        url:"/user/delete",
        headers:{
           "Authorization":localStorage.getItem('token'),
        },
        params:{
          id
        }
    })
 }
 export function deleteAll(arr){
   return request({
      method:"post",
      url:"/user/deleteAll",
      headers:{
         "Authorization":localStorage.getItem('token'),
      },
      data:arr
  })
 }

 export function query(form){
  return request({
     method:"post",
     url:"/user/query",
     headers:{
        "Authorization":localStorage.getItem('token'),
     },
     data:{
        ...form
     }
 })
}