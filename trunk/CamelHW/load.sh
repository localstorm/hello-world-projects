#!/bin/sh

while [ true ]
do
    curl -d @tracking.xml http://localhost:8080/tracking
    date
done

