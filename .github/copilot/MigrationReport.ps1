Write-Host "`n=== PLAYWRIGHT MIGRATION REPORT ===`n"

$selenium = Get-ChildItem -Recurse ./src -Include *.java | Select-String -Pattern "org.openqa.selenium"
$rest = Get-ChildItem -Recurse ./src -Include *.java | Select-String -Pattern "io.restassured"
$playwright = Get-ChildItem -Recurse ./src -Include *.java | Select-String -Pattern "com.microsoft.playwright"

Write-Host "Selenium references found: $($selenium.Count)"
Write-Host "Rest Assured references found: $($rest.Count)"
Write-Host "Playwright references found: $($playwright.Count)"

if ($selenium.Count -eq 0 -and $rest.Count -eq 0 -and $playwright.Count -gt 0) {
    Write-Host "`nMigration Completed Successfully!" -ForegroundColor Green
} else {
    Write-Host "`nMigration incomplete. Review required." -ForegroundColor Yellow
}
