#!/bin/bash
fileName="/etc/passwd"
cat $fileName | sort -n -t ':' -k3 | awk -F ":" '{ printf "%s : %s\n", $1, $3 }'
