#!/bin/bash
grep -E -r -oh -a '[[:alnum:]]+@[[:alnum:]]+[.][[:alnum:]]+' /etc | sort | uniq \ awk '{ ORS=", " } ; { print $0 }' > emails.lst
