#!/bin/sh
# Gradle wrapper script for Unix
exec "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" "$@" 2>/dev/null || \
  curl -sL "https://services.gradle.org/distributions/gradle-8.3-bin.zip" -o /tmp/gradle.zip && \
  unzip -q /tmp/gradle.zip -d /tmp/ && \
  /tmp/gradle-8.3/bin/gradle "$@"
