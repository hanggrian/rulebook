#!/bin/bash

./gradlew assemble

uv sync

pnpm i
pnpm -r build
