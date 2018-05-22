#!/bin/bash
################################################################
# Name			: start-hazelcast.sh
# Type			: Script
# Purpose		: To spawn hazelcast cluster. This script must run on all the nodes of hazelcast cluster
# Mandatory Params	: Java Xmx, Java MaxHeapFreeRatio and Java MinHeapFreeRatio. These conf may vary use case to use case.


# Modification logs Log
# Date			By		Description
# ---------- 	----------------- 	---------------
# 2018-05-07	Sameena Parveen		Inception						
################################################################

#THIS_DIR will have the directory where this script is residing, not from where it is called or not PWD/current working directory.
THIS_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
JAR_DIR="${THIS_DIR}/../hazelcast-cluster-startup-1.0.0.jar"
CONF_BASE_DIR="${THIS_DIR}/../"



usage() {
    echo "Usage: <Script-Path>/start-cache.sh <Java Xmx> <Java MaxHeapFreeRatio> <Java MinHeapFreeRatio> &"
    echo "Example: <Script-Path>/start-cache.sh 25600 60 15 &"   
exit
}

if [[ $1 = "--help" || $1 = "-h" ]]
 then
  usage
elif [ $# -lt 3 ]
  then
   echo "Invalid arguments supplied!!!"
   usage
else
JAVA_XMX="${1}"
JAVA_MAXHEAPFREEFRATIO="${2}"
JAVA_MINHEAPFREEFRATIO="${3}"

echo "Going to use JAVA_XMX=${1}, JAVA_MAXHEAPFREEFRATIO=${2},JAVA_MINHEAPFREEFRATIO=${3}"
fi

java -Xmx"$JAVA_XMX"m -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=60 -XX:MaxHeapFreeRatio="$JAVA_MAXHEAPFREEFRATIO" -XX:MinHeapFreeRatio="$JAVA_MINHEAPFREEFRATIO" -cp "${JAR_DIR}:${THIS_DIR}/../lib/*" -Dhazelcast.config="$CONF_BASE_DIR"hazelcast.xml -Dhazelcast.logging.type=log4j -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dhazelcast.jmx=true -Dlog4j.configuration=file://"$CONF_BASE_DIR"hazelcast-server-log4j.properties com.impetus.hazelcast.StartHazelCastInstance
