#!/bin/bash
fileName="/var/log/anaconda/syslog"
awk '$2=="INFO"' $fileName > info.log
