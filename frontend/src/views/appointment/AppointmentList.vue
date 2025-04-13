<template>
  <div class="appointment-list">
    <div class="page-header">
      <h2>预约管理</h2>
      <el-button type="primary" @click="handleAdd">新建预约</el-button>
    </div>
    
    <el-card>
      <div class="filter-container">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="患者姓名">
            <el-input v-model="searchForm.patientName" placeholder="输入患者姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="医生姓名">
            <el-input v-model="searchForm.doctorName" placeholder="输入医生姓名" clearable></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="选择状态" clearable>
              <el-option
                v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <el-table
        v-loading="loading"
        :data="filteredAppointments"
        border
        fit
        highlight-current-row>
        <el-table-column align="center" label="ID" width="80">
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        
        <el-table-column label="患者姓名" width="120">
          <template slot-scope="scope">
            {{ scope.row.patientName }}
          </template>
        </el-table-column>
        
        <el-table-column label="医生姓名" width="120">
          <template slot-scope="scope">
            {{ scope.row.doctorName }}
          </template>
        </el-table-column>
        
        <el-table-column label="科室" width="120">
          <template slot-scope="scope">
            {{ scope.row.doctorDepartment }}
          </template>
        </el-table-column>
        
        <el-table-column label="预约时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.appointmentTime) }}
          </template>
        </el-table-column>
        
        <el-table-column label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column label="描述" min-width="150">
          <template slot-scope="scope">
            {{ scope.row.description }}
          </template>
        </el-table-column>
        
        <el-table-column label="操作" align="center" width="230">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.row)">
              查看
            </el-button>
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button 
              size="mini" 
              type="warning" 
              v-if="scope.row.status === '等待中'"
              @click="handleCancel(scope.row)">
              取消
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加/编辑预约对话框 -->
    <el-dialog :title="dialogStatus === 'create' ? '新建预约' : '编辑预约'" :visible.sync="dialogVisible">
      <el-form
        ref="appointmentForm"
        :model="form"
        :rules="rules"
        label-width="100px"
        class="appointment-form">
        <el-form-item label="患者" prop="patientId">
          <el-select 
            v-model="form.patientId" 
            filterable 
            placeholder="请选择患者"
            @change="handlePatientChange">
            <el-option
              v-for="item in patients"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="医生" prop="doctorId">
          <el-select 
            v-model="form.doctorId" 
            filterable 
            placeholder="请选择医生"
            @change="handleDoctorChange">
            <el-option
              v-for="item in doctors"
              :key="item.id"
              :label="`${item.name} (${item.department})`"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker
            v-model="form.appointmentTime"
            type="datetime"
            placeholder="选择日期时间"
            value-format="yyyy-MM-ddTHH:mm:ss"
            :picker-options="{
              disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;
              }
            }">
          </el-date-picker>
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="预约描述" prop="description">
          <el-input type="textarea" v-model="form.description" rows="4"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">
          取 消
        </el-button>
        <el-button type="primary" @click="submitForm">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { formatDate } from '@/utils/date'

export default {
  name: 'AppointmentList',
  
  data() {
    return {
      searchForm: {
        patientName: '',
        doctorName: '',
        status: ''
      },
      statusOptions: [
        { label: '等待中', value: '等待中' },
        { label: '已完成', value: '已完成' },
        { label: '已取消', value: '已取消' }
      ],
      dialogVisible: false,
      dialogStatus: 'create',
      form: {
        id: undefined,
        patientId: undefined,
        doctorId: undefined,
        appointmentTime: '',
        status: '等待中',
        description: '',
        patientName: '',
        doctorName: '',
        doctorDepartment: ''
      },
      rules: {
        patientId: [
          { required: true, message: '请选择患者', trigger: 'change' }
        ],
        doctorId: [
          { required: true, message: '请选择医生', trigger: 'change' }
        ],
        appointmentTime: [
          { required: true, message: '请选择预约时间', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ]
      }
    }
  },
  
  computed: {
    ...mapState({
      appointments: state => state.appointments,
      patients: state => state.patients,
      loading: state => state.loading
    }),
    ...mapState('doctor', ['doctors']),
    
    filteredAppointments() {
      let result = this.appointments
      
      if (this.searchForm.patientName) {
        result = result.filter(a => 
          a.patientName && a.patientName.toLowerCase().includes(this.searchForm.patientName.toLowerCase())
        )
      }
      
      if (this.searchForm.doctorName) {
        result = result.filter(a => 
          a.doctorName && a.doctorName.toLowerCase().includes(this.searchForm.doctorName.toLowerCase())
        )
      }
      
      if (this.searchForm.status) {
        result = result.filter(a => a.status === this.searchForm.status)
      }
      
      return result
    }
  },
  
  methods: {
    formatDateTime(dateTimeStr) {
      if (!dateTimeStr) return '';
      const date = new Date(dateTimeStr);
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`;
    },
    
    getStatusType(status) {
      switch (status) {
        case '等待中': return 'warning'
        case '已完成': return 'success'
        case '已取消': return 'danger'
        default: return 'info'
      }
    },
    
    handleSearch() {
      // 前端已经在filteredAppointments计算属性中处理了过滤逻辑
    },
    
    resetSearch() {
      this.searchForm = {
        patientName: '',
        doctorName: '',
        status: ''
      }
    },
    
    handleAdd() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogVisible = true
    },
    
    handleView(row) {
      this.$router.push(`/appointments/${row.id}`)
    },
    
    handleEdit(row) {
      this.dialogStatus = 'update'
      this.form = { ...row }
      
      // 确保时间格式正确
      if (typeof this.form.appointmentTime === 'string') {
        this.form.appointmentTime = this.form.appointmentTime.replace(' ', 'T');
      }
      
      this.dialogVisible = true
    },
    
    handleCancel(row) {
      this.$confirm('确定要取消此预约吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('cancelAppointment', row.id).then(() => {
          this.$message.success('预约已取消')
        }).catch(err => {
          this.$message.error('取消预约失败')
          console.error(err)
        })
      }).catch(() => {})
    },
    
    handleDelete(row) {
      this.$confirm('此操作将永久删除该预约, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('deleteAppointment', row.id).then(() => {
          this.$message.success('删除成功')
        }).catch(err => {
          this.$message.error('删除失败')
          console.error(err)
        })
      }).catch(() => {})
    },
    
    resetForm() {
      this.form = {
        id: undefined,
        patientId: undefined,
        doctorId: undefined,
        appointmentTime: '',
        status: '等待中',
        description: '',
        patientName: '',
        doctorName: '',
        doctorDepartment: ''
      }
    },
    
    handlePatientChange(patientId) {
      const patient = this.patients.find(p => p.id === patientId)
      if (patient) {
        this.form.patientName = patient.name
      }
    },
    
    handleDoctorChange(doctorId) {
      const doctor = this.doctors.find(d => d.id === doctorId)
      if (doctor) {
        this.form.doctorName = doctor.name
        this.form.doctorDepartment = doctor.department
      }
    },
    
    submitForm() {
      this.$refs.appointmentForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.dialogStatus === 'create') {
              await this.$store.dispatch('createAppointment', this.form)
              this.$message.success('预约创建成功')
            } else {
              await this.$store.dispatch('updateAppointment', this.form)
              this.$message.success('预约更新成功')
            }
            this.dialogVisible = false
          } catch (error) {
            this.$message.error('操作失败')
            console.error(error)
          }
        } else {
          console.log('表单验证失败')
          return false
        }
      })
    }
  },
  
  created() {
    this.$store.dispatch('fetchAppointments')
    this.$store.dispatch('fetchPatients')
    this.$store.dispatch('doctor/fetchDoctors')
  }
}
</script>

<style scoped>
.appointment-list {
  padding: 10px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.filter-container {
  margin-bottom: 20px;
}

.appointment-form {
  margin: 20px 0;
}

.dialog-footer {
  text-align: right;
}
</style> 