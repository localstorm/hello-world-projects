#! /bin/sh

java -Xmx256m -cp 'dist/*:lib/*' org.localstorm.tools.aop.weaver.Runner $@