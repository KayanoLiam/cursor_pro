# 简易医院管理系统

这是一个基于Spring Boot的简易医院管理系统，提供了基本的患者、医生和预约管理功能。

## 功能需求

该系统包含以下核心功能：

1. **患者管理**
   - 添加、查询、修改和删除患者信息
   - 支持按姓名搜索患者

2. **医生管理**
   - 添加、查询、修改和删除医生信息
   - 支持按科室或姓名搜索医生

3. **预约管理**
   - 创建、查询、修改和取消预约
   - 支持按患者ID或医生ID查询预约
   - 支持更新预约状态（等待中、已完成、已取消）

## 技术栈

- Java 21
- Spring Boot 3.4.4
- MyBatis
- MySQL
- Lombok

## 系统架构

本系统采用典型的三层架构：

- **控制器层（Controller）**：处理HTTP请求，接收和返回数据
- **服务层（Service）**：包含业务逻辑
- **数据访问层（Mapper）**：与数据库交互

## 数据模型

系统包括三个主要实体：

1. **患者（Patient）**：存储患者的基本信息
2. **医生（Doctor）**：存储医生的基本信息和专业信息
3. **预约（Appointment）**：记录患者与医生之间的预约信息

## 如何使用

### 环境要求

- JDK 21+
- MySQL 8.0+
- Maven 3.6+

### 数据库配置

1. 创建名为`hospital_db`的数据库
2. 配置`application.properties`中的数据库连接信息

### 运行应用

1. 克隆项目到本地
2. 确保MySQL服务已启动
3. 使用Maven构建项目：`mvn clean install`
4. 运行应用：`mvn spring-boot:run`或运行`HospitalDemoApplication`类
5. 访问：`http://localhost:8080/hospital/`

## API接口说明

### 患者接口

- `POST /api/patients` - 添加患者
- `GET /api/patients` - 获取所有患者
- `GET /api/patients/{id}` - 根据ID获取患者
- `GET /api/patients/search?name={name}` - 根据姓名搜索患者
- `PUT /api/patients/{id}` - 更新患者信息
- `DELETE /api/patients/{id}` - 删除患者

### 医生接口

- `POST /api/doctors` - 添加医生
- `GET /api/doctors` - 获取所有医生
- `GET /api/doctors/{id}` - 根据ID获取医生
- `GET /api/doctors/department/{department}` - 根据科室获取医生
- `GET /api/doctors/search?name={name}` - 根据姓名搜索医生
- `PUT /api/doctors/{id}` - 更新医生信息
- `DELETE /api/doctors/{id}` - 删除医生

### 预约接口

- `POST /api/appointments` - 创建预约
- `GET /api/appointments` - 获取所有预约
- `GET /api/appointments/{id}` - 根据ID获取预约
- `GET /api/appointments/patient/{patientId}` - 获取患者的所有预约
- `GET /api/appointments/doctor/{doctorId}` - 获取医生的所有预约
- `PATCH /api/appointments/{id}/status?status={status}` - 更新预约状态
- `PUT /api/appointments/{id}` - 更新预约信息
- `PATCH /api/appointments/{id}/cancel` - 取消预约

## 注意事项

1. 本系统为简易版本，实际应用中需增加用户认证和权限控制
2. 数据库初始化脚本位于`src/main/resources/schema.sql`和`data.sql`
3. 默认数据库用户名为`root`，密码为`123456`，可在`application.properties`中修改 # cursor_pro
