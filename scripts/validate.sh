#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew check

pylint rulebook-pylint/ --ignore-paths=rulebook-pylint/tests/resources/
pylint rulebook-cppcheck/
pylint sample/python/
pylint --rcfile=sample-configured/custom_pylintrc sample-configured/python/

cppcheck \
  --enable=warning \
  --addon=local.addon.json \
  sample/c/*.c \
  sample/cpp/*.cpp
cppcheck \
  --enable=warning \
  --addon=sample-configured/local.custom_addon.json \
  sample-configured/c/*.c \
  sample-configured/cpp/*.cpp

npm run lint
npm run lint2
