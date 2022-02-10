#!/bin/bash
if (( $(echo "$2 > $1" | bc) ))
then
    if (( $(echo "$2 > $3" | bc) ))
    then ech0 $2
    else echo $3
    fi
else
    if (( $(echo "$1 > $3" | bc) ))
    then echo $1
    else echo $3
    fi
fi