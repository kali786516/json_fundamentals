#!/bin/sh

curl -i -s -X POST http://localhost:8000/ -d @"$1" --header "Content-Type: application/json"

echo
echo
