#!/bin/bash 

directory="/home/user/lab4"

if [ "$1" = "" ]
then echo "You should run lab4_task2.sh some_file_name"
    exit
fi

if ! [ -f ~/.trash.log ]
then echo "trash log file does not exist"
    exit
fi

file_link=`cat ~/.trash.log | grep $1`

if [ "$file_link" = "" ]
then echo "There is no record in $HOME/.trash.log about $1"
    exit
fi

for file_link in `cat ~/.trash.log | grep $1`
do
 file_name=`echo $file_link | sed 's/*/ /g' | awk '{print $1}'`
 file_directory_name=`echo $file_name | awk '{print $1}' | sed "s/$1//g"`
 link_name=`echo $file_link | sed 's/*/ /g' | awk '{print $2}'`

echo "file_link="$file_link
echo "file_name="$file_name
echo "file_directory_name="$file_directory_name
echo "link_name="$link_name

 if ! [ -f $link_name ]
 then echo "File $1's link does not exist in file $HOME/.trash
Press Enter to continue..."
 else echo "$1's link has founded. Would you like to repair $1?"
      echo  "Type yes or no"
 fi
   read answer
     if [ "$answer" = "yes" ]
     then
          if ! [ -d $file_directory_name ]
          then
              echo "Ther is no directory $file_directory_name and $1 will be repaired to home directory"
              if ! [ -f $HOME/$1 ]
              then
                  ln $link_name $HOME/$1
              else
                  echo "In home directory file with name $1 already exist. Define another file name"
                  read new_file_name
                  ln $link_name $HOME/$new_file_name
              fi

          else
             if ! [ -f $file_name ]
             then
                   ln $link_name $file_name
             else
                 echo "File with name $1 already exist. Define another file name"
                 read new_file_name
                 ln $link_name $file_directory_name/$new_file_name
             fi
          fi
          rm -f $link_name
      fi
done
