#!/bin/bash

./proc1.sh&
proc1_pid=$!
./proc2.sh&
./proc3.sh&
proc3_pid=$!

sleep 9

cpulimit -p $proc1_pid -l 10 &
kill $proc3_pid
