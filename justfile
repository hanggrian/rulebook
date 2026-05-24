install CLEAN="false":
    go mod {{ if CLEAN == "true" { "download" } else { "tidy" } }}
    {{ if CLEAN == "false" { "just _update-gradlew" } else { "" } }}
    uv sync {{ if CLEAN == "true" { "--locked" } else { "" } }}
    pnpm install {{ if CLEAN == "true" { "--frozen-lockfile" } else { "" } }}
    {{ if CLEAN == "false" { "scripts/prepare_clion.sh" } else { "" } }}

[group: 'check']
lint-gradle:
    just _gradle-{{ os() }} checkstyleMain codenarcMain codenarcScript ktlintCheck

[group: 'check']
lint-python:
    uv run poe lint

[group: 'check']
lint-node:
    pnpm lint

[group: 'check']
[parallel]
lint: lint-gradle lint-python lint-node
    stylebook .

# skip lint-node with network calls
[group: 'check']
[parallel]
minimal-lint: lint-gradle lint-python
    stylebook .

[group: 'check']
test-gradle:
    just _gradle-{{ os() }} test

[group: 'check']
test-python:
    uv run poe test

[group: 'check']
test-node:
    pnpm -r test

[group: 'check']
[parallel]
test: test-gradle test-python test-node

[group: 'check']
cov-gradle:
    just _gradle-{{ os() }} koverXmlReport

[group: 'check']
cov-python:
    uv run poe cov

[group: 'check']
cov-node:
    pnpm -r cov

[group: 'check']
[parallel]
cov: cov-gradle cov-python cov-node

format:
    just --fmt
    gofmt -w .

[group: 'doc']
doc-gradle:
    just _gradle-{{ os() }} dokkaGenerateHtml

[group: 'doc']
doc-python:
    uv run poe doc

[group: 'doc']
doc-node:
    pnpm doc

[group: 'doc']
[parallel]
doc: doc-gradle doc-python doc-node

[group: 'website']
prepare-website:
    uv pip install -r website/requirements.txt

[group: 'website']
preview-website: prepare-website
    cd website/ && uv run mkdocs serve --livereload

[group: 'website']
publish-website: prepare-website doc
    rm -rf website/docs/api/
    mkdir -p website/docs/api/
    mv build/dokka/html/ website/docs/api/javadoc/
    mv build/pdoc/ website/docs/api/pydoc/
    mv build/typedoc/ website/docs/api/tsdoc/
    cd website/ && uv run mkdocs gh-deploy
    rm -rf website/docs/api/

_gradle-linux *args:
    ./gradlew {{ args }}

_gradle-macos *args:
    ./gradlew {{ args }}

_gradle-windows *args:
    gradlew.bat {{ args }}

_update-gradlew:
    just _gradle-{{ os() }} wrapper
