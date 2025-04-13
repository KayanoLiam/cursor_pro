<template>
  <div class="doctor-detail">
    <div class="page-header">
      <h2>医生详情</h2>
      <div class="actions">
        <el-button @click="goBack">返回</el-button>
        <el-button type="primary" @click="handleEdit" v-if="doctor">编辑</el-button>
      </div>
    </div>

    <el-card v-loading="loading">
      <div v-if="doctor" class="detail-content">
        <el-row :gutter="20">
          <el-col :span="18">
            <el-row :gutter="20">
              <el-col :span="12">
                <div class="info-item">
                  <div class="label">医生ID：</div>
                  <div class="value">{{ doctor.id }}</div>
                </div>
                <div class="info-item">
                  <div class="label">姓名：</div>
                  <div class="value">{{ doctor.name }}</div>
                </div>
                <div class="info-item">
                  <div class="label">性别：</div>
                  <div class="value">{{ doctor.gender }}</div>
                </div>
                <div class="info-item">
                  <div class="label">科室：</div>
                  <div class="value">{{ doctor.department }}</div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="info-item">
                  <div class="label">职位：</div>
                  <div class="value">{{ doctor.position }}</div>
                </div>
                <div class="info-item">
                  <div class="label">电话：</div>
                  <div class="value">{{ doctor.phone }}</div>
                </div>
                <div class="info-item">
                  <div class="label">专长：</div>
                  <div class="value">{{ doctor.specialty }}</div>
                </div>
              </el-col>
            </el-row>
            
            <el-divider></el-divider>
            
            <div class="info-block">
              <div class="section-title">医生简介</div>
              <div class="description">{{ doctor.introduction || '暂无简介' }}</div>
            </div>
          </el-col>
          
          <el-col :span="6">
            <div class="avatar-container">
              <el-avatar :size="120" icon="el-icon-user-solid"></el-avatar>
              <div class="doctor-name">{{ doctor.name }}</div>
              <div class="doctor-title">{{ doctor.position }}</div>
              <div class="doctor-department">{{ doctor.department }}</div>
            </div>
          </el-col>
        </el-row>
        
        <el-divider></el-divider>
        
        <div class="appointments-section">
          <div class="section-header">
            <div class="section-title">预约列表</div>
            <el-button size="small" type="primary" @click="handleNewAppointment">新建预约</el-button>
          </div>
          
          <el-table
            v-loading="loadingAppointments"
            :data="doctorAppointments"
            style="width: 100%"
            border>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="patientName" label="患者姓名" width="120"></el-table-column>
            <el-table-column label="预约时间" width="180">
              <template slot-scope="scope">
                {{ formatDateTime(scope.row.appointmentTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="viewAppointment(scope.row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
          
          <div v-if="doctorAppointments.length === 0 && !loadingAppointments" class="no-data">
            <el-empty description="暂无预约记录"></el-empty>
          </div>
        </div>
      </div>
      <div v-else-if="!loading" class="not-found">
        <el-empty description="未找到医生信息"></el-empty>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { formatDateTime } from '@/utils/date'

export default {
  name: 'DoctorDetail',
  
  data() {
    return {
      loadingAppointments: false,
      doctorAppointments: []
    }
  },
  
  computed: {
    ...mapState({
      currentDoctor: state => state.currentDoctor,
      loading: state => state.loading
    }),
    
    doctorId() {
      return parseInt(this.$route.params.id)
    },
    
    doctor() {
      return this.currentDoctor
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
      this.$router.push('/doctors')
    },
    
    handleEdit() {
      this.$router.push({
        path: '/doctors',
        query: { edit: this.doctorId }
      })
    },
    
    async fetchDoctorAppointments() {
      this.loadingAppointments = true
      try {
        const response = await this.$store.dispatch('fetchAppointmentsByDoctorId', this.doctorId)
        this.doctorAppointments = response || []
      } catch (error) {
        this.$message.error('获取预约列表失败')
        console.error(error)
      } finally {
        this.loadingAppointments = false
      }
    },
    
    handleNewAppointment() {
      this.$router.push({
        path: '/appointments',
        query: { newAppointment: true, doctorId: this.doctorId }
      })
    },
    
    viewAppointment(appointment) {
      this.$router.push(`/appointments/${appointment.id}`)
    }
  },
  
  created() {
    // 获取医生详情
    this.$store.dispatch('fetchDoctorById', this.doctorId)
    // 获取该医生的所有预约
    this.fetchDoctorAppointments()
  }
}
</script>

<style scoped>
.doctor-detail {
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

.avatar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #f8f8f8;
}

.doctor-name {
  font-size: 18px;
  font-weight: bold;
  margin-top: 15px;
}

.doctor-title {
  font-size: 14px;
  color: #606266;
  margin-top: 5px;
}

.doctor-department {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.section-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #303133;
}

.description {
  padding: 15px;
  background-color: #f8f8f8;
  border-radius: 4px;
  min-height: 80px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.appointments-section {
  margin-top: 20px;
}

.not-found {
  padding: 40px 0;
  text-align: center;
}

.no-data {
  margin: 20px 0;
}
</style> 