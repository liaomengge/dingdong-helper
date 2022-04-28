#!/bin/sh

. package.properties

# 服务器上的用户名
USER=$user
# 服务器上的密码
PASSWORD=$password
# 服务器实例
IP=$ip
# 上传到服务器指定的目录
DIR=$dir
# 上传的tar文件
TAR_FILE=dingdong-helper-application-8-1.0.0.tar.gz

#打包项目
mvn clean package -DskipTests

#切换到打包好的项目目录
cd ./target
echo "$(pwd)"

expect -c "
spawn scp $TAR_FILE $USER@$IP:$DIR

expect \"password:\"
send \"${PASSWORD}\r\"
expect eof
"