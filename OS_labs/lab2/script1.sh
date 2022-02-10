#!/bin/bash

ps U root -o pid,comm > temp1
wc -l < temp1 > task1 ; awk '{print $0}' temp1 >> task1
