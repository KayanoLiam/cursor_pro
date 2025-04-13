<template>
  <div class="doctor-list">
    <div class="page-header">
      <h2>医生管理</h2>
      <el-button type="primary" @click="handleAdd">添加医生</el-button>
    </div>
    
    <el-card>
      <div class="filter-container">
        <el-input
          placeholder="医生姓名/科室"
          v-model="listQuery.keyword"
          class="filter-item"
          @keyup.enter.native="handleFilter"
          clearable />
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
      </div>
      
      <el-table
        v-loading="loading"
        :data="doctors"
        element-loading-text="加载中..."
        border
        fit
        highlight-current-row>
        <el-table-column align="center" label="ID" width="80">
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        
        <el-table-column label="姓名" width="120">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        
        <el-table-column label="科室" width="120">
          <template slot-scope="scope">
            {{ scope.row.department }}
          </template>
        </el-table-column>
        
        <el-table-column label="职称" width="120">
          <template slot-scope="scope">
            {{ scope.row.title }}
          </template>
        </el-table-column>
        
        <el-table-column label="电话" width="150">
          <template slot-scope="scope">
            {{ scope.row.phone }}
          </template>
        </el-table-column>
        
        <el-table-column label="专长" min-width="200">
          <template slot-scope="scope">
            {{ scope.row.specialty }}
          </template>
        </el-table-column>
        
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.row)">
              查看
            </el-button>
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="listQuery.page"
          :page-size="listQuery.limit"
          layout="total, prev, pager, next"
          :total="total">
        </el-pagination>
      </div>
    </el-card>
    
    <!-- 添加/编辑医生对话框 -->
    <el-dialog :title="dialogStatus === 'create' ? '添加医生' : '编辑医生'" :visible.sync="dialogVisible">
      <el-form
        ref="doctorForm"
        :model="form"
        :rules="rules"
        label-width="80px"
        class="doctor-form">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="科室" prop="department">
          <el-select v-model="form.department" placeholder="请选择科室">
            <el-option
              v-for="item in departmentOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-select v-model="form.title" placeholder="请选择职称">
            <el-option
              v-for="item in titleOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="专长" prop="specialty">
          <el-input type="textarea" v-model="form.specialty" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input type="textarea" v-model="form.introduction" rows="5"></el-input>
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

export default {
  name: 'DoctorList',
  
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 10,
        keyword: ''
      },
      dialogVisible: false,
      dialogStatus: 'create',
      form: {
        id: undefined,
        name: '',
        department: '',
        title: '',
        phone: '',
        specialty: '',
        introduction: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入医生姓名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        department: [
          { required: true, message: '请选择科室', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请选择职称', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      },
      departmentOptions: [
        '内科',
        '外科',
        '妇产科',
        '儿科',
        '眼科',
        '口腔科',
        '耳鼻喉科',
        '皮肤科',
        '神经科',
        '肿瘤科',
        '精神科',
        '急诊科',
        '康复科'
      ],
      titleOptions: [
        '住院医师',
        '主治医师',
        '副主任医师',
        '主任医师',
        '教授'
      ]
    }
  },
  
  computed: {
    ...mapState({
      doctors: state => state.doctor.doctors,
      loading: state => state.doctor.loading,
      total: state => state.doctor.totalDoctors || 0
    })
  },
  
  created() {
    this.fetchData()
  },
  
  methods: {
    async fetchData() {
      if (this.loading) return; // 防止重复请求
      
      try {
        const loading = this.$loading({
          lock: true,
          text: this.listQuery.keyword ? '正在搜索...' : '加载中...',
          spinner: 'el-icon-loading',
          background: 'rgba(255, 255, 255, 0.7)'
        });
        
        await this.$store.dispatch('doctor/fetchDoctors', this.listQuery);
        
        loading.close();
        
        // 如果正在搜索且没有结果，显示提示
        if (this.listQuery.keyword && this.doctors.length === 0) {
          this.$message({
            type: 'info',
            message: '没有找到匹配的医生信息'
          });
        }
      } catch (error) {
        console.error('获取医生列表失败:', error);
        this.$message.error('获取医生列表失败，请稍后重试');
      }
    },
    
    handleFilter() {
      this.listQuery.page = 1;
      this.fetchData();
    },
    
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.fetchData()
    },
    
    resetForm() {
      this.form = {
        id: undefined,
        name: '',
        department: '',
        title: '',
        phone: '',
        specialty: '',
        introduction: ''
      }
    },
    
    handleAdd() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.doctorForm.clearValidate()
      })
    },
    
    handleView(row) {
      this.$router.push(`/doctors/${row.id}`)
    },
    
    handleEdit(row) {
      this.form = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.doctorForm.clearValidate()
      })
    },
    
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该医生信息吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        
        const loading = this.$loading({
          lock: true,
          text: '正在删除...',
          spinner: 'el-icon-loading',
          background: 'rgba(255, 255, 255, 0.7)'
        });
        
        try {
          // 调用删除接口
          const result = await this.$store.dispatch('doctor/deleteDoctor', row.id);
          loading.close();
          
          // 处理结果
          if (result && result.success) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            
            // 刷新列表以确保同步
            this.fetchData();
          } else {
            console.error('删除失败:', result ? result.message : '未知错误');
            
            // 后端删除失败，但前端仍可以移除显示（提升用户体验）
            this.$message({
              type: 'warning',
              message: '删除请求发送成功，但服务器响应异常。界面已更新，但数据可能未同步。'
            });
            
            // 从本地数据中移除该医生
            this.$store.commit('doctor/REMOVE_DOCTOR', row.id);
          }
        } catch (error) {
          loading.close();
          console.error('删除操作失败:', error);
          
          // 即使出错，也尝试从界面上移除
          this.$message({
            type: 'warning',
            message: '删除请求失败。界面已更新，请刷新页面确认删除状态。'
          });
          
          // 从本地数据中移除该医生
          this.$store.commit('doctor/REMOVE_DOCTOR', row.id);
        }
      } catch (err) {
        // 用户取消删除操作
        if (err !== 'cancel') {
          console.error('删除操作异常:', err);
          this.$message.error('删除操作发生异常，请稍后重试');
        }
      }
    },
    
    submitForm() {
      this.$refs.doctorForm.validate(async valid => {
        if (valid) {
          const isNew = this.dialogStatus === 'create'
          try {
            if (isNew) {
              await this.$store.dispatch('doctor/createDoctor', this.form)
              this.$message({
                type: 'success',
                message: '创建成功!'
              })
            } else {
              await this.$store.dispatch('doctor/updateDoctor', this.form)
              this.$message({
                type: 'success',
                message: '更新成功!'
              })
            }
            this.dialogVisible = false
            this.fetchData()
          } catch (error) {
            this.$message.error(isNew ? '创建失败' : '更新失败')
            console.error(error)
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.doctor-list {
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

.filter-item {
  margin-right: 10px;
  width: 200px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style> 