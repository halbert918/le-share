#!/bin/bash
APP_NAME=exp-admin

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

JAVA_OPTS=" -Djava.net.preferIPv4Stack=true -Dfile.encoding=utf-8"
JAVA_MEM_OPTS=" -server -Xms4g -Xmx4g -XX:SurvivorRatio=2 -XX:+UseParallelGC "

MAIN_CLASS=com.tencent.rdm.exp.ExpAdminApplication
START_CMD="java $JAVA_OPTS $JAVA_MEM_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS"

isRunning() {
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "$pid" ]; then
   return 1
  else
    return 0
  fi
}

start() {
  echo "### start $APP_NAME `date '+%Y-%m-%d %H:%M:%S'` ###" >>  /dev/null   2>&1 &
  isRunning
  if [$? -eq "0"]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java $JAVA_OPTS $JAVA_MEM_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS >> /dev/null 2>&1 &
  fi
  PID=$!
  echo $! > $PIDFILE
  echo "new pid exp-admin:$!"
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

