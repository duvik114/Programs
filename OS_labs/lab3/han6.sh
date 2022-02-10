#!/bin/bash

echo $$ > .pid

fun="WAIT"
fun1() 
{
fun="+"
}
fun2()
{
fun="*"
}

trap 'fun1' USR1
trap 'fun2' USR2

count=1
while true
do

    case $fun in
    "WAIT")
	echo $fun
        ;;
    "+")
        let count=$count+2
        echo $count
	;;
    "*")
        let count=$count*2
        echo $count
esac

sleep 1

done
