#/bin/bash

#This script will deal with the dns_data.

LOGIN_FTP_USER=$1    #ftp login user .
LOGIN_FTP_PASSWD=$2  #ftp login passwrod
REMOTE_DIR=/s1u_dns_orgn   #test dir
LOCAL_DIR=/s1u_dns_orgn  
FTP_HOST=$4        
TIME_NOW=$5 
TIME_DAY=`date +%Y%m%d`
length_p5=`echo ${TIME_NOW}|wc -L`

#check the parameter
if [ -z ${TIME_NOW} ]; then
TIME_NOW=`date +%Y%m%d%H`   
echo "Will get data of $TIME_NOW...."
elif [ ${length_p5} -ne 8 ];then  
echo "The parameter 5 is wrong,please check and run again..."
exit 1
else
echo "Will get data of $TIME_NOW...."
fi
data_dir=$LOCAL_DIR/$TIME_NOW  #remote dir

#make dir and change dir 
if [ -d ${data_dir} ]; then
cd ${data_dir}
else
echo "The dictionary is not exist,will create..."
mkdir -p ${data_dir}
cd ${data_dir}
fi

#login ftp
for (( i=0;i<=15;i++))
do
if [ $i -lt 10 ];then
j=`printf "%02d" "$i"`
else
j=$i
fi
ftp -n<<!
open ${FTP_HOST}
user ${LOGIN_FTP_USER} ${LOGIN_FTP_PASSWD}
cd ${REMOTE_DIR}/${TIME_DAY}
prompt
mget *${TIME_NOW}${j}*.ok1
close
bye
!
done
if [ $? -eq 0 ]; then
echo "Files transfer is completed...(0~15 minutes data)"
else
echo "File transfer failed"
exit 1
fi

#compress
cd $data_dir
gzip *
if [ $? -eq 0 ]; then
echo "Compress files completed..."
else
echo "Comgress files failed!!"
exit 1
fi

echo "Transmitting file to cluster......"
scp -r $data_dir/*.gz hadoop@host.of.cluster:/home/hadoop/data_dir/dns/$TIME_NOW
echo "End of transmission......"
ssh hadoop@host.of.cluster sh load_hive.sh 
echo "Load data to hive database......."

----------------------------------------------------------------
#/bin/bash
#table_dns.sh

LOCAL_DIR=/s1u_dns_orgn
TIME_NOW=`date +%Y%m%d%H`
TIME_MONTH=`date +%m`
TIME_DAY=`date +%d`
TIME_HOUR=`date +%H`
TARGET_DIR=$LOCAL_DIR/$TIME_NOW

cd $TARGET_DIR

hive <<EOF  
ALTER TABLE load_test.partition_test ADD IF NOT EXISTS PARTITION (month='$TIME_MONTH',day='$TIME_DAY',hour='$TIME_HOUR');
exit;
EOF

hdfs dfs -put ${TARGET_DIR}/* /user/hive/warehouse/load_test.db/partition_test/month=$TIME_MONTH/day=$TIME_DAY/hour=$TIME_HOUR
echo "load data completed..."


