#!/bin/sh

#Testing with 15Mb heap
java -Xmx48m  -cp 'lib/*:dist/*' -Dcom.sun.management.jmxremote org.localstorm.stocktracker.Main $@

# For NetBeans Profiler
#java -Xmx15m -agentpath:/home/localstorm/Program_Files/netbeans-6.5/profiler3/lib/deployed/jdk15/linux/libprofilerinterface.so=/home/localstorm/Program_Files/netbeans-6.5/profiler3/lib,5140  -cp 'lib/*:dist/*' -Dcom.sun.management.jmxremote org.localstorm.stocktracker.Main 
