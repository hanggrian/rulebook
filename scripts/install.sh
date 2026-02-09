#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT
CLI_BUILD_DIR="rulebook-cli/build"

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew assemble

pip install -r requirements-dev.txt
pip install -r website/requirements.txt
pip install -e rulebook-cppcheck/
pip install -e rulebook-pylint/

npm i
npm run build

mkdir -p "$CLI_BUILD_DIR"
cd "$CLI_BUILD_DIR" || exit
cmake .. && cmake --build .
