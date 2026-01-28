#!/bin/bash

SOURCE_ROOT=$(cd "$(dirname "$0")" && pwd) && readonly SOURCE_ROOT
readonly API_DIR='website/site/api'

source "$SOURCE_ROOT/.activate_python.sh"
cd "$SOURCE_ROOT/.." || exit

./gradlew clean dokkaGenerateHtml

pdoc --html rulebook-pylint/rulebook_pylint --output-dir build/pdoc

cd website && mkdocs build && cd ..
rm -rf "$API_DIR"
mkdir "$API_DIR"
cp -r build/dokka/html "$API_DIR/dokka"
cp -r build/pdoc/rulebook_pylint "$API_DIR/pdoc"
