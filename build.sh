#!/bin/bash

set -e

BIN_DIR="bin/"

build() {
  echo "building spl"
  if command -v javac >/dev/null; then
    find src -name '*.java' | xargs javac -d "$BIN_DIR"
  else
    echo "no javac, skipping"
  fi

  if command -v go >/dev/null; then
    go install golang.org/x/tools/cmd/stringer@latest
    go generate ./...

    mkdir -p build
    go build -o gobin/splprime cmd/splprime/main.go
  else
    echo "no go, skipping"
  fi
}

build
