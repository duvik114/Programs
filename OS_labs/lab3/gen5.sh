#!/bin/bash

while true; do
read LINE
 case $LINE in
  QUIT)
   echo $LINE > pipe
   exit
    ;;
  *)
    echo $LINE > pipe
    ;;
 esac
done
