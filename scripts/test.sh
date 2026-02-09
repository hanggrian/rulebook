#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew test

pytest rulebook-cppcheck/
pytest rulebook-pylint/

npm run test
