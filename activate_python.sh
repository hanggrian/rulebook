#!/bin/bash

readonly VENV_NAME='.venv'
readonly ACTIVATE_EXEC="$VENV_NAME/bin/activate"

if ! [[ -f "$ACTIVATE_EXEC" ]]; then
  python3 -m venv "$VENV_NAME"
fi
source "$ACTIVATE_EXEC"
