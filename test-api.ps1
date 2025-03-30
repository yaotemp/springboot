# 测试创建用户
Write-Host "测试创建用户..."
$createUserResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -ContentType "application/json" -Body '{"username":"testuser1","email":"test1@example.com"}'
Write-Host "创建用户结果: $($createUserResponse | ConvertTo-Json)"

# 测试获取所有用户
Write-Host "`n测试获取所有用户..."
$allUsersResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/users/all" -Method Post
Write-Host "所有用户: $($allUsersResponse | ConvertTo-Json)"

# 测试根据ID获取用户
$userId = $createUserResponse.id
Write-Host "`n测试根据ID获取用户..."
$userByIdResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/users/find" -Method Post -ContentType "application/json" -Body "{`"id`":$userId}"
Write-Host "获取用户: $($userByIdResponse | ConvertTo-Json)"

# 测试更新用户
Write-Host "`n测试更新用户..."
$updateUserResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/users/update" -Method Post -ContentType "application/json" -Body "{`"id`":$userId,`"username`":`"updatedUser`",`"email`":`"updated@example.com`"}"
Write-Host "更新用户结果: $($updateUserResponse | ConvertTo-Json)"

# 测试删除用户
Write-Host "`n测试删除用户..."
Invoke-RestMethod -Uri "http://localhost:8080/api/users/delete" -Method Post -ContentType "application/json" -Body "{`"id`":$userId}"
Write-Host "用户已删除"

# 验证用户已被删除
Write-Host "`n验证用户已被删除..."
try {
    $deletedUserResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/users/find" -Method Post -ContentType "application/json" -Body "{`"id`":$userId}"
    Write-Host "错误：用户仍然存在"
} catch {
    Write-Host "成功：用户已被删除，服务器返回错误: $($_.Exception.Message)"
}