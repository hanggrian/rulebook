install CLEAN="false":
    make install CLEAN={{ CLEAN }}
    {{ if CLEAN == "false" { "just _update-gradlew" } else { "" } }}
    uv sync {{ if CLEAN == "true" { "--locked" } else { "" } }}
    pnpm install {{ if CLEAN == "true" { "--frozen-lockfile" } else { "" } }}
    {{ if CLEAN == "false" { "scripts/prepare_clion.sh" } else { "" } }}

[private]
lint1:
    just _gradle-{{ os() }} checkstyleMain codenarcMain codenarcScript ktlintCheck

[private]
lint2:
    uv run poe lint

[private]
lint3:
    pnpm lint

[group('check')]
[parallel]
lint: lint1 lint2 lint3
    stylebook .

# skip lint-node with network calls
[group('check')]
[parallel]
offline-lint: lint1 lint2

[private]
test1:
    just _gradle-{{ os() }} test

[private]
test2:
    uv run poe test

[private]
test3:
    pnpm -r test

[group('check')]
[parallel]
test: test1 test2 test3

[private]
cov1:
    just _gradle-{{ os() }} koverXmlReport

[private]
cov2:
    uv run poe cov

[private]
cov3:
    pnpm -r cov

[group('check')]
[parallel]
cov: cov1 cov2 cov3

format:
    just --fmt
    make format

[private]
doc1:
    just _gradle-{{ os() }} dokkaGenerateHtml

[private]
doc2:
    uv run poe doc

[private]
doc3:
    pnpm doc

[parallel]
doc: doc1 doc2 doc3

[group('website')]
prepare-website:
    uv pip install -r website/requirements.txt

[group('website')]
preview-website: prepare-website
    cd website/ && uv run mkdocs serve --livereload

[group('website')]
publish-website: prepare-website doc
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
