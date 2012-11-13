#!/bin/sh

# $1 : 		your port#
# $2 : 		# of repetitions
# $3 :		origin host
# $4 ~ $8: 	remote host ips

java -cp UWAgent.jar:. UWAgent.UWInject -p $1 localhost WhoAgent $2 $3 $4 $5 $6 $7 $8
