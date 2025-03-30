-- 添加EFIN字段到users表
ALTER TABLE users ADD COLUMN efin CHAR(5);

-- 如果需要创建索引（可选）
CREATE INDEX idx_user_efin ON users (efin);

-- 更新说明
COMMENT ON COLUMN users.efin IS 'Electronic Filing Identification Number (5 characters)'; 