/**
 * 日期工具函数
 */

/**
 * 将日期格式化为 YYYY-MM-DD 格式
 * @param {String|Date} date 日期字符串或Date对象
 * @returns {String} 格式化后的日期字符串
 */
export function formatDate(date) {
  if (!date) return '';
  
  const d = typeof date === 'string' ? new Date(date) : date;
  
  if (isNaN(d.getTime())) return '';
  
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  
  return `${year}-${month}-${day}`;
}

/**
 * 将日期时间格式化为 YYYY-MM-DD HH:MM:SS 格式
 * @param {String|Date} date 日期字符串或Date对象
 * @returns {String} 格式化后的日期时间字符串
 */
export function formatDateTime(date) {
  if (!date) return '';
  
  const d = typeof date === 'string' ? new Date(date) : date;
  
  if (isNaN(d.getTime())) return '';
  
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  const seconds = String(d.getSeconds()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

/**
 * 简短格式化日期时间 YYYY-MM-DD HH:MM
 * @param {String|Date} date 日期字符串或Date对象
 * @returns {String} 格式化后的日期时间字符串
 */
export function formatDateTimeShort(date) {
  if (!date) return '';
  
  const d = typeof date === 'string' ? new Date(date) : date;
  
  if (isNaN(d.getTime())) return '';
  
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  const hours = String(d.getHours()).padStart(2, '0');
  const minutes = String(d.getMinutes()).padStart(2, '0');
  
  return `${year}-${month}-${day} ${hours}:${minutes}`;
} 