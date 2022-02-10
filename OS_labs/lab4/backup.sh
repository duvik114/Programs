#!/bin/bash 

cur_date=`date +%Y-%m-%d`
echo $cur_date
cur_date_sec=`date -d"$cur_date" +%s`
backup_dir_exist=0
count=0

ls $HOME/ | grep 'Backup-[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]' > ./$$_tmp
while read compare_dir
do
 date_compare_dir=`echo $compare_dir | sed 's/Backup-//g'`
 date_compare_dir_sec=`date -d"$date_compare_dir" +%s`
 if [ `expr $cur_date_sec - $date_compare_dir_sec` -lt 604800 ]
 then backup_dir_exist=1
      back_up_dir=$compare_dir
      echo "back_up_dir=" $back_up_dir
     break
 fi
done < ./$$_tmp
rm -f ./$$_tmp
if [ $backup_dir_exist -gt 0 ]
then echo "backup dir exist"
    for i in `ls $HOME/source`
    do
      if ! [ -f $HOME/$back_up_dir/$i ]
      then
          cp $HOME/source/$i $HOME/$back_up_dir
          echo "copied $HOME/source/$i to $HOME/$back_up_dir" >> $HOME/backup-report.$$
          count=`expr $count + 1`
      else
          cmp $HOME/source/$i $HOME/$back_up_dir/$i
          if [ $? -ne 0 ]
          then cp $HOME/$back_up_dir/$i $HOME/$back_up_dir/$i.$cur_date
               cp $HOME/source/$i $HOME/$back_up_dir/$i
               echo "copied $HOME/source/$i to $HOME/$back_up_dir/$i.$cur_date" >> $HOME/backup-report.$$
               count=`expr $count + 1`
          fi
      fi
    done
    if [ $count -gt 0 ]
    then
       echo "=========================================" >> $HOME/backup-report
       echo `date`" Some changes in "$back_up_dir >> $HOME/backup-report
       cat $HOME/backup-report.$$ >> $HOME/backup-report
       rm -f $HOME/backup-report.$$
    fi

else echo "backup dir doesn't exist"
    mkdir $HOME/Backup-$cur_date
    echo "=========================================" >> $HOME/backup-report
    echo `date`" Created directory $HOME/Backup-$cur_date" >> $HOME/backup-report
    for i in `ls $HOME/source`
    do
      cp $HOME/source/$i $HOME/Backup-$cur_date
      echo "Copied $HOME/source/$i to $HOME/Backup-$cur_date" >> $HOME/backup-report
    done
fi
