#!/bin/bash

./gradlew check test

source activate_python.sh
pytest

npm run test
