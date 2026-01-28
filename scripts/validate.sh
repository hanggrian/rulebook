#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT
CLI_BUILD_DIR="rulebook-cli/build"

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

# should show success

./gradlew sample:check sample-configured:check

# expect errors

pylint sample
pylint sample-configured

npm run lint

cppcheck --enable=warning --addon=addon.json sample/c/*.c

# test binary

"$CLI_BUILD_DIR/rulebook" -h
