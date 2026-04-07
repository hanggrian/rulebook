#!/bin/bash

readonly API_DIR='website/site/api/'

pnpm doc

cd website && uv run mkdocs build && cd ..
mkdir -p "$API_DIR"
rm -rf "${API_DIR:?}"*
mv build/dokka/html/ "${API_DIR}javadoc/"
mv build/pdoc "${API_DIR}pydoc/"
mv build/typedoc "${API_DIR}tsdoc/"
