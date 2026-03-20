#!/bin/bash

./gradlew checkstyleMain codenarcMain codenarcScript ktlintCheck

uv run pylint rulebook-pylint/ --ignore-paths=rulebook-pylint/tests/resources/
uv run pylint rulebook-cppcheck/
uv run pylint sample/python/
uv run pylint \
  --rcfile=sample-configured/custom_pylintrc sample-configured/python/

local_addon='local.addon.json'
local_custom_addon='sample-configured/local.custom_addon.json'
if [[ -f "$local_addon" ]]; then
  uv run cppcheck \
    --enable=performance,portability,style,warning \
    "--addon=$local_addon" \
    sample/c/*.c \
    sample/cpp/*.cpp
fi
if [[ -f "$local_custom_addon" ]]; then
  uv run cppcheck \
    --enable=performance,portability,style,warning \
    "--addon=$local_custom_addon" \
    sample-configured/c/*.c \
    sample-configured/cpp/*.cpp
fi

pnpm lint
pnpm lint2
