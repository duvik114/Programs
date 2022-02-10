#!/bin/bash

echo $$ > .pid

MODE="+"
count=1

(tail -f pipe) |
while true
do read LINE
   if [ "$LINE" = "+" ]
   then
      MODE="+"
   elif [ "$LINE" = "*" ]
   then
      MODE="*"
   elif [[ $LINE ]] && [ $LINE -eq $LINE 2>/dev/null ]
   then
      if [ "$MODE" = "+" ]
      then
           let count=$count+$LINE
      else
           let count=$count*$LINE
      fi
      echo $count
   elif [ "$LINE" = "QUIT" ]
   then
      echo "Correct Stop"
      exit
   else
      echo "Incorrect stop"
      exit
   fi
done

