#!/bin/bash

source build.sh

run() {
  java -cp "$BIN_DIR" splprime.SplPrime "$@"
  ./gobin/splprime "$@"
}

run "$@"
