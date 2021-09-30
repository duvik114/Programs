#!/bin/bash
STR=""
while IFS='$\n' read -r LINE
do
    if [[ "$LINE" == "q" ]]
    then
    echo $STR
    break
    else
    STR="$STR$LINE"
    fi
done