#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew check

pylint rulebook-pylint/ --ignore-paths=rulebook-pylint/tests/resources/
pylint rulebook-cppcheck/
pylint sample/python/
pylint --rcfile=sample-configured/custom_pylintrc sample-configured/python/

local_addon='local.addon.json'
if [[ -f "$local_addon" ]]; then
  cppcheck \
    --enable=warning \
    "--addon=$local_addon" \
    sample/c/*.c \
    sample/cpp/*.cpp
fi
local_custom_addon='local.custom_addon.json'
if [[ -f "local_custom_addon" ]]; then
  cppcheck \
    --enable=warning \
    "--addon=sample-configured/$local_custom_addon" \
    sample-configured/c/*.c \
    sample-configured/cpp/*.cpp
fi

npm run lint
npm run lint2
