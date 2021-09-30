#!/bin/bash
fileName="/var/log/anaconda/X.log"
awk '{ if ($3=="(WW)") {$3="WARNING:" ; print $0} }' $fileName > full.log
awk '{ if ($3=="(II)") {$3="INFORMATION:" ; print $0} }' $fileName >> full.log
cat full.log