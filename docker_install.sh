#!/bin/bash
# 容器安装脚本
#
# @author <a href="https://github.com/xiaogithuboo">limou3434</a>
# 更新包管理
sudo apt-get update && sudo apt-get install -y \
apt-transport-https \
ca-certificates \
curl \
gnupg \
lsb-release

# 添加 Docker 的 GPG 密钥
curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
# 备用 curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# 添加 Docker 软件源
echo \
"deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://mirrors.aliyun.com/docker-ce/linux/ubuntu \
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
# 备用源:
# echo \
# " deb [arch = amd64 signed-by =/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
# $(lsb_release -cs) stable " | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null


# 更新包管理器并且开始安装 Docker
sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y

# 验证 Docker 安装版本
sudo docker --version

# 拉取需要的镜像文件
sudo docker pull openjdk:17-jdk-slim
sudo docker pull node:22.11.0

