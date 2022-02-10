#!/bin/bash
time=$(date '+%d.%m.%Y-%H:%M:%S')
mkdir ~/test && echo 'catalog test was created successfully' >> ~/report && touch ~/test/$time
ping -c 1 "www.net_nikogo.ru" || echo $time 'error cannot find given host' >> ~/report
