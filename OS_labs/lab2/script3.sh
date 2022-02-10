#!/bin/bash

ps -Ao ppid,pid,cmd | awk '{ if($1 != '$$' && $2 != '$$') print $2 " : " $3 }' | tail -1
