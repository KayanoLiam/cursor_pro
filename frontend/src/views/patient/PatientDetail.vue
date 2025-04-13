<template>
  <div class="patient-detail">
    <div class="page-header">
      <h2>患者详情</h2>
      <div>
        <el-button @click="$router.push('/patients')">返回</el-button>
        <el-button type="primary" @click="handleEdit">编辑</el-button>
      </div>
    </div>
    
    <el-card v-loading="loading">
      <el-descriptions title="患者信息" :column="2" border>
        <el-descriptions-item label="ID">{{ patient.id }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ patient.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ patient.gender }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ patient.age }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ patient.phone }}</el-descriptions-item>
        <el-descriptions-item label="地址">{{ patient.address }}</el-descriptions-item>
        <el-descriptions-item label="病史" :span="2">{{ patient.medicalHistory || '无' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
    
    <el-card class="appointment-card" v-loading="loading">
      <div slot="header" class="clearfix">
        <span>预约记录</span>
        <el-button style="float: right" type="primary" size="small" @click="handleAddAppointment">新增预约</el-button>
      </div>
      <el-table
        :data="appointments"
        style="width: 100%"
        :empty-text="'暂无预约记录'">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="doctorName" label="医生" width="120"></el-table-column>
        <el-table-column prop="doctorDepartment" label="科室" width="120"></el-table-column>
        <el-table-column prop="appointmentTime" label="预约时间" :formatter="formatDate"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="viewAppointment(scope.row.id)">查看</el-button>
            <el-button
              v-if="scope.row.status === '等待中'"
              size="mini"
              type="danger"
              @click="cancelAppointment(scope.row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 编辑患者对话框 -->
    <el-dialog title="编辑患者" :visible.sync="dialogVisible" width="500px">
      <el-form :model="form" :rules="rules" ref="patientForm" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="120"></el-input-number>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="病史" prop="medicalHistory">
          <el-input type="textarea" v-model="form.medicalHistory" rows="4"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'PatientDetail',
  
  data() {
    return {
      dialogVisible: false,
      form: {
        id: null,
        name: '',
        gender: '男',
        age: 30,
        phone: '',
        address: '',
        medicalHistory: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入患者姓名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        age: [
          { required: true, message: '请填写年龄', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      }
    }
  },
  
  computed: {
    ...mapState(['loading']),
    
    patientId() {
      return this.$route.params.id
    },
    
    patient() {
      return this.$store.state.currentPatient || {}
    },
    
    appointments() {
      return this.$store.state.appointments.filter(a => a.patientId === parseInt(this.patientId))
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
    },
    
    handleEdit() {
      this.form = { ...this.patient }
      this.dialogVisible = true
    },
    
    submitForm() {
      this.$refs.patientForm.validate(async (valid) => {
        if (valid) {
          try {
            await this.$store.dispatch('updatePatient', this.form)
            this.$message.success('更新成功')
            this.dialogVisible = false
          } catch (error) {
            this.$message.error('更新失败')
            console.error(error)
          }
        } else {
          return false
        }
      })
    },
    
    handleAddAppointment() {
      this.$router.push('/appointments/new?patientId=' + this.patientId)
    },
    
    viewAppointment(id) {
      this.$router.push(`/appointments/${id}`)
    },
    
    async cancelAppointment(id) {
      try {
        await this.$confirm('确定要取消此预约吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await this.$store.dispatch('cancelAppointment', id)
        this.$message.success('预约已取消')
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('操作失败')
          console.error(error)
        }
      }
    }
  },
  
  created() {
    this.$store.dispatch('fetchPatientById', this.patientId)
    this.$store.dispatch('fetchAppointments')
  }
}
</script>

<style scoped>
.patient-detail {
  padding: 10px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.appointment-card {
  margin-top: 20px;
}
</style> 