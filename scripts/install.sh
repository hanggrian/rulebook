#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew assemble

pip install -r requirements-dev.txt
pip install -r website/requirements.txt
pip install -e codecheck/
pip install -e rulebook-cppcheck/
pip install -e rulebook-pylint/

npm i
npm run build
