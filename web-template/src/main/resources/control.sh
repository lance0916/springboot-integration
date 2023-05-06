#!/bin/bash
# 停止、启动服务脚本

# ============================== 变量定义 ==============================
APP_NAME="web" # 应用名
SERVER_PORT=8080 # 端口号
BASE_PATH="/Users/wu/data/${APP_NAME}" # 应用根路径
LOG_PATH="${BASE_PATH}/logs" # 日志目录
mkdir -p ${LOG_PATH}
PID_FILE="${BASE_PATH}/${APP_NAME}.pid" # PID_FILE 文件
JAR_FILE_NAME="${APP_NAME}.jar" # jar 文件名
SH_LOG_FILE="${BASE_PATH}/control.log"
# ============================== 变量定义 ==============================

CMD=$0
CMD_ACTION=$1 # start or stop
ACTIVE_PROFILE=$2 # 使用的环境变量 dev test prod

# 启动服务方法
start() {
  echo "执行命令: ${CMD} ${CMD_ACTION} ${ACTIVE_PROFILE}" >> ${SH_LOG_FILE}
  if [ -z "${ACTIVE_PROFILE}" ]; then
    echo "需要指定要启动的环境" >> ${SH_LOG_FILE}
    exit 1
  fi

  # JVM 参数配置
  JVM_PARAMS="-XX:+PrintCommandLineFlags -XX:+UseG1GC"
  if [ "${ACTIVE_PROFILE}" == "prod" ]; then
    JVM_PARAMS="${JVM_PARAMS} -Xms2g -Xmx2g -XX:MetaspaceSize=100M"
    JVM_PARAMS="${JVM_PARAMS} -XX:MaxGCPauseMillis=500 -XX:GCPauseIntervalMillis=8000"
    JVM_PARAMS="${JVM_PARAMS} -XX:ParallelGCThreads=8 -XX:ConcGCThreads=2 -XX:G1ReservePercent=30"
  else
    JVM_PARAMS="${JVM_PARAMS} -Xms1g -Xmx1g"
    exit 1
  fi

  # 开启GC日志 jdk8
  JVM_PARAMS="${JVM_PARAMS} -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:${LOG_PATH}/gc.log"
  JVM_PARAMS="${JVM_PARAMS} -XX:+UseGCLogFileRotation -XX:GCLogFileSize=1024M -XX:NumberOfGCLogFiles=10"
  # 开启GC日志 jdk11
  #JVM_PARAMS="${JVM_PARAMS} -Xlog:gc,heap,safepoint:file=${LOG_PATH}/gc.log:time,uptime:filecount=10,filesize=10M"

  echo "服务端口: ${SERVER_PORT}" >> ${SH_LOG_FILE}
  echo "日志目录: ${LOG_PATH}" >> ${SH_LOG_FILE}
  echo "JVM参数: ${JVM_PARAMS}" >> ${SH_LOG_FILE}

  # 启动应用
  nohup java ${JVM_PARAMS} \
      -Dspring.profiles.active=${ACTIVE_PROFILE} \
      -Dserver.port=${SERVER_PORT} \
      -DLOG_DIR=${LOG_PATH} \
      -Dfile.encoding=UTF-8 \
      -jar ${BASE_PATH}/${JAR_FILE_NAME} \
      2>> /dev/null 1>> /dev/null &

  # 写入 PID 文件
  echo $! > ${PID_FILE}
}

stop() {
  # 先检查有没有进程
  PID=$(ps -ef | grep ${JAR_FILE_NAME} | grep java | grep -v 'grep' | awk '{print $2}')
  [ ! "${PID}" ] && PID=$(netstat -ntlp | grep :${SERVER_PORT} | awk '{print $NF}' | awk -F'/' '{print $1}')

  # 没有进行直接返回
  if [ ! "${PID}" ]; then
    echo "${JAR_FILE_NAME} 对应的进程不存在！" >> ${SH_LOG_FILE}
    exit 0
  fi

  # 将机器下线
  echo "将服务置为不可用状态，开始时间: $(date)" >> ${SH_LOG_FILE}
  curl --request PUT "http://127.0.0.1:${SERVER_PORT}/health/status" \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "secureKey": "09704be6-11a4-4b24-b421-aa2e1b8855b7",
        "status": 503
    }'
  echo "将服务置为不可用状态，结束时间: $(date)" >> ${SH_LOG_FILE}
  echo "等待机器被网关摘除......" >> ${SH_LOG_FILE}
  sleep 40
  echo "网关摘除机器成功！准备停止服务..." >> ${SH_LOG_FILE}

  # 结束进程
  killTimes=0
  while [ $killTimes -lt 3 ]; do
    PID=$(ps -ef | grep ${JAR_FILE_NAME} | grep java | grep -v 'grep' | awk '{print $2}')
    [ ! "${PID}" ] && PID=$(netstat -ntlp | grep :${SERVER_PORT} | awk '{print $NF}' | awk -F'/' '{print $1}')
    echo "获取到进程号: ${PID} 即将被杀死... (${killTimes})" >> ${SH_LOG_FILE}
    if [ "${PID}" ]; then
      kill -15 "${PID}"
      sleep 10
      ((killTimes++))
    else
      echo "杀死进程成功！(-15)" >> ${SH_LOG_FILE}
      break
    fi
  done

  # 如果正常结束进程不行，则强制结束
  PID=$(ps -ef | grep ${JAR_FILE_NAME} | grep java | grep -v 'grep' | awk '{print $2}')
  [ ! "${PID}" ] && PID=$(netstat -ntlp | grep :${SERVER_PORT} | awk '{print $NF}' | awk -F'/' '{print $1}')
  if [ "${PID}" ]; then
    echo "进程没有正常被终止，即将强制终止..." >> ${SH_LOG_FILE}
    kill -9 "${PID}"
    sleep 10

    # 再次探测进程是否存在
    PID=$(ps -ef | grep ${JAR_FILE_NAME} | grep java | grep -v 'grep' | awk '{print $2}')
    [ ! "${PID}" ] && PID=$(netstat -ntlp | grep :${SERVER_PORT} | awk '{print $NF}' | awk -F'/' '{print $1}')
    if [ "${PID}" ]; then
        echo "进程无法结束！！！请进行手动检查" >> ${SH_LOG_FILE}
        exit 1
    else
        echo "杀死进程成功！(-9)" >> ${SH_LOG_FILE}
    fi
  fi
}

# 根据命令进行启动或停止
case "${CMD_ACTION}" in
    start)
      start
    ;;
    stop)
      stop
    ;;
    restart)
      stop
      start "${ACTIVE_PROFILE}"
    ;;
    *)
      echo "未知的命令参数" >> ${SH_LOG_FILE}
    ;;
esac
