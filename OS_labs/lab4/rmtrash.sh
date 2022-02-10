#!/bin/bash 

if [ "$1" = "" ]
then echo "You should run lab4_task1.sh some_file_name"
    exit
fi

dir_name=$(pwd)
#echo ${dir_name}

if ! [ -d ~/.trash ]
then echo "Hidden directory trash does not exist"
    mkdir ~/.trash
fi

ln ${dir_name}/$1 "$HOME/.trash/$$_$1"
if [ $? -eq 0 ]
then
    echo "=============================Removed file and created link===========================================" >> ~/.trash.log
    echo "${dir_name}/$1*${HOME}/.trash/$$_$1" >> ~/.trash.log
    rm -f ${dir_name}/$1
fi
