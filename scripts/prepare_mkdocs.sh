#!/bin/bash

if ! uv run which mkdocs > /dev/null 2>&1; then
  uv pip install -r website/requirements.txt
fi
