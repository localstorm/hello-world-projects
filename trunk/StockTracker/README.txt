BUILD COMMAND:

ant (Needs Apache Ant 1.7.0 in your classpath)

RUN COMMAND:

./run.sh (Needs java >= v1.6 in your classpath)
or 
./run.sh config/config.xml (Needs java >= v1.6 in your classpath)

TESTING:

'test' directory contains some test data and test scripts. 

cURL (http://curl.haxx.se/) utility must be already installed  (scripts use this utility)

<username>.sh      -- starts tracking stocks for <username> account
<username>-out.sh  -- stops tracking stocks for <username> account
prices-sampler.sh  -- emulates stock events

1) Run StockTracker application
2) Change your working directory to test
3) Run prices-sampler.sh script in a background
4) Check output/notifications.txt file (Should be empty)
3) Run localstorm.sh script
4) Wait for a few seconds and check output/notifications.txt file. (Should contain "Dear localstorm,..." )
5) Run mendosa.sh script
6) Wait for a few seconds and check output/notifications.txt file. (Should contain both "Dear localstorm,..." and "Dear mendosa,..." )
7) Run localstorm-out.sh script
8) Wait for a few seconds and check output/notifications.txt file. (Should contain only "Dear mendosa,..." at the end of file)

You can edit XML data files to provide proper start/end times

-----------------------------------------------------------------
Third-party libs:

ASM                       v3.1     // Jersey dependency
bsh-core                  v2.0b4   // BeanShell interpreter
camel-core                v1.6.0   // Apache Camel Platform
commons-beanutils         v1.6     // JavaBeans tools
commons-collections       v3.2.1   // Quartz dependency
commons-logging           v1.1.1   // Logging adapter
grizzly-servlet-webserver v1.8.3   // HTTP Engine
jersey-core               v1.0.1   // Jersey REST engine
jersey-server             v1.0.1   // Jersey server
jsr311-api                v1.0     // RESTful WS API (JSR 311)
Quartz                    v1.6.5   // Opensymphony Quartz Scheduler