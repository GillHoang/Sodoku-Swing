# Build script cho Sudoku-Swing: biên dịch -> đóng gói JAR -> tạo .exe (kèm runtime)
# Cách chạy (trong PowerShell, tại thư mục gốc dự án):
#   powershell -ExecutionPolicy Bypass -File .\build.ps1

$ErrorActionPreference = "Stop"

# Đường dẫn tới JDK (máy bạn java/jar/jpackage nằm ở đây).
# Có thể override bằng cách đặt biến môi trường JAVA_HOME.
if ($env:JAVA_HOME) {
    $JDK = Join-Path $env:JAVA_HOME "bin"
} else {
    $JDK = "C:\Program Files\Java\jdk-21.0.11\bin"
}

if (-not (Test-Path "$JDK\jpackage.exe")) {
    Write-Error "Khong tim thay jpackage tai '$JDK'. Hay sua bien `$JDK hoac dat JAVA_HOME."
}

# 1. Don thu muc build cu
Remove-Item -Recurse -Force out, build, dist -ErrorAction SilentlyContinue

# 2. Bien dich toan bo ma nguon vao thu muc out
New-Item -ItemType Directory -Force -Path out | Out-Null
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
& "$JDK\javac.exe" -d out $files

# 3. Dong goi JAR thuc thi (main class = app.Main)
New-Item -ItemType Directory -Force -Path build\jar | Out-Null
& "$JDK\jar.exe" --create --file build\jar\Sudoku.jar --main-class app.Main -C out .

# 4. Tao .exe kem runtime bang jpackage
& "$JDK\jpackage.exe" --type app-image --name Sudoku `
    --input build\jar --main-jar Sudoku.jar --main-class app.Main `
    --dest dist

Write-Host ""
Write-Host "Build xong! File thuc thi: dist\Sudoku\Sudoku.exe"
