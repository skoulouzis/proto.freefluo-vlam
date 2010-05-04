#!/bin/sh
###
# File   : main.sh
# Module : setIndexLocationRelative startup file
# Info: 
#   Startup script for the web service module
#   from http://elab.science.uva.nl:7080/axis1.4/services/SearcherWS?wsdl
###

echo BASE_DIR      = $BASE_DIR
echo JAVA_HOME     = /usr/local/sun-java/jdk1.4/

echo CLASSPATH     = $CLASSPATH

$JAVA_HOME/bin/java nl.wtcw.vle.webservice.DynamicClient http://elab.science.uva.nl:7080/axis1.4/services/SearcherWS?wsdl setIndexLocationRelative