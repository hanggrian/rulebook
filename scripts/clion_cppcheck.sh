#!/bin/bash

uv venv
uv run cppcheck "$@"
