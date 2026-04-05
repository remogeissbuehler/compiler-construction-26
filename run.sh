#!/bin/bash

source build.sh

run() {
  echo "[ JAVA ]"
  java -cp "$BIN_DIR" splprime.SplPrime "$@"

  echo
  echo "[ GoLang ]"
  ./gobin/splprime "$@"
}

run "$@"
