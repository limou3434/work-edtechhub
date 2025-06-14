#!/bin/bash
# 设置脚本执行过程中一旦出现错误就退出脚本
set -e

# 提示开始本次脚本的执行
echo "$(date) 自动同步开始"

# 子模块同步部分
git submodule foreach --recursive "
  cd \"\$toplevel/\$path\"
  echo \"正在处理子仓库: \$name\"
  git pull
  git add --all
  git commit -m \"子模块自动数据备份\"
  git push
"

# 父模块同步部分
echo "正在处理父仓库..."
git pull
git add --all
git commit -m "父模块自动数据备份"
git push

# 汇总
echo "[SUCCESS] 全部同步完成"
