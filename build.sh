#!/bin/bash

set -e

build() {
  echo "building spl"
  javac -d bin src/**/*.java
}

build
