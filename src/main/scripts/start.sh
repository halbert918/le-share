#!/bin/bash
APP_NAME=le-share

BIN_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd $BIN_DIR

PIDFILE="$BIN_DIR/$(basename $APP_NAME).pid"
PID=0
if [[ -f $PIDFILE ]]; then
  PID=`cat $PIDFILE`
fi

CONF_DIR=./config
LIB_DIR=./lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1091 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

JAVA_OPTS=" -Djava.net.preferIPv4Stack=true -Dfile.encoding=utf-8 -Dspring.profiles.active=prod"
JAVA_MEM_OPTS=" -server -Xms2g -Xmx2g -XX:SurvivorRatio=2 -XX:+UseParallelGC "

# 主应用包
MAIN_JAR="./le-share.jar"
MAIN_CLASS=com.le.share.LeShareApplication
START_CMD="java $JAVA_OPTS $JAVA_MEM_OPTS -classpath $CONF_DIR:$LIB_JARS$MAIN_JAR $MAIN_CLASS"

isRunning() {
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  echo "pid is $pid"
  #如果不存在返回1，存在返回0
  if [ -z "$pid" ]; then
   return 1
  else
    return 0
  fi
}

start() {
  echo "### start $APP_NAME `date '+%Y-%m-%d %H:%M:%S'` ###" >>  ./start.log 2>&1 &
  isRunning
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup START_CMD >>  ./start.log 2>&1 &
    #nohup java $JAVA_OPTS $JAVA_MEM_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS >> /dev/null 2>&1 &
  fi
  PID=$!
  echo $! > $PIDFILE
  echo "new pid le-share:$!"
}

stop() {
  echo "### stop $APP_NAME `date '+%Y-%m-%d %H:%M:%S'` ###" >>  /dev/null   2>&1 &
  isRunning
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

restart() {
  stop
  start
}

restart

