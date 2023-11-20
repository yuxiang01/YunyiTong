#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20230706.sql ./mysql/db
cp ../sql/ry_config_20220929.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../yyt-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy yyt-gateway "
cp ../yyt-gateway/target/yyt-gateway.jar ./ruoyi/gateway/jar

echo "begin copy yyt-auth "
cp ../yyt-auth/target/yyt-auth.jar ./ruoyi/auth/jar

echo "begin copy yyt-visual "
cp ../yyt-visual/yyt-monitor/target/yyt-visual-monitor.jar  ./ruoyi/visual/monitor/jar

echo "begin copy yyt-modules-system "
cp ../yyt-modules/yyt-system/target/yyt-modules-system.jar ./ruoyi/modules/system/jar

echo "begin copy yyt-modules-file "
cp ../yyt-modules/yyt-file/target/yyt-modules-file.jar ./ruoyi/modules/file/jar

echo "begin copy yyt-modules-job "
cp ../yyt-modules/yyt-job/target/yyt-modules-job.jar ./ruoyi/modules/job/jar

echo "begin copy yyt-modules-gen "
cp ../yyt-modules/yyt-gen/target/yyt-modules-gen.jar ./ruoyi/modules/gen/jar

