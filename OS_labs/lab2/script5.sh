#!/bin/bash

:> task5

prev=0
sum=0
count=0

while read -r line
do
ppid = $(echo $line | awk '{print $2}')
art = $(echo $line | awk '{print $3}')
if [[ $ppid -eq $prev ]]
then
sum = $(echo "$sum + $art" | bc)
count = $(($count + 1))
else
i = $(bc -l <<< "$sum / $count")
echo "A_R_C_o_P=$prev is $i" >> task5
sum=$art
count=1
prev=$ppid
fi
echo $line >> task5
done < task4
i = $(bc -l <<< "$sum / $count")
echo "A_R_C_o_P=$prev is $i" >> task5
