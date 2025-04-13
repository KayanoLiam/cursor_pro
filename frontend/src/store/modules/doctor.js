import request from '@/utils/request'

// 初始状态
const state = {
  doctors: [],
  currentDoctor: null,
  loading: false,
  totalDoctors: 0
}

// getter
const getters = {
  doctorById: state => id => state.doctors.find(doctor => doctor.id === id)
}

// actions
const actions = {
  // 获取医生列表
  async fetchDoctors({ commit }, query) {
    commit('SET_LOADING', true)
    try {
      const response = await request({
        url: '/doctors',
        method: 'get',
        params: query
      })
      commit('SET_DOCTORS', response.data.content)
      commit('SET_TOTAL_DOCTORS', response.data.totalElements)
      return response
    } catch (error) {
      console.error('获取医生列表失败:', error)
      throw error
    } finally {
      commit('SET_LOADING', false)
    }
  },

  // 获取单个医生信息
  async fetchDoctor({ commit }, id) {
    commit('SET_LOADING', true)
    try {
      const response = await request({
        url: `/doctors/${id}`,
        method: 'get'
      })
      commit('SET_CURRENT_DOCTOR', response.data)
      return response
    } catch (error) {
      console.error('获取医生信息失败:', error)
      throw error
    } finally {
      commit('SET_LOADING', false)
    }
  },

  // 创建医生
  async createDoctor({ commit, dispatch }, data) {
    try {
      const response = await request({
        url: '/doctors',
        method: 'post',
        data
      })
      return response
    } catch (error) {
      console.error('创建医生失败:', error)
      throw error
    }
  },

  // 更新医生信息
  async updateDoctor({ commit, dispatch }, data) {
    try {
      const response = await request({
        url: `/doctors/${data.id}`,
        method: 'put',
        data
      })
      return response
    } catch (error) {
      console.error('更新医生信息失败:', error)
      throw error
    }
  },

  // 删除医生
  async deleteDoctor({ commit, dispatch }, id) {
    try {
      const response = await request({
        url: `/doctors/${id}`,
        method: 'delete'
      })
      return response
    } catch (error) {
      console.error('删除医生失败:', error)
      throw error
    }
  }
}

// mutations
const mutations = {
  SET_DOCTORS(state, doctors) {
    state.doctors = doctors
  },
  SET_CURRENT_DOCTOR(state, doctor) {
    state.currentDoctor = doctor
  },
  SET_LOADING(state, status) {
    state.loading = status
  },
  SET_TOTAL_DOCTORS(state, total) {
    state.totalDoctors = total
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
} 