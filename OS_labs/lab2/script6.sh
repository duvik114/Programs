#!/bin/bash

:> mem.txt

for pid in $(ps aux | awk '{print $2}')
do
if [[ -r /proc/$pid/status ]]
then
memory = $(grep -s "VmHWM:" /proc/$pid/status -hs | awk '{print $2}')
if [ ! -z $memory ]
then
echo $pid $memory >> mem.txt
fi
fi
done
sort mem.txt -rnk 2 > task6
head -1 task6
