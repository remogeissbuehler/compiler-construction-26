#!/bin/bash

set -e

BIN_DIR="bin/"

build() {
  echo "building spl"
  find src -name '*.java' | xargs javac -d "$BIN_DIR"

  go generate ./...

  mkdir -p build
  go build -o build/splprime cmd/splprime/main.go
}

build
