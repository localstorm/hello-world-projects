#!/bin/sh

while [ true ]
do
    curl -d @prices-lo.xml http://localhost:8080/prices
    curl -d @prices-hi.xml http://localhost:8080/prices
    sleep 1
    date
done

