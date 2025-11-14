```powershell
param(
    [string]$Path = "./src/test/java"
)

Write-Host "`n=== Scanning for Selenium and RestAssured usage ===`n"

Get-ChildItem -Recurse $Path -Include *.java | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file

    if ($content -match "org.openqa.selenium" -or $content -match "io.restassured") {
        Write-Host "⚠ Found usage in: $file"
        Select-String -Path $file -Pattern "org.openqa.selenium","io.restassured"
    }
}

Write-Host "`nScan complete.`n"
