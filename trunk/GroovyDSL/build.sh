#! /bin/sh

rm -rf build/*
rm -rf apps/*

groovyc -d build `find src -name *.groovy`
groovyc --classpath build -d build `find models -name *.groovy`