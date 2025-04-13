import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.config.productionTip = false

// 使用Element UI组件
Vue.use(ElementUI)

// 配置axios
axios.defaults.baseURL = '/api'
// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 这里可以添加认证信息等
    return config
  },
  error => {
    return Promise.reject(error)
  }
)
// 添加响应拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    // 处理响应错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未授权
          console.error('未授权，请登录')
          break
        case 404:
          // 资源不存在
          console.error('请求的资源不存在')
          break
        default:
          console.error(`服务器错误: ${error.response.status}`)
      }
    } else {
      console.error('网络错误或服务器未响应')
    }
    return Promise.reject(error)
  }
)

// 将axios添加到Vue原型链，方便使用
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app') 