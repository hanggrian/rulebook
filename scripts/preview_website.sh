#!/bin/bash

uv venv
cd website && uv run mkdocs serve --livereload
