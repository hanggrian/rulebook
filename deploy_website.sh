#!/bin/bash

readonly API_DIR='website/site/api'

source activate_python.sh

./gradlew clean dokkaGenerateHtml

pdoc --html rulebook-pylint/rulebook_pylint --output-dir build/pdoc

cd website && mkdocs build && cd ..
rm -rf "$API_DIR"
mkdir "$API_DIR"
cp -r build/dokka/html "$API_DIR/dokka"
cp -r build/pdoc/rulebook_pylint "$API_DIR/pdoc"
