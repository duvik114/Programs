#!/bin/bash
$(echo man bash) | grep -o "[[:alpha:]]\{4,\}" | sort | uniq -c | sort -rn -k1 | head -n 3
#10#
