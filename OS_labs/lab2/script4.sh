#!/bin/bash

:> task4

for pid in $(ps aux | awk 'print $2')
do
if [ -r /proc/$pid/status ] && [ -r /proc/$pid/sched ]
then
ppid = $(grep -s "^PPid" /proc/$pid/status | awk '{print $2}')
ser = $(grep -s "sum_exec_runtime" /proc/$pid/sched | awk '{print $3}')
nr = $(grep -s "nr_swithes" /proc/$pid/sched | awk '{print $3}')
art = $(echo "$ser / $nr" | bc -l)
if [ ! -z "$ppid" ] && [ ! -z "$ser" ] && [ ! -z "$nr" ]
then
echo $pid $ppid $art >> task4
fi
fi
done

sort -o -n -k 2 task4 task4
