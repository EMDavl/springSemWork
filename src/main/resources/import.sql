INSERT INTO users(email, is_public_account, nickname, password, role, status)
SELECT 'admin@admin', 'false', 'admin', 'someHashedPass', 'ADMIN', 'CONFIRMED'
FROM users
WHERE NOT EXISTS(SELECT 1 FROM users WHERE role = 'ADMIN')