#!/bin/bash
export JAVA_HOME=/opt/app/java
export HADOOP_HOME=/opt/app/hadoop
export HADOOP_CONF_DIR=/opt/app/hdconf
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin
mypath="$(cd "$(dirname "$0")"; pwd)"
cd $mypath
ANALY_DATE=$1
ANALY_HOUR=$2

HIVE_PATH="hdfs://dtcluster/datang"
HIVE_TABLES="TB_XDR_IFC_X2 LTE_MRO_SOURCE TB_XDR_IFC_UU"
LOCAL_PATH=/appfs/hadoopfile
CSV_REP_DIR=/appfs/csv_report_files
cd ${CSV_REP_DIR}/${ANALY_DATE}
gzip ${ANALY_DATE}${ANALY_HOUR}*.csv
x2=${LOCAL_PATH}/TB_XDR_IFC_X2/${ANALY_DATE}/${ANALY_HOUR}
mro=${LOCAL_PATH}/LTE_MRO_SOURCE/${ANALY_DATE}/${ANALY_HOUR}
uu=${LOCAL_PATH}/TB_XDR_IFC_UU/${ANALY_DATE}/${ANALY_HOUR}
rm -rf ${LOCAL_PATH}/TB_XDR_IFC_X2
rm -rf ${LOCAL_PATH}/TB_XDR_IFC_UU
rm -rf ${LOCAL_PATH}/LTE_MRO_SOURCE
mkdir -p $x2
mkdir -p $uu
mkdir -p $mro
mv ${ANALY_DATE}${ANALY_HOUR}*mrosource*.gz ${mro}
mv ${ANALY_DATE}${ANALY_HOUR}*uu*.gz ${uu}
mv ${ANALY_DATE}${ANALY_HOUR}*x2*.gz ${x2}

for tableName in ${HIVE_TABLES}
do
hdfs dfs -test -e ${HIVE_PATH}/${tableName}/${ANALY_DATE}/${ANALY_HOUR}
if [ $? -eq 0 ] ;then
   hdfs dfs -rm -r ${HIVE_PATH}/${tableName}/${ANALY_DATE}/${ANALY_HOUR}
fi
#echo "hdfs dfs -mkdir -p ${HIVE_PATH}/${tableName}/${ANALY_DATE}/${ANALY_HOUR}"
#hdfs dfs -mkdir -p ${HIVE_PATH}/${tableName}/${ANALY_DATE}/${ANALY_HOUR}
echo "put ${LOCAL_PATH}/${tableName}/${ANALY_DATE}/${ANALY_HOUR} to ${HIVE_PATH}/${tableName}/"
hdfs dfs -put ${LOCAL_PATH}/${tableName}/* ${HIVE_PATH}/${tableName}/
done
echo "Success!"

