#!/bin/bash

if ! command -v cppcheck &> /dev/null; then
  source_root=$(cd "$(dirname "$0")" && pwd)
  source "$source_root/../.venv/bin/activate"
fi

cppcheck "$@"
