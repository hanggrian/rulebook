#!/bin/bash

if ! command -v cppcheck &> /dev/null; then
  source_root=$(cd "$(dirname "$0")" && pwd)
  # shellcheck source=/dev/null
  source "$source_root/../.venv/bin/activate"
fi

cppcheck "$@"
