#!/bin/bash

set -e

bash gradlew --quiet --parallel --build-cache assemble
cp app/build/outputs/apk/release/app-release-unsigned.apk /apk/app.apk
