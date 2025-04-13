<template>
  <div class="appointment-detail">
    <div class="page-header">
      <h2>预约详情</h2>
      <div class="actions">
        <el-button @click="goBack">返回</el-button>
        <el-button type="primary" @click="handleEdit" v-if="appointment">编辑</el-button>
      </div>
    </div>

    <el-card v-loading="loading">
      <div v-if="appointment" class="detail-content">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="info-item">
              <div class="label">预约ID：</div>
              <div class="value">{{ appointment.id }}</div>
            </div>
            <div class="info-item">
              <div class="label">患者姓名：</div>
              <div class="value">{{ appointment.patientName }}</div>
            </div>
            <div class="info-item">
              <div class="label">医生姓名：</div>
              <div class="value">{{ appointment.doctorName }}</div>
            </div>
            <div class="info-item">
              <div class="label">科室：</div>
              <div class="value">{{ appointment.doctorDepartment }}</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="info-item">
              <div class="label">预约时间：</div>
              <div class="value">{{ formatDateTime(appointment.appointmentTime) }}</div>
            </div>
            <div class="info-item">
              <div class="label">状态：</div>
              <div class="value">
                <el-tag :type="getStatusType(appointment.status)">{{ appointment.status }}</el-tag>
                <el-button 
                  v-if="appointment.status === '等待中'" 
                  size="mini" 
                  type="text" 
                  @click="showStatusDialog">
                  更改状态
                </el-button>
              </div>
            </div>
            <div class="info-item" v-if="appointment.status === '等待中'">
              <el-button type="warning" size="small" @click="handleCancel">取消预约</el-button>
            </div>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <div class="info-block">
          <div class="label">预约描述：</div>
          <div class="description">{{ appointment.description || '无' }}</div>
        </div>
        
        <el-divider></el-divider>
        
        <div class="related-info">
          <h3>相关信息</h3>
          <el-tabs type="border-card">
            <el-tab-pane label="患者信息">
              <el-button 
                type="text" 
                size="small" 
                icon="el-icon-view" 
                @click="viewPatientInfo">
                查看患者详情
              </el-button>
              <div v-if="patient" class="inner-info">
                <div class="info-item">
                  <div class="label">姓名：</div>
                  <div class="value">{{ patient.name }}</div>
                </div>
                <div class="info-item">
                  <div class="label">性别：</div>
                  <div class="value">{{ patient.gender }}</div>
                </div>
                <div class="info-item">
                  <div class="label">年龄：</div>
                  <div class="value">{{ patient.age }}</div>
                </div>
                <div class="info-item">
                  <div class="label">电话：</div>
                  <div class="value">{{ patient.phone }}</div>
                </div>
              </div>
              <div v-else-if="loadingPatient" class="loading-placeholder">
                <el-skeleton :rows="4" animated />
              </div>
            </el-tab-pane>
            <el-tab-pane label="医生信息">
              <el-button 
                type="text" 
                size="small" 
                icon="el-icon-view" 
                @click="viewDoctorInfo">
                查看医生详情
              </el-button>
              <div v-if="doctor" class="inner-info">
                <div class="info-item">
                  <div class="label">姓名：</div>
                  <div class="value">{{ doctor.name }}</div>
                </div>
                <div class="info-item">
                  <div class="label">科室：</div>
                  <div class="value">{{ doctor.department }}</div>
                </div>
                <div class="info-item">
                  <div class="label">职位：</div>
                  <div class="value">{{ doctor.position }}</div>
                </div>
                <div class="info-item">
                  <div class="label">专长：</div>
                  <div class="value">{{ doctor.specialty }}</div>
                </div>
              </div>
              <div v-else-if="loadingDoctor" class="loading-placeholder">
                <el-skeleton :rows="4" animated />
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <div v-else-if="!loading" class="not-found">
        <el-empty description="未找到预约信息"></el-empty>
      </div>
    </el-card>
    
    <!-- 状态修改对话框 -->
    <el-dialog title="修改预约状态" :visible.sync="statusDialogVisible" width="30%">
      <el-form :model="statusForm" label-width="80px">
        <el-form-item label="状态">
          <el-select v-model="statusForm.status" placeholder="请选择状态">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="statusDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateStatus">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { formatDateTime } from '@/utils/date'

export default {
  name: 'AppointmentDetail',
  
  data() {
    return {
      loadingPatient: false,
      loadingDoctor: false,
      patient: null,
      doctor: null,
      statusDialogVisible: false,
      statusForm: {
        status: ''
      },
      statusOptions: [
        { label: '等待中', value: '等待中' },
        { label: '已完成', value: '已完成' },
        { label: '已取消', value: '已取消' }
      ]
    }
  },
  
  computed: {
    ...mapState({
      currentAppointment: state => state.currentAppointment,
      loading: state => state.loading
    }),
    
    appointmentId() {
      return parseInt(this.$route.params.id)
    },
    
    appointment() {
      return this.currentAppointment
    }
  },
  
  methods: {
    formatDateTime,
    
    getStatusType(status) {
      switch (status) {
        case '等待中': return 'warning'
        case '已完成': return 'success'
        case '已取消': return 'danger'
        default: return 'info'
      }
    },
    
    goBack() {
      this.$router.push('/appointments')
    },
    
    handleEdit() {
      this.$router.push({
        path: '/appointments',
        query: { edit: this.appointmentId }
      })
    },
    
    showStatusDialog() {
      this.statusForm.status = this.appointment.status
      this.statusDialogVisible = true
    },
    
    async updateStatus() {
      try {
        // 更新状态
        await this.$store.dispatch('updateAppointmentStatus', {
          id: this.appointmentId,
          status: this.statusForm.status
        })
        
        // 刷新数据
        await this.$store.dispatch('fetchAppointmentById', this.appointmentId)
        
        this.$message.success('状态更新成功')
        this.statusDialogVisible = false
      } catch (error) {
        this.$message.error('状态更新失败')
        console.error(error)
      }
    },
    
    async handleCancel() {
      try {
        await this.$confirm('确定要取消此预约吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await this.$store.dispatch('cancelAppointment', this.appointmentId)
        await this.$store.dispatch('fetchAppointmentById', this.appointmentId)
        this.$message.success('预约已取消')
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('取消预约失败')
          console.error(error)
        }
      }
    },
    
    async viewPatientInfo() {
      if (this.patient) return
      
      this.loadingPatient = true
      try {
        const patientId = this.appointment.patientId
        this.patient = await this.$store.dispatch('fetchPatientById', patientId)
      } catch (error) {
        this.$message.error('获取患者信息失败')
        console.error(error)
      } finally {
        this.loadingPatient = false
      }
    },
    
    async viewDoctorInfo() {
      if (this.doctor) return
      
      this.loadingDoctor = true
      try {
        const doctorId = this.appointment.doctorId
        this.doctor = await this.$store.dispatch('fetchDoctorById', doctorId)
      } catch (error) {
        this.$message.error('获取医生信息失败')
        console.error(error)
      } finally {
        this.loadingDoctor = false
      }
    }
  },
  
  created() {
    // 获取预约详情
    this.$store.dispatch('fetchAppointmentById', this.appointmentId)
  }
}
</script>

<style scoped>
.appointment-detail {
  padding: 10px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.detail-content {
  padding: 10px;
}

.info-item {
  display: flex;
  margin-bottom: 15px;
}

.label {
  font-weight: bold;
  color: #606266;
  width: 100px;
}

.value {
  color: #333;
  flex: 1;
}

.info-block {
  margin: 20px 0;
}

.description {
  padding: 10px;
  background-color: #f8f8f8;
  border-radius: 4px;
  margin-top: 10px;
  min-height: 80px;
}

.not-found {
  padding: 40px 0;
  text-align: center;
}

.related-info {
  margin-top: 20px;
}

.inner-info {
  padding: 15px;
  background-color: #f8f8f8;
  border-radius: 4px;
  margin-top: 10px;
}

.loading-placeholder {
  padding: 20px;
}
</style> 