(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-346f7860"],{b067:function(e,t,o){"use strict";o.d(t,"e",(function(){return a})),o.d(t,"b",(function(){return u})),o.d(t,"a",(function(){return c})),o.d(t,"d",(function(){return l})),o.d(t,"g",(function(){return d})),o.d(t,"c",(function(){return i})),o.d(t,"f",(function(){return b}));var n=o("5530"),r=o("b775");function a(e,t){return Object(r["a"])({url:"/book/list",method:"get",params:{current:e,size:t}})}function u(){return Object(r["a"])({url:"/book/all",method:"get"})}function c(e){return Object(r["a"])({method:"post",url:"/book/add",data:Object(n["a"])({},e)})}function l(e){return Object(r["a"])({method:"get",url:"/book/detail",params:{id:e}})}function d(e){return Object(r["a"])({method:"post",url:"/book/update",data:Object(n["a"])({},e)})}function i(e){return Object(r["a"])({method:"get",url:"/book/delete",headers:{Authorization:localStorage.getItem("token")},params:{id:e}})}function b(e){return Object(r["a"])({method:"post",url:"/book/query",headers:{Authorization:localStorage.getItem("token")},data:Object(n["a"])({},e)})}},f057:function(e,t,o){"use strict";o.r(t);var n=o("7a23"),r=Object(n["createTextVNode"])("订购");function a(e,t,o,a,u,c){var l=this,d=Object(n["resolveComponent"])("el-page-header"),i=Object(n["resolveComponent"])("el-option"),b=Object(n["resolveComponent"])("el-select"),f=Object(n["resolveComponent"])("el-form-item"),m=Object(n["resolveComponent"])("el-input-number"),s=Object(n["resolveComponent"])("el-input"),O=Object(n["resolveComponent"])("el-button"),j=Object(n["resolveComponent"])("el-form"),h=Object(n["resolveComponent"])("el-card");return Object(n["openBlock"])(),Object(n["createElementBlock"])("div",null,[Object(n["createVNode"])(h,null,{header:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(d,{title:"返回",content:"教材订购",onBack:t[0]||(t[0]=function(e){return l.$router.back()}),class:"pageHeader"})]})),default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(j,{model:a.formRef,"label-width":"120px"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(f,{label:"教材名称"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(b,{modelValue:a.formRef.bookId,"onUpdate:modelValue":t[1]||(t[1]=function(e){return a.formRef.bookId=e}),filterable:"",placeholder:"输入/选择教材",onChange:c.findMax},{default:Object(n["withCtx"])((function(){return[(Object(n["openBlock"])(!0),Object(n["createElementBlock"])(n["Fragment"],null,Object(n["renderList"])(a.books,(function(e){return Object(n["openBlock"])(),Object(n["createBlock"])(i,{key:e.id,label:e.bookName,value:e.id},null,8,["label","value"])})),128))]})),_:1},8,["modelValue","onChange"])]})),_:1}),Object(n["createVNode"])(f,{label:"教材数量"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(m,{modelValue:a.formRef.count,"onUpdate:modelValue":t[2]||(t[2]=function(e){return a.formRef.count=e}),min:1,onChange:c.getSum,max:u.max},null,8,["modelValue","onChange","max"])]})),_:1}),Object(n["createVNode"])(f,{label:"总价"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(m,{modelValue:a.formRef.sum,"onUpdate:modelValue":t[3]||(t[3]=function(e){return a.formRef.sum=e}),disabled:!0},null,8,["modelValue"])]})),_:1}),Object(n["createVNode"])(f,{label:"订购人"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(s,{modelValue:a.formRef.agent,"onUpdate:modelValue":t[4]||(t[4]=function(e){return a.formRef.agent=e}),disabled:"",placeholder:"订购人",style:{width:"20%"}},null,8,["modelValue"])]})),_:1}),Object(n["createVNode"])(f,{label:"发起订购"},{default:Object(n["withCtx"])((function(){return[Object(n["createVNode"])(O,{type:"primary",onClick:c.order},{default:Object(n["withCtx"])((function(){return[r]})),_:1},8,["onClick"])]})),_:1})]})),_:1},8,["model"])]})),_:1})])}var u=o("b067"),c=o("f8b7"),l=o("c9a1"),d={name:"Subscribe",created:function(){var e,t=this;Object(u["b"])().then((function(e){t.books=e.data.data})),this.formRef.agent=null===(e=this.$store.getters.getUser)||void 0===e?void 0:e.username},data:function(){return{max:100,agent:"",book:""}},setup:function(){var e=Object(n["ref"])([]),t=Object(n["ref"])({bookId:"",bookname:"",agent:"",sum:"",count:0});return{books:e,formRef:t}},props:{},methods:{getSum:function(){this.formRef.sum=this.book.bookPrice*this.formRef.count},findMax:function(e){var t=this;Object(u["d"])(e).then((function(e){var o=e.data.data;t.book=o,t.formRef.bookname=o.bookName,t.max=e.data.data.inventory}))},order:function(){var e=this;Object(c["a"])(this.formRef).then((function(t){l["a"].confirm("教材采购订单登记成功！跳转还是留在当前页面？","回应",{confirmButtonText:"跳转至订单页面",cancelButtonText:"留在当前页面"}).then((function(){e.$router.push({name:"Order"})}))}))}}},i=o("d959"),b=o.n(i);const f=b()(d,[["render",a]]);t["default"]=f},f8b7:function(e,t,o){"use strict";o.d(t,"d",(function(){return a})),o.d(t,"a",(function(){return u})),o.d(t,"b",(function(){return c})),o.d(t,"c",(function(){return l})),o.d(t,"e",(function(){return d}));var n=o("5530"),r=o("b775");function a(e,t){return Object(r["a"])({url:"/order/list",method:"get",params:{current:e,size:t}})}function u(e){return Object(r["a"])({method:"post",url:"/order/add",data:Object(n["a"])({},e)})}function c(e){return Object(r["a"])({method:"get",url:"/order/delete",headers:{Authorization:localStorage.getItem("token")},params:{id:e}})}function l(e){return Object(r["a"])({method:"post",url:"/order/deleteAll",headers:{Authorization:localStorage.getItem("token")},data:e})}function d(e){return Object(r["a"])({method:"post",url:"/order/query",headers:{Authorization:localStorage.getItem("token")},data:Object(n["a"])({},e)})}}}]);
//# sourceMappingURL=chunk-346f7860.9bde2c6b.js.map