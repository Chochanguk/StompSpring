# dev-up.ps1
# 개발용 인프라(rabbitmq + mongo) 올리기 (컨테이너 삭제 X)

$ErrorActionPreference = "Continue"

Write-Host "🐳 [UP] docker compose up -d 실행..." -ForegroundColor Cyan
docker compose up -d

# 🔍 RabbitMQ 컨테이너 준비될 때까지 대기
Write-Host "⏳ [WAIT] rabbitmq 컨테이너 준비 대기..." -ForegroundColor Cyan

$maxWait = 30      # 최대 30초 대기
$waited  = 0
$ready   = $false

while (-not $ready -and $waited -lt $maxWait) {
    Start-Sleep -Seconds 3
    $waited += 3

    $status = docker ps --filter "name=rabbitmq" --format "{{.Status}}"
    if ($status -match "Up") {
        $ready = $true
    }
}

if (-not $ready) {
    Write-Host "❌ rabbitmq 컨테이너가 정상적으로 올라오지 않았습니다." -ForegroundColor Red
    docker ps
    exit 1
}

Write-Host "✅ rabbitmq 컨테이너가 실행 중입니다." -ForegroundColor Green

# ⚙️ STOMP 플러그인 활성화 (이미 켜져 있어도 재실행해도 됨)
Write-Host "⚙️ [RABBITMQ] rabbitmq_stomp 플러그인 활성화 시도..." -ForegroundColor Cyan
docker exec rabbitmq rabbitmq-plugins enable rabbitmq_stomp | Out-Null

Write-Host "📋 [RABBITMQ] rabbitmq_stomp 상태 확인..." -ForegroundColor DarkCyan
docker exec rabbitmq rabbitmq-plugins list | Select-String "rabbitmq_stomp"

Write-Host "✅ [DONE] 현재 실행 중 컨테이너 목록:" -ForegroundColor Green
docker ps
