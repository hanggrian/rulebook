#!/bin/bash

./gradlew test

uv run pytest rulebook-cppcheck/
uv run pytest rulebook-pylint/

pnpm -r test
