#!/bin/bash 

while true
do
echo `date` `/usr/bin/top -n1 -b | grep mem.bash | awk '{print $0}'` | head -1 >> /home/user/lab5/cpu_load2.log
echo `date` `/usr/bin/top -n1 -b | grep mem2.bash | awk '{print $0}'` | head -1 >> /home/user/lab5/cpu_load2.log
echo `date` `/usr/bin/top -n1 | grep 'MiB Mem' | awk '{print "Mib Mem: "$4" total "$6" free "$8" used "$10" buff/cache"}'`>> /home/user/lab5/cpu_load2.log
echo `date` `/usr/bin/top -n1 | grep 'MiB Swap' | awk '{print "Mib Swap: "$3" total "$5" free "$7" used "$9" avail mem"}'`>> /home/user/lab5/cpu_load2.log
echo `date` `/usr/bin/top -n1 -b | awk 'NR >= 8 && NR <= 12 {print $1}'` >> /home/user/lab5/cpu_load2.log
echo "" >> /home/user/lab5/cpu_load2.log
sleep 60
done
