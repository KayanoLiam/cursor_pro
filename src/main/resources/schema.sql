-- 删除已存在的表（如果存在）
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS doctor;

-- 患者表
CREATE TABLE patient (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  gender VARCHAR(10),
  age INT,
  phone VARCHAR(20),
  address VARCHAR(200),
  medical_history TEXT,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 医生表
CREATE TABLE doctor (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  gender VARCHAR(10),
  department VARCHAR(100),
  position VARCHAR(100),
  specialty VARCHAR(200),
  phone VARCHAR(20),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 预约表
CREATE TABLE appointment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  appointment_time DATETIME NOT NULL,
  status VARCHAR(20) DEFAULT '等待中',
  description TEXT,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES patient(id),
  FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

-- 添加索引
CREATE INDEX idx_patient_name ON patient(name);
CREATE INDEX idx_doctor_name ON doctor(name);
CREATE INDEX idx_doctor_department ON doctor(department);
CREATE INDEX idx_appointment_patient ON appointment(patient_id);
CREATE INDEX idx_appointment_doctor ON appointment(doctor_id);
CREATE INDEX idx_appointment_time ON appointment(appointment_time);
CREATE INDEX idx_appointment_status ON appointment(status); 