#!/bin/bash

if [ ! -d bin ]
then
    if ! mkdir bin
    then
	cat<<EOF>&2
$0 error creating directory 'bin'.
EOF
	exit 1
    fi
fi

if javac -g -d bin iiotiff.java
then
    if cd bin
    then
	if jar cvfe ../iiotiff.jar iiotiff * 
	then
	    cd ..
	    ls -l iiotiff.jar
	else
	    cat<<EOF>&2
$0 error creating jar file.
EOF
	    exit 1
	fi
    else
	cat<<EOF>&2
$0 error entering bin directory to create jar file.
EOF
	exit 1
    fi
else
    cat<<EOF>&2
$0 error compiling.
EOF
    exit 1
fi

