#!/usr/bin/env bash
set -e

echo "🔴 STARTING FULL AUTO FIX..."

# 1. Ensure correct repo
if [ ! -d ".git" ]; then
  echo "❌ Not a git repository"
  exit 1
fi

# 2. Remove secret file completely
echo "🧹 Removing AppConfig.kt from history (secret detected)"
git filter-branch --force --index-filter \
"git rm --cached --ignore-unmatch app/src/main/java/com/osamendis/liveweather/core/AppConfig.kt" \
--prune-empty --tag-name-filter cat -- --all

# 3. Recreate safe AppConfig.kt
echo "🛠 Recreating safe AppConfig.kt"
mkdir -p app/src/main/java/com/osamendis/liveweather/core
cat > app/src/main/java/com/osamendis/liveweather/core/AppConfig.kt <<'EOF'
package com.osamendis.liveweather.core

object AppConfig {
    const val API_KEY = BuildConfig.API_KEY
}
EOF

# 4. Remove Gradle cache & large jars
echo "🧹 Removing Gradle cache & large files"
rm -rf gradle/caches
rm -rf gradle/lib/*.jar || true

# 5. Add .gitignore rules
echo "📄 Updating .gitignore"
cat >> .gitignore <<'EOF'

# Secrets
*.keystore
*.jks
local.properties

# Gradle cache
.gradle/
gradle/caches/
gradle/lib/

# Build
/build
/app/build
EOF

# 6. Commit clean state
git add .
git commit -m "Remove secrets & clean repo for GitHub Actions"

# 7. Force push clean history
echo "🚀 Force pushing clean repo"
git push origin main --force

echo "✅ DONE! Repo is clean & GitHub Actions ready"
