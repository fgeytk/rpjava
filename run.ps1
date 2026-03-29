$ErrorActionPreference = "Stop"

$projectRoot = "C:\Users\titou\Documents\rpjava"
$srcDir = "C:\Users\titou\Documents\rpjava\src"
$outDir = "C:\Users\titou\Documents\rpjava\out"

if (Test-Path $outDir) {
    Remove-Item -Recurse -Force $outDir
}

New-Item -ItemType Directory -Path $outDir | Out-Null

$sources = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }

if (-not $sources) {
    throw "Aucun fichier Java trouve dans $srcDir"
}

javac -d $outDir $sources

if ($LASTEXITCODE -ne 0) {
    throw "La compilation a echoue."
}

java -cp $outDir rpjava.Main

if ($LASTEXITCODE -ne 0) {
    throw "L'execution a echoue."
}
