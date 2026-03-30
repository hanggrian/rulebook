#!/bin/bash

./gradlew checkstyleMain codenarcMain codenarcScript ktlintCheck

uv run pylint rulebook-pylint/ --ignore-paths=rulebook-pylint/tests/resources/
uv run pylint rulebook-cppcheck/
uv run pylint sample/python/
uv run pylint \
  --rcfile=sample-configured/custom_pylintrc \
  sample-configured/python/

uv run cppcheck \
  --check-level=exhaustive \
  --enable=performance,portability,style,warning \
  --addon=addon.json \
  rulebook-cli/src/*.cpp \
  rulebook-cli/src/*.hpp
uv run cppcheck \
  --enable=performance,portability,style,warning \
  --addon=addon.json \
  sample/c/*.c \
  sample/cpp/*.cpp
uv run cppcheck \
  --enable=performance,portability,style,warning \
  --addon=sample-configured/custom_addon.json \
  sample-configured/c/*.c \
  sample-configured/cpp/*.cpp

pnpm lint
pnpm lint2
