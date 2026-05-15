prepare:
    mkdir -p rulebook-cli/build/
    cd rulebook-cli/build/ && cmake .. -G Ninja

build:
    cd rulebook-cli/build/ && cmake --build .

install-all clean="false":
    {{ if clean == "false" { "just _update-gradlew" } else { "" } }}
    uv sync {{ if clean == "true" { "--locked" } else { "" } }}
    pnpm {{ if clean == "true" { "ci" } else { "i" } }}
    {{ if clean == "false" { "scripts/prepare_clion.sh" } else { "" } }}

lint-all:
    just --fmt --check
    just _gradle-{{ os() }} checkstyleMain codenarcMain codenarcScript ktlintCheck
    uv run poe lint
    pnpm lint

test-all:
    just _gradle-{{ os() }} test
    uv run poe test
    pnpm -r test

coverage-all:
    just _gradle-{{ os() }} koverXmlReport
    uv run poe coverage
    pnpm -r coverage

documentation-all:
    just _gradle-{{ os() }} dokkaGenerateHtml
    uv run poe documentation
    pnpm documentation

prepare-website:
    uv pip install -r website/requirements.txt

publish-website: prepare-website documentation-all
    rm -rf website/docs/api/
    mkdir -p website/docs/api/
    mv build/dokka/html/ website/docs/api/javadoc/
    mv build/pdoc/ website/docs/api/pydoc/
    mv build/typedoc/ website/docs/api/tsdoc/
    cd website/ && uv run mkdocs gh-deploy
    rm -rf website/docs/api/

preview-website: prepare-website
    cd website/ && uv run mkdocs serve --livereload

_update-gradlew:
    just _gradle-{{ os() }} wrapper

_gradle-linux *args:
    ./gradlew {{ args }}

_gradle-macos *args:
    ./gradlew {{ args }}

_gradle-windows *args:
    gradlew.bat {{ args }}
