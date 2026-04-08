#!/bin/bash

LOCAL_ADDON='local.addon.json'
if [[ ! -f "$LOCAL_ADDON" ]]; then
  cat > "$LOCAL_ADDON" << EOF
{
  "script": "$HOME/GitHub/rulebook/rulebook-cppcheck/rulebook_cppcheck/checkset.py"
}
EOF
fi
