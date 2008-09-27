#! /bin/sh

if [ -z $1 ]
then
    echo "Usage: <Config-file-path>"
    exit 1
fi

java -cp "lib/*:dist/*" org.localstorm.punjab.Punjab $1