import axios from 'axios'

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
    commit('SET_DOCTORS', []) // 先清空列表，防止旧数据影响
    
    try {
      // 处理查询参数
      const params = { ...query }
      let url = '/api/doctors'
      
      // 如果有关键词搜索
      if (params.keyword && params.keyword.trim() !== '') {
        // 使用搜索接口
        url = '/api/doctors/search'
        params.name = params.keyword
      }
      
      const response = await axios({
        url,
        method: 'get',
        params
      })
      
      // 安全地处理响应数据
      let doctors = []
      let total = 0
      
      if (response && response.data) {
        // 尝试不同的响应数据格式
        if (Array.isArray(response.data)) {
          // 直接是数组
          doctors = response.data
          total = response.data.length
        } else if (response.data.content && Array.isArray(response.data.content)) {
          // 分页格式
          doctors = response.data.content
          total = response.data.totalElements || doctors.length
        } else if (response.data.list && Array.isArray(response.data.list)) {
          // 另一种分页格式
          doctors = response.data.list
          total = response.data.total || doctors.length
        } else {
          // 其他可能的格式
          doctors = response.data
          total = 1
        }
      }
      
      // 确保 doctors 是数组
      if (!Array.isArray(doctors)) {
        doctors = doctors ? [doctors] : []
      }
      
      commit('SET_DOCTORS', doctors)
      commit('SET_TOTAL_DOCTORS', total || 0)
      return { data: doctors, total }
    } catch (error) {
      console.error('获取医生列表失败:', error)
      commit('SET_DOCTORS', [])
      commit('SET_TOTAL_DOCTORS', 0)
      throw error
    } finally {
      commit('SET_LOADING', false)
    }
  },

  // 获取单个医生信息
  async fetchDoctor({ commit }, id) {
    commit('SET_LOADING', true)
    try {
      const response = await axios({
        url: `/api/doctors/${id}`,
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
      const response = await axios({
        url: '/api/doctors',
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
      const response = await axios({
        url: `/api/doctors/${data.id}`,
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
  async deleteDoctor({ commit }, id) {
    // 尝试多个可能的API路径
    const apiPaths = [
      `/api/doctors/${id}`,          // 标准RESTful路径
      `/api/doctor/${id}`,           // 单数形式
      `/api/doctors/delete/${id}`    // 显式删除路径
    ];
    
    let lastError = null;
    
    // 依次尝试每个路径
    for (const apiPath of apiPaths) {
      try {
        // 添加详细的请求配置
        const response = await axios({
          url: apiPath,
          method: 'delete',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          // 确保请求超时足够长
          timeout: 10000
        });
        
        // 检查响应状态
        if (response.status >= 200 && response.status < 300) {
          // 删除成功，更新本地数据
          commit('REMOVE_DOCTOR', id);
          console.log(`删除医生成功，使用路径: ${apiPath}`);
          return { success: true, message: '删除成功' };
        }
      } catch (error) {
        console.log(`尝试路径 ${apiPath} 失败:`, error.message);
        lastError = error;
        // 继续尝试下一个路径
        continue;
      }
    }
    
    // 所有路径都失败了
    console.error('所有删除尝试均失败:', lastError);
    let errorMessage = '删除失败';
    
    if (lastError.response) {
      // 服务器返回了错误响应
      console.error('错误状态:', lastError.response.status);
      console.error('错误数据:', lastError.response.data);
      errorMessage = `删除失败: ${lastError.response.status} ${lastError.response.data?.message || ''}`;
    } else if (lastError.request) {
      // 请求发送成功，但没有收到响应
      console.error('未收到响应:', lastError.request);
      errorMessage = '删除失败: 服务器未响应';
    } else {
      // 请求配置有误
      console.error('请求错误:', lastError.message);
      errorMessage = `删除失败: ${lastError.message}`;
    }
    
    return { success: false, message: errorMessage };
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
  },
  REMOVE_DOCTOR(state, id) {
    state.doctors = state.doctors.filter(doctor => doctor.id !== id)
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
} 