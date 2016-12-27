#!/bin/bash

#today=`date +%Y%m%d`
#hour=`date +%d`
today=$1
hour=$2

mypath="$(cd "$(dirname "$0")"; pwd)"
cd ${mypath}
SOURCE_SVR="hdfs://dtcluster/datang"
# define etype
JAR_FILE="/dt/lib/ExceptionAnalysis.jar"
ETYPE_MAIN="cn.com.dtmobile.hadoop.biz.exception.job."

VOLTE_RX=${SOURCE_SVR}/volte_rx/${today}/${hour}/*
VOLTE_ORGN=${SOURCE_SVR}/volte_orgn/${today}/${hour}/*
S1MME_ORGN=${SOURCE_SVR}/s1mme_orgn/${today}/${hour}/*
TB_XDR_IFC_UU=${SOURCE_SVR}/tb_xdr_ifc_uu/${today}/${hour}/*
TB_XDR_IFC_X2=${SOURCE_SVR}/TB_XDR_IFC_X2/${today}/${hour}/*
ETYPE_OUT=${SOURCE_SVR}/ETYPE_OUT

HADOOP=`which hadoop`
HDFS=`which hdfs`
${HADOOP} fs -rm -R ${ETYPE_OUT}/volte_rx
${HADOOP} jar ${JAR_FILE} ${ETYPE_MAIN}GxRxProcessJob ${VOLTE_RX} ${ETYPE_OUT}/volte_rx
${HADOOP} fs -rm -R ${ETYPE_OUT}/volte_orgn
${HADOOP} jar ${JAR_FILE} ${ETYPE_MAIN}MwProcessJob ${VOLTE_RX} ${ETYPE_OUT}/volte_orgn
${HADOOP} fs -rm -R ${ETYPE_OUT}/s1mme_orgn
${HADOOP} fs -rm -R ${ETYPE_OUT}/tb_xdr_ifc_uu
${HADOOP} fs -rm -R ${ETYPE_OUT}/TB_XDR_IFC_X2
${HADOOP} jar ${JAR_FILE} ${ETYPE_MAIN}S1mmeProcessJob ${VOLTE_RX} ${ETYPE_OUT}/s1mme_orgn
${HADOOP} jar ${JAR_FILE} ${ETYPE_MAIN}UuProcessJob ${VOLTE_RX} ${ETYPE_OUT}/tb_xdr_ifc_uu
${HADOOP} jar ${JAR_FILE} ${ETYPE_MAIN}X2ProcessJob ${VOLTE_RX} ${ETYPE_OUT}/TB_XDR_IFC_X2

##################################################################################
# exception analysis.
EXCEPTION_MAIN="cn.com.dtmobile.hadoop.biz.exception.job.ExceptionCommonJob"

# Tables
LTE_MRO_SOURCE=${SOURCE_SVR}/LTE_MRO_SOURCE/${today}/${hour}/*
SV_TABLE=${SOURCE_SVR}/volte_sv/${today}/${hour}/*
EXCEPTION_MAP="/datang/exception_map/EXCEPTIONMAP.tsv"
cellMR=hdfs://dtcluster/user/hive/warehouse/dcl.db/cell_mr/dt=${today}/h=${hour}/000000_0
ltecell="/datang/ltcell/ltcell.csv"
T_PROCESS="/datang/t_process/t_process.csv"
EXCEPTION_OUT=hdfs://dtcluster/user/hive/warehouse/dcl.db/exception_analysis/dt=${today}/h=${hour}

# If the table has partitions.
${HADOOP} fs -rm -R ${EXCEPTION_OUT}
${HADOOP} jar ${jar_file} ${package_name} \
${ETYPE_OUT}/tb_xdr_ifc_uu/* \
${LTE_MRO_SOURCE}/* \
${ETYPE_OUT}/volte_orgn/* \
${ETYPE_OUT}/s1mme_orgn/* \
${SV_TABLE} \
${ETYPE_OUT}/TB_XDR_IFC_X2/* \
${ETYPE_OUT}/volte_rx/* \
${EXCEPTION_OUT}
${EXCEPTION_MAP} \
${cellMR} \
${ltecell} \
${T_PROCESS} 

if [ $? -eq 0 ]; then
echo "Success."
else
echo "job failed."
fi
# If the table has no partition.
#hadoop jar ${jar_file} ${package_name} \
#${uu} \
#${lte_mro_source} \
#${mw} \
#${s1} \
#${sv} \
#${x2} \
#${gx} \
#${exception_out} \
#${excep} \
#${cellMR} \
#${ltecell} \
#${t_process}



#####################################################################################
# Process the ue, to fillback TA and AOA.
#hadoop jar ${jar_file} cn.com.dtmobile.hadoop.biz.ueFillBack.job.UeMrSortJob $UE_TABLE $UE_OUTPUT

