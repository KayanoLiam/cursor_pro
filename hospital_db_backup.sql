-- MySQL dump 10.13  Distrib 9.2.0, for macos13.7 (x86_64)
--
-- Host: localhost    Database: hospital_db
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `patient_id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `appointment_time` datetime NOT NULL,
  `status` varchar(20) DEFAULT '等待中',
  `description` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_appointment_patient` (`patient_id`),
  KEY `idx_appointment_doctor` (`doctor_id`),
  KEY `idx_appointment_time` (`appointment_time`),
  KEY `idx_appointment_status` (`status`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,1,1,'2023-06-01 09:30:00','已完成','心脏不适，胸闷','2025-04-13 03:47:10','2025-04-13 03:47:10'),(2,2,2,'2023-06-01 10:00:00','已完成','孩子发烧，体温38.5度','2025-04-13 03:47:10','2025-04-13 03:47:10'),(3,3,3,'2023-06-02 14:30:00','已取消','腹部疼痛，疑似阑尾炎','2025-04-13 03:47:10','2025-04-13 03:47:10'),(4,4,4,'2023-06-03 11:00:00','已完成','产检','2025-04-13 03:47:10','2025-04-13 03:47:10'),(5,5,5,'2023-06-04 16:00:00','等待中','左腿骨折复诊','2025-04-13 03:47:10','2025-04-13 03:47:10');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `appointment_date` datetime NOT NULL,
  `status` enum('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,1,1,'2023-07-15 10:00:00','SCHEDULED','Regular checkup','2025-04-12 15:44:55','2025-04-12 15:44:55'),(2,2,2,'2023-07-16 11:30:00','SCHEDULED','Follow-up appointment','2025-04-12 15:44:55','2025-04-12 15:44:55'),(3,3,3,'2023-07-15 14:00:00','SCHEDULED','Initial consultation','2025-04-12 15:44:55','2025-04-12 15:44:55'),(4,4,4,'2023-07-17 09:15:00','SCHEDULED','Post-surgery checkup','2025-04-12 15:44:55','2025-04-12 15:44:55');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (1,'Cardiology','Deals with disorders of the heart','2025-04-12 15:44:55','2025-04-12 15:44:55'),(2,'Neurology','Deals with disorders of the nervous system','2025-04-12 15:44:55','2025-04-12 15:44:55'),(3,'Pediatrics','Medical care of infants, children, and adolescents','2025-04-12 15:44:55','2025-04-12 15:44:55'),(4,'Orthopedics','Deals with conditions involving the musculoskeletal system','2025-04-12 15:44:55','2025-04-12 15:44:55');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `specialty` varchar(200) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_doctor_name` (`name`),
  KEY `idx_doctor_department` (`department`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'陈医生','男','内科','主任医师','心血管疾病','15800158001','2025-04-13 03:47:10','2025-04-13 03:47:10'),(2,'林医生','女','儿科','副主任医师','儿童常见病','15900159002','2025-04-13 03:47:10','2025-04-13 03:47:10'),(3,'黄医生','男','外科','主治医师','普外科手术','15700157003','2025-04-13 03:47:10','2025-04-13 03:47:10'),(4,'刘医生','女','妇产科','主任医师','产科疾病','15600156004','2025-04-13 03:47:10','2025-04-13 03:47:10'),(5,'吴医生','男','骨科','副主任医师','骨折治疗','15500155005','2025-04-13 03:47:10','2025-04-13 03:47:10');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `specialization` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Dr. John Smith','Cardiologist','john.smith@hospital.com','123-456-7890',1,'2025-04-12 15:44:55','2025-04-12 15:44:55'),(2,'Dr. Emily Johnson','Neurologist','emily.johnson@hospital.com','123-456-7891',2,'2025-04-12 15:44:55','2025-04-12 15:44:55'),(3,'Dr. Michael Brown','Pediatrician','michael.brown@hospital.com','123-456-7892',3,'2025-04-12 15:44:55','2025-04-12 15:44:55'),(4,'Dr. Sarah Davis','Orthopedic Surgeon','sarah.davis@hospital.com','123-456-7893',4,'2025-04-12 15:44:55','2025-04-12 15:44:55'),(5,'景林','cdwedfcwe','Kayano04@outlook.jp','18881367060',2,'2025-04-12 15:46:05','2025-04-12 15:46:05');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_records`
--

DROP TABLE IF EXISTS `medical_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `diagnosis` text NOT NULL,
  `prescription` text,
  `notes` text,
  `record_date` datetime NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `medical_records_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`),
  CONSTRAINT `medical_records_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_records`
--

LOCK TABLES `medical_records` WRITE;
/*!40000 ALTER TABLE `medical_records` DISABLE KEYS */;
INSERT INTO `medical_records` VALUES (1,1,1,'Hypertension','Lisinopril 10mg daily','Blood pressure slightly elevated','2023-06-15 10:30:00','2025-04-12 15:44:55','2025-04-12 15:44:55'),(2,2,2,'Migraine','Sumatriptan as needed','Recommended stress reduction techniques','2023-06-16 12:00:00','2025-04-12 15:44:55','2025-04-12 15:44:55'),(3,3,3,'Common cold','Rest and fluids','Should recover within a week','2023-06-15 14:30:00','2025-04-12 15:44:55','2025-04-12 15:44:55'),(4,4,4,'Fractured wrist','Cast for 6 weeks','Healing well after surgery','2023-06-10 09:45:00','2025-04-12 15:44:55','2025-04-12 15:44:55');
/*!40000 ALTER TABLE `medical_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `medical_history` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_patient_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'张三','男',35,'13800138001','北京市朝阳区XX街XX号','无重大病史','2025-04-13 03:47:10','2025-04-13 03:47:10'),(2,'李四','女',28,'13900139002','上海市浦东新区XX路XX号','2018年曾患肺炎','2025-04-13 03:47:10','2025-04-13 03:47:10'),(3,'王五','男',45,'13700137003','广州市天河区XX大道XX号','高血压，需定期服药','2025-04-13 03:47:10','2025-04-13 03:47:10'),(4,'赵六','女',30,'13600136004','深圳市南山区XX街XX号','无','2025-04-13 03:47:10','2025-04-13 03:47:10'),(5,'钱七','男',55,'13500135005','杭州市西湖区XX路XX号','糖尿病，需控制饮食','2025-04-13 03:47:10','2025-04-13 03:47:10');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `blood_group` varchar(10) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'Alice Williams','FEMALE','1985-03-15','123 Main St, Anytown','987-654-3210','alice@example.com','O+','2025-04-12 15:44:55','2025-04-12 15:44:55'),(2,'Bob Johnson','MALE','1978-07-22','456 Oak Ave, Somewhere','987-654-3211','bob@example.com','A-','2025-04-12 15:44:55','2025-04-12 15:44:55'),(3,'Carol Martinez','FEMALE','1990-11-30','789 Pine Rd, Nowhere','987-654-3212','carol@example.com','B+','2025-04-12 15:44:55','2025-04-12 15:44:55'),(4,'David Thompson','MALE','1965-05-10','101 Elm St, Anywhere','987-654-3213','david@example.com','AB+','2025-04-12 15:44:55','2025-04-12 15:44:55'),(5,'dewdwe','OTHER','2222-02-02','50845 SW Dundee Rd','15181706826','2941143016@qq.com','','2025-04-12 15:45:48','2025-04-12 15:45:48');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-13 12:55:49
