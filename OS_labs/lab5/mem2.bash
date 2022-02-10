#!/bin/bash 

s=" 1 2 3 4 5 6 7 8 9 10"
echo `date` "Starting ..." > ./report2.log
count=0
while true
do
arr+=($s)
count=`expr $count + 1`
if [ $count -eq 100000 ]
then echo `date` "Count of records in array arr in $count iteration is "${#arr[*]} >> ./report2.log
    count=0
fi
done
