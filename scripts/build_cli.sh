#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

./gradlew build

build_dir="$SOURCE_ROOT/../rulebook-cli/build/"
mkdir -p "$build_dir"
cd "$build_dir" || exit
if command -v ninja &> /dev/null; then
  cmake .. -G Ninja
else
  cmake ..
fi
cmake --build .

./rulebook -h
