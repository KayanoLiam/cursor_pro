<template>
  <div class="patient-list">
    <div class="page-header">
      <h2>患者管理</h2>
      <el-button type="primary" @click="handleAdd">添加患者</el-button>
    </div>
    
    <el-card>
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="患者姓名">
          <el-input v-model="searchForm.name" placeholder="输入患者姓名" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      
      <el-table
        v-loading="loading"
        :data="filteredPatients"
        style="width: 100%"
        border>
        <el-table-column prop="id" label="ID" width="70"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="gender" label="性别" width="70"></el-table-column>
        <el-table-column prop="age" label="年龄" width="70"></el-table-column>
        <el-table-column prop="phone" label="电话" width="150"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleViewDetail(scope.row.id)">查看</el-button>
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加/编辑患者对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
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
  name: 'PatientList',
  
  data() {
    return {
      searchForm: {
        name: ''
      },
      dialogVisible: false,
      dialogTitle: '添加患者',
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
    ...mapState(['patients', 'loading']),
    
    filteredPatients() {
      if (!this.searchForm.name) {
        return this.patients
      }
      return this.patients.filter(p => 
        p.name.toLowerCase().includes(this.searchForm.name.toLowerCase())
      )
    }
  },
  
  methods: {
    // 搜索相关
    handleSearch() {
      // 如果需要后端搜索，可以调用store的action
      if (this.searchForm.name) {
        this.$store.dispatch('fetchPatientsByName', this.searchForm.name)
      } else {
        this.$store.dispatch('fetchPatients')
      }
    },
    
    resetSearch() {
      this.searchForm.name = ''
      this.$store.dispatch('fetchPatients')
    },
    
    // 表单相关
    resetForm() {
      this.form = {
        id: null,
        name: '',
        gender: '男',
        age: 30,
        phone: '',
        address: '',
        medicalHistory: ''
      }
    },
    
    // 操作相关
    handleAdd() {
      this.dialogTitle = '添加患者'
      this.resetForm()
      this.dialogVisible = true
    },
    
    handleEdit(row) {
      this.dialogTitle = '编辑患者'
      this.form = { ...row }
      this.dialogVisible = true
    },
    
    handleViewDetail(id) {
      this.$router.push(`/patients/${id}`)
    },
    
    async handleDelete(id) {
      try {
        await this.$confirm('此操作将永久删除该患者信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await this.$store.dispatch('deletePatient', id)
        this.$message.success('删除成功')
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('删除失败')
          console.error(error)
        }
      }
    },
    
    submitForm() {
      this.$refs.patientForm.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id) {
              // 更新
              await this.$store.dispatch('updatePatient', this.form)
              this.$message.success('更新成功')
            } else {
              // 新增
              await this.$store.dispatch('createPatient', this.form)
              this.$message.success('添加成功')
            }
            this.dialogVisible = false
          } catch (error) {
            this.$message.error('操作失败')
            console.error(error)
          }
        } else {
          return false
        }
      })
    }
  },
  
  created() {
    this.$store.dispatch('fetchPatients')
  }
}
</script>

<style scoped>
.patient-list {
  padding: 10px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-form {
  margin-bottom: 20px;
}
</style> 