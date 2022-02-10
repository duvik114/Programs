#!/bin/bash 

if [ "$1" = "" ]
then echo "Run newmem.bash with max massive size"
    exit
fi
count=0
while [ $count -lt 30 ]
do
./newmem.bash $1 &
count=`expr $count + 1`
sleep 1
#echo $count
done
