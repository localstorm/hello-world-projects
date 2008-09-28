#! /bin/sh

if [ -z $1 ]
then
    echo "Usage: <Config-file-path> <recipient> <message-text>"
    exit 1
fi

if [ -z $2 ]
then
    echo "Usage: <Config-file-path> <recipient> <message-text>"
    exit 1
fi

if [ -z $3 ]
then
    echo "Usage: <Config-file-path> <recipient> <message-text>"
    exit 1
fi

java -cp "lib/*:dist/*" org.localstorm.punjab.SimpleSender $1 $2 $3
