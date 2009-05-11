#!/bin/sh

while [ true ]
do
    curl -d @localstorm.xml http://localhost:8080/tracking
    date
done

