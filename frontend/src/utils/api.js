import axios from 'axios'

// 基础URL配置
axios.defaults.baseURL = '/api'

// 患者相关API
export const PatientAPI = {
  // 获取所有患者
  getAll: () => axios.get('/patients'),
  
  // 根据ID获取患者
  getById: (id) => axios.get(`/patients/${id}`),
  
  // 根据姓名搜索患者
  searchByName: (name) => axios.get(`/patients/search?name=${name}`),
  
  // 创建患者
  create: (patientData) => axios.post('/patients', patientData),
  
  // 更新患者信息
  update: (id, patientData) => axios.put(`/patients/${id}`, patientData),
  
  // 删除患者
  delete: (id) => axios.delete(`/patients/${id}`)
}

// 医生相关API
export const DoctorAPI = {
  // 获取所有医生
  getAll: () => axios.get('/doctors'),
  
  // 根据ID获取医生
  getById: (id) => axios.get(`/doctors/${id}`),
  
  // 根据科室查询医生
  getByDepartment: (department) => axios.get(`/doctors/department/${department}`),
  
  // 根据姓名搜索医生
  searchByName: (name) => axios.get(`/doctors/search?name=${name}`),
  
  // 创建医生
  create: (doctorData) => axios.post('/doctors', doctorData),
  
  // 更新医生信息
  update: (id, doctorData) => axios.put(`/doctors/${id}`, doctorData),
  
  // 删除医生
  delete: (id) => axios.delete(`/doctors/${id}`)
}

// 预约相关API
export const AppointmentAPI = {
  // 获取所有预约
  getAll: () => axios.get('/appointments'),
  
  // 根据ID获取预约
  getById: (id) => axios.get(`/appointments/${id}`),
  
  // 获取患者的所有预约
  getByPatientId: (patientId) => axios.get(`/appointments/patient/${patientId}`),
  
  // 获取医生的所有预约
  getByDoctorId: (doctorId) => axios.get(`/appointments/doctor/${doctorId}`),
  
  // 创建预约
  create: (appointmentData) => axios.post('/appointments', appointmentData),
  
  // 更新预约信息
  update: (id, appointmentData) => axios.put(`/appointments/${id}`, appointmentData),
  
  // 更新预约状态
  updateStatus: (id, status) => axios.patch(`/appointments/${id}/status?status=${status}`),
  
  // 取消预约
  cancel: (id) => axios.patch(`/appointments/${id}/cancel`)
} 