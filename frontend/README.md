# 医院管理系统 - 前端

这是医院管理系统的前端部分，使用Vue.js开发。

## 技术栈

- Vue 2.x
- Vuex
- Vue Router
- Axios
- Element UI

## 项目设置与运行

### 安装依赖
```
cd frontend
npm install
```

### 启动开发服务器
```
npm run serve
```
这将在端口9090上启动开发服务器，访问 http://localhost:9090

### 编译生产版本
```
npm run build
```

## 项目结构

```
frontend/
├── public/            # 静态资源
├── src/
│   ├── assets/        # 图片等资源
│   ├── components/    # 可复用组件
│   ├── router/        # 路由配置
│   ├── store/         # Vuex状态管理
│   ├── utils/         # 工具函数
│   ├── views/         # 页面组件
│   ├── App.vue        # 根组件
│   └── main.js        # 入口文件
├── .eslintrc.js       # ESLint配置
├── babel.config.js    # Babel配置
├── package.json       # 依赖和脚本
└── vue.config.js      # Vue配置
```

## 功能模块

- **患者管理**: 添加、查询、编辑和删除患者信息
- **医生管理**: 添加、查询、编辑和删除医生信息
- **预约管理**: 创建、查询、编辑和取消预约

## 接口配置

前端使用Axios与后端进行通信，接口配置位于`src/utils/api.js`文件中。在开发环境中，使用了代理配置转发请求到后端。

## 开发注意事项

1. 本项目配置了代理，开发时API请求会转发到`http://localhost:8080/hospital`
2. 确保后端服务已启动并正常运行
3. Element UI组件库已集成，可直接使用 