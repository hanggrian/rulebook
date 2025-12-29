#!/bin/bash

source activate_python.sh

./gradlew check test

pytest
