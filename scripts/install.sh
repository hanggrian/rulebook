#!/bin/bash

./gradlew assemble

LOCAL_ADDON='local.addon.json'

uv sync
uv pip install -r website/requirements.txt

# PyCharm plugin requires a addon.json with relative path
if [[ ! -f "$LOCAL_ADDON" ]]; then
    cat > "$LOCAL_ADDON" << EOF
{
  "script": "$HOME/GitHub/rulebook/rulebook-cppcheck/rulebook_cppcheck/checkset.py"
}
EOF
fi

pnpm i
pnpm -r build
