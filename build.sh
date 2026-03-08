#!/bin/bash

set -e

BIN_DIR="bin/"

build() {
  echo "building spl"
  find src -name '*.java' | xargs javac -d "$BIN_DIR"
}

build
