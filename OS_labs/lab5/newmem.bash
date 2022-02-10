#!/bin/bash 

if [ "$1" = "" ]
then echo "Run newmem.bash with max size of massive"
    exit
fi
s=" 1 2 3 4 5 6 7 8 9 10"
#echo `date` "Starting ..." > ./newreport.log
count=0
while true
do
arr+=($s)
count=`expr $count + 1`
if [ ${#arr[*]} -gt $1 ]
then exit
fi
#if [ $count -eq 100000 ]
#then echo `date` "Count of records in array arr in $count iteration is "${#arr[*]} >> ./newreport.log
#    count=0
#fi
done
