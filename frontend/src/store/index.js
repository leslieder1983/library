import { createStore } from 'vuex'

export default createStore({
  state: {
    token:localStorage.getItem('token'),
    userInfo:JSON.parse(localStorage.getItem('userInfo')) ,
    dept:[],

  },
  mutations: {
    SET_Token(pre,value){
      pre.token=value;
      localStorage.setItem('token',value);
    },
    SET_UserInfo(state,value){
      console.log(state,value);
      state.userInfo=value;
      localStorage.setItem('userInfo',JSON.stringify(value));
    },
    removeInfo(state){
      state.userInfo={};
      state.token='';
      localStorage.removeItem('token');
      localStorage.removeItem('userInfo');
    },
    setDept(state,value){
      state.dept=value;
    },
    remember(state,value){
      localStorage.setItem("username",value.username);
      localStorage.setItem("password",value.password);
      localStorage.setItem("remember",true);
    },
    unRemember(state,value){
      localStorage.removeItem ("username");
      localStorage.removeItem("password");
      localStorage.setItem("remember",false);
    },
  },
  getters:{
    getToken(state){
        return state.token;
    },
    getUser(state){
      return state.userInfo;
    },
    getDept(state){
      return state.dept;
    }
  },
  actions: {
  },
  modules: {
  }
})
