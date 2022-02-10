#!/bin/bash

subDir=`ls -d $HOME/* | grep 'Backup-' | sort -n | tail -1`

for subFile in $subDir/*
do
    fileName=$subDir/`echo $subFile | awk -F/ '{ print $NF }' | grep -E '\.[0-9]+-[0-9]+-[0-9]+'`
    if [[ $subFile != $fileName ]]
    then
	cp -R $subFile $HOME/restore
    fi
done
