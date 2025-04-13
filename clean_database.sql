-- 清理hospital_db数据库中的多余表
-- 请在执行前考虑备份数据库：
-- mysqldump -u root -p121381121381 -P 1556 hospital_db > hospital_db_backup.sql

-- 先确认项目使用的表（不要删除这些表）:
-- 1. appointment - 用于预约管理
-- 2. doctor - 用于医生管理
-- 3. patient - 用于患者管理

-- 由于存在外键约束，需要先删除引用表
-- 先删除可能存在外键引用的表
DROP TABLE IF EXISTS medical_records;

-- 删除复数形式的冗余表
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;

-- 删除其他可能不使用的关联表
DROP TABLE IF EXISTS departments;

-- 检查数据库中剩余的表
-- 可以使用SHOW TABLES查看 