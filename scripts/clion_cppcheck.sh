#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT

if ! command -v cppcheck &> /dev/null; then
    # shellcheck source=/dev/null
    source "$SOURCE_ROOT/../.venv/bin/activate"
fi

cppcheck "$@"
