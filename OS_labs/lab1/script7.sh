#!/bin/bash
STR=$(grep -E -r -oh -a '[[:alnum:]]+@[[:alnum:]]+[.][[:alnum:]]+' /etc | sort | uniq | tr '\n' ',') > emails.lst
echo ${STR: : -2} > emails.lst
