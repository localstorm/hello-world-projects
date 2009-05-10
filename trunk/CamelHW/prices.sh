#!/bin/sh

while [ true ]
do
    curl -d @prices.xml http://localhost:8080/prices
    date
done

