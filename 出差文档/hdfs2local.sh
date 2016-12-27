-bash-4.1$ cat hdfs2local.sh.bak 
#!/bin/bash
ANALY_DATE=$1
ANALY_HOUR=$2
mypath="$(cd "$(dirname "$0")"; pwd)"
cd $mypath
#SOURCE_SVR="hdfs://nameservice1:8020/datang"
SOURCE_SVR="hdfs://nameservice1:8020/ws/detail"
HIVE_TABLES="s1mme_orgn volte_orgn volte_rx volte_sv s1u_http_orgn"
TEMP_DIR=/cup/d4/datang/TEMP
echo "get data begin!------------------------------------------"
rm -rf $TEMP_DIR
for tableName in ${HIVE_TABLES}
do
rm -rf $TEMP_DIR/${tableName}/$ANALY_DATE/$ANALY_HOUR/*
mkdir -p ${TEMP_DIR}/${tableName}/$ANALY_DATE/$ANALY_HOUR
echo "get from ${SOURCE_SVR}/${tableName}/p1_day=${ANALY_DATE}/*_${ANALY_DATE}${ANALY_HOUR}33*.txt.ok1 to  ${TEMP_DIR}/${tableName}/$ANALY_DATE/$ANALY_HOUR
"
hdfs dfs -get ${SOURCE_SVR}/${tableName}/p1_day=${ANALY_DATE}/*_${ANALY_DATE}${ANALY_HOUR}33*.txt.ok1  ${TEMP_DIR}/${tableName}/$ANALY_DATE/$ANALY_HOUR
cd ${TEMP_DIR}/${tableName}/$ANALY_DATE/$ANALY_HOUR
gzip *.ok1
done
echo "get data end!------------------------------------------"
exit



/ws/detail/volte_sv/p1_day=20161218/sv_20161218223844_01880.txt.ok1
