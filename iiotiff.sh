#!/bin/bash


if [ -h "${0}" ]
then
    wd=$(dirname $(readlink $0 ) )
    jf=${wd}/$(basename $0 .sh).jar
else
    wd=$(dirname $0 )
    jf=${wd}/$(basename $0 .sh).jar
fi

if [ -f "${jf}" ]
then
    java -jar ${jf} $*
else
    cat<<EEOF>&2
$0 error jar file not found at '${jf}'.
EEOF
    exit 1
fi
