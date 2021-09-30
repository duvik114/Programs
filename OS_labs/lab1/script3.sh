#!/bin/bash
read -r VAR
case $VAR in
1) nano; exit 0;;
2) vi; exit 0;;
3) links; exit 0;;
4) exit 0;;
esac