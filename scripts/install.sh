#!/bin/bash

./gradlew assemble

uv sync
uv pip install -r website/requirements.txt

pnpm i
pnpm -r build
