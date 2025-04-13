import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 路由懒加载
const Home = () => import('../views/Home.vue')
const PatientList = () => import('../views/patient/PatientList.vue')
const PatientDetail = () => import('../views/patient/PatientDetail.vue')
const DoctorList = () => import('../views/doctor/DoctorList.vue')
const DoctorDetail = () => import('../views/doctor/DoctorDetail.vue')
const AppointmentList = () => import('../views/appointment/AppointmentList.vue')
const AppointmentDetail = () => import('../views/appointment/AppointmentDetail.vue')

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/patients',
    name: 'PatientList',
    component: PatientList
  },
  {
    path: '/patients/:id',
    name: 'PatientDetail',
    component: PatientDetail
  },
  {
    path: '/doctors',
    name: 'DoctorList',
    component: DoctorList
  },
  {
    path: '/doctors/:id',
    name: 'DoctorDetail',
    component: DoctorDetail
  },
  {
    path: '/appointments',
    name: 'AppointmentList',
    component: AppointmentList
  },
  {
    path: '/appointments/:id',
    name: 'AppointmentDetail',
    component: AppointmentDetail
  },
  {
    path: '*',
    redirect: '/'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router 