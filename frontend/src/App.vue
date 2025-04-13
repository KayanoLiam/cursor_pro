<template>
  <div id="app">
    <el-container class="main-container">
      <el-header>
        <div class="logo">医院管理系统</div>
        <div class="header-right">
          <el-menu class="header-menu" mode="horizontal" :router="true" background-color="#409EFF" text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/patients">患者管理</el-menu-item>
            <el-menu-item index="/doctors">医生管理</el-menu-item>
            <el-menu-item index="/appointments">预约管理</el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
      <el-footer>医院管理系统 &copy; 2025</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
  margin: 0;
  padding: 0;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}

.main-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.el-header {
  background-color: #409EFF;
  color: #fff;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

.header-right {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.header-menu {
  border-bottom: none;
}

.el-main {
  flex: 1;
  padding: 20px;
  background-color: #f0f2f5;
}

.el-footer {
  height: 60px !important;
  line-height: 60px;
  text-align: center;
  background-color: #f0f2f5;
  color: #999;
  border-top: 1px solid #e6e6e6;
}
</style> 