#!/bin/bash

for f in inputs/*.spl; do 
    echo "$f";
    ./run.sh $f; 
done