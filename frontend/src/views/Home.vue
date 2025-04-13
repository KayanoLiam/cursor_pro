<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="dashboard-card">
          <div slot="header" class="clearfix">
            <span>患者管理</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push('/patients')">查看更多</el-button>
          </div>
          <div class="dashboard-info">
            <i class="el-icon-user dashboard-icon patient-icon"></i>
            <div class="dashboard-count">{{patients.length}}</div>
          </div>
          <div class="dashboard-action">
            <el-button type="primary" size="small" @click="$router.push('/patients')">管理患者</el-button>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="dashboard-card">
          <div slot="header" class="clearfix">
            <span>医生管理</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push('/doctors')">查看更多</el-button>
          </div>
          <div class="dashboard-info">
            <i class="el-icon-s-custom dashboard-icon doctor-icon"></i>
            <div class="dashboard-count">{{doctors.length}}</div>
          </div>
          <div class="dashboard-action">
            <el-button type="primary" size="small" @click="$router.push('/doctors')">管理医生</el-button>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card class="dashboard-card">
          <div slot="header" class="clearfix">
            <span>预约管理</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push('/appointments')">查看更多</el-button>
          </div>
          <div class="dashboard-info">
            <i class="el-icon-date dashboard-icon appointment-icon"></i>
            <div class="dashboard-count">{{appointments.length}}</div>
          </div>
          <div class="dashboard-action">
            <el-button type="primary" size="small" @click="$router.push('/appointments')">管理预约</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" class="recent-section">
      <el-col :span="12">
        <el-card class="recent-card">
          <div slot="header" class="clearfix">
            <span>最近预约</span>
          </div>
          <el-table 
            v-loading="loading"
            :data="recentAppointments" 
            style="width: 100%"
            :empty-text="'暂无预约'"
            size="small">
            <el-table-column prop="patientName" label="患者姓名" width="100"></el-table-column>
            <el-table-column prop="doctorName" label="医生姓名" width="100"></el-table-column>
            <el-table-column prop="appointmentTime" label="预约时间" :formatter="formatDate"></el-table-column>
            <el-table-column prop="status" label="状态" width="80">
              <template slot-scope="scope">
                <el-tag :type="getStatusType(scope.row.status)">{{scope.row.status}}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card class="system-info-card">
          <div slot="header" class="clearfix">
            <span>系统信息</span>
          </div>
          <div class="info-item">
            <span class="label">系统名称:</span>
            <span class="value">医院管理系统</span>
          </div>
          <div class="info-item">
            <span class="label">版本:</span>
            <span class="value">1.0.0</span>
          </div>
          <div class="info-item">
            <span class="label">后端端口:</span>
            <span class="value">8080</span>
          </div>
          <div class="info-item">
            <span class="label">前端端口:</span>
            <span class="value">9090</span>
          </div>
          <div class="info-item">
            <span class="label">数据库:</span>
            <span class="value">MySQL</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Home',
  
  computed: {
    ...mapState({
      patients: state => state.patients,
      appointments: state => state.appointments,
      loading: state => state.loading,
      doctors: state => state.doctor.doctors
    }),
    
    recentAppointments() {
      return this.appointments.slice(0, 5)
    }
  },
  
  methods: {
    formatDate(row, column) {
      if (!row.appointmentTime) return ''
      const date = new Date(row.appointmentTime)
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`
    },
    
    getStatusType(status) {
      switch (status) {
        case '等待中': return 'warning'
        case '已完成': return 'success'
        case '已取消': return 'danger'
        default: return 'info'
      }
    }
  },
  
  created() {
    this.$store.dispatch('fetchPatients')
    this.$store.dispatch('doctor/fetchDoctors')
    this.$store.dispatch('fetchAppointments')
  }
}
</script>

<style scoped>
.home {
  padding: 10px;
}

.dashboard-card {
  height: 180px;
  margin-bottom: 20px;
}

.dashboard-info {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
}

.dashboard-icon {
  font-size: 40px;
  margin-right: 20px;
}

.patient-icon {
  color: #409EFF;
}

.doctor-icon {
  color: #67C23A;
}

.appointment-icon {
  color: #E6A23C;
}

.dashboard-count {
  font-size: 36px;
  font-weight: bold;
}

.dashboard-action {
  text-align: center;
}

.recent-section {
  margin-top: 20px;
}

.recent-card, .system-info-card {
  height: 300px;
}

.info-item {
  margin-bottom: 15px;
  font-size: 14px;
}

.label {
  font-weight: bold;
  margin-right: 10px;
  color: #606266;
  display: inline-block;
  width: 100px;
}

.value {
  color: #333;
}
</style> 