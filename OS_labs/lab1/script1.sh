#!/bin/bash
if [[ $2 -gt $3 ]]
then
    if [[ $2 -gt $3 ]]
    then ech0 $2
    else echo $3
    fi
else
    if [[ $1 -gt $3 ]]
    then echo $1
    else echo $3
    fi
fi