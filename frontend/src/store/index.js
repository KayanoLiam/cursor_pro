import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import doctor from './modules/doctor'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    doctor
  },
  state: {
    patients: [],
    appointments: [],
    currentPatient: null,
    currentAppointment: null,
    loading: false
  },
  getters: {
    getPatientById: (state) => (id) => {
      return state.patients.find(patient => patient.id === id)
    },
    getAppointmentById: (state) => (id) => {
      return state.appointments.find(appointment => appointment.id === id)
    }
  },
  mutations: {
    SET_LOADING(state, status) {
      state.loading = status
    },
    SET_PATIENTS(state, patients) {
      state.patients = patients
    },
    SET_APPOINTMENTS(state, appointments) {
      state.appointments = appointments
    },
    SET_CURRENT_PATIENT(state, patient) {
      state.currentPatient = patient
    },
    SET_CURRENT_APPOINTMENT(state, appointment) {
      state.currentAppointment = appointment
    },
    ADD_PATIENT(state, patient) {
      state.patients.push(patient)
    },
    ADD_APPOINTMENT(state, appointment) {
      state.appointments.push(appointment)
    },
    UPDATE_PATIENT(state, updatedPatient) {
      const index = state.patients.findIndex(p => p.id === updatedPatient.id)
      if (index !== -1) {
        state.patients.splice(index, 1, updatedPatient)
      }
    },
    UPDATE_APPOINTMENT(state, updatedAppointment) {
      const index = state.appointments.findIndex(a => a.id === updatedAppointment.id)
      if (index !== -1) {
        state.appointments.splice(index, 1, updatedAppointment)
      }
    },
    DELETE_PATIENT(state, patientId) {
      state.patients = state.patients.filter(p => p.id !== patientId)
    },
    DELETE_APPOINTMENT(state, appointmentId) {
      state.appointments = state.appointments.filter(a => a.id !== appointmentId)
    }
  },
  actions: {
    // 患者相关
    async fetchPatients({ commit }) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.get('/api/patients')
        commit('SET_PATIENTS', response.data)
      } catch (error) {
        console.error('获取患者列表失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchPatientById({ commit }, patientId) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.get(`/api/patients/${patientId}`)
        commit('SET_CURRENT_PATIENT', response.data)
        return response.data
      } catch (error) {
        console.error(`获取患者ID=${patientId}失败:`, error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async createPatient({ commit }, patient) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.post('/api/patients', patient)
        commit('ADD_PATIENT', response.data)
        return response.data
      } catch (error) {
        console.error('创建患者失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async updatePatient({ commit }, patient) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.put(`/api/patients/${patient.id}`, patient)
        commit('UPDATE_PATIENT', response.data)
        return response.data
      } catch (error) {
        console.error(`更新患者ID=${patient.id}失败:`, error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async deletePatient({ commit }, patientId) {
      commit('SET_LOADING', true)
      try {
        await axios.delete(`/api/patients/${patientId}`)
        commit('DELETE_PATIENT', patientId)
      } catch (error) {
        console.error(`删除患者ID=${patientId}失败:`, error)
      } finally {
        commit('SET_LOADING', false)
      }
    },

    // 预约相关
    async fetchAppointments({ commit }) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.get('/api/appointments')
        commit('SET_APPOINTMENTS', response.data)
      } catch (error) {
        console.error('获取预约列表失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchAppointmentById({ commit }, appointmentId) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.get(`/api/appointments/${appointmentId}`)
        commit('SET_CURRENT_APPOINTMENT', response.data)
        return response.data
      } catch (error) {
        console.error(`获取预约ID=${appointmentId}失败:`, error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async createAppointment({ commit }, appointment) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.post('/api/appointments', appointment)
        commit('ADD_APPOINTMENT', response.data)
        return response.data
      } catch (error) {
        console.error('创建预约失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async updateAppointment({ commit }, appointment) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.put(`/api/appointments/${appointment.id}`, appointment)
        commit('UPDATE_APPOINTMENT', response.data)
        return response.data
      } catch (error) {
        console.error(`更新预约ID=${appointment.id}失败:`, error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async cancelAppointment({ commit }, appointmentId) {
      commit('SET_LOADING', true)
      try {
        await axios.patch(`/api/appointments/${appointmentId}/cancel`)
        
        // 更新本地状态
        const updatedAppointment = {
          id: appointmentId,
          status: '已取消'
        }
        commit('UPDATE_APPOINTMENT', updatedAppointment)
        return true
      } catch (error) {
        console.error(`取消预约ID=${appointmentId}失败:`, error)
        return false
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async deleteAppointment({ commit }, appointmentId) {
      commit('SET_LOADING', true)
      try {
        await axios.delete(`/api/appointments/${appointmentId}`)
        commit('DELETE_APPOINTMENT', appointmentId)
        return true
      } catch (error) {
        console.error(`删除预约ID=${appointmentId}失败:`, error)
        return false
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchAppointmentsByDoctorId({ commit }, doctorId) {
      commit('SET_LOADING', true)
      try {
        const response = await axios.get(`/api/appointments/doctor/${doctorId}`)
        return response.data
      } catch (error) {
        console.error(`获取医生ID=${doctorId}的预约列表失败:`, error)
        return []
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async updateAppointmentStatus({ commit }, { id, status }) {
      commit('SET_LOADING', true)
      try {
        await axios.patch(`/api/appointments/${id}/status?status=${status}`)
        
        // 更新本地状态
        const updatedAppointment = {
          id: id,
          status: status
        }
        commit('UPDATE_APPOINTMENT', updatedAppointment)
        return true
      } catch (error) {
        console.error(`更新预约ID=${id}状态失败:`, error)
        return false
      } finally {
        commit('SET_LOADING', false)
      }
    }
  }
}) 