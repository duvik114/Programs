#!/bin/bash

ps ax -o cmd,pid | grep '^/sbin/' | awk '{print $NF}' > task2
