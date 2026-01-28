#!/bin/bash

readonly ACTIVATE_EXEC="$SOURCE_ROOT/../.venv/bin/activate"

if [ ! -f "$ACTIVATE_EXEC" ]; then
  python3 -m venv "$VENV_NAME"
fi
source "$ACTIVATE_EXEC"
