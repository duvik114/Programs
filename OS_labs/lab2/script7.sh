#!/bin/bash

:> task7

start=()
end=()
pids=()
cmdline=()

for pid in $(ps -Ao pid)
do
if [[ -r /proc/$pid/io ]]
then
start[$pid]=$(grep "rchar" /proc/$pid/io | awk'{print $2}')
pids[$pid] = $pid
cmdline[$pid]=$(cat /proc/$pid/cmdline | tr -d '\0')
fi
done

sleep 60

for pid in "${pids[@]}"
do
end[$pid]=$(grep "rchar" /proc/$pid/io | awk '{print $2}')
done

for pid in "${pids[@]}"
do
dif=$(echo "${end[$pid]} - ${start[$pid]}" | bc)
echo $pid $dif ${cmdline[$pid]} >> task7
done

sort -nr -k 2 task7 | head -n 3 | awk '{print $1":"$2":"$3}'
