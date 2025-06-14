#!/bin/bash
# 设置脚本执行过程中一旦出现错误就退出脚本
set -e

# 设置脚本的日志文件和错误文件
LOG_FILE="./.sync_git.log"
ERR_FILE="./.sync_git_error.log"

# 提示开始本次脚本的执行
echo "[START] $(date) 自动同步开始" | tee -a "$LOG_FILE"

# 清空错误日志
: > error.log # 这里的 ":" 相当于 "true"

# 子模块同步部分
git submodule foreach --recursive "
  echo \">>> [\$name] >>>\"
  echo \"[INFO] 正在处理子模块: \$name\" | tee -a \"../$LOG_FILE\"
  cd \"\$toplevel/\$path\" || exit 1

  git checkout main 2>/dev/null || git checkout master 2>/dev/null || true

  branch=\$(git rev-parse --abbrev-ref HEAD)
  echo \"[INFO] 当前分支: \$branch\" | tee -a \"../$LOG_FILE\"

  if git pull origin \"\$branch\"; then
    echo \"[INFO] 拉取成功，准备提交...\" | tee -a \"../$LOG_FILE\"
    git add --all
    git commit -m \"子模块自动数据备份\" 2>/dev/null || echo \"[INFO] 无需提交\" | tee -a \"../$LOG_FILE\"
    git push origin \"\$branch\" || echo \"[ERROR] 子模块 \$name push 失败\" >> \"../$ERR_FILE\"
  else
    echo \"[ERROR] 子模块 \$name pull 失败\" >> \"../$ERR_FILE\"
  fi
  echo \"<<< [\$name] <<<\"
"

# 父模块同步部分
echo "[INFO] 正在处理父仓库..." | tee -a "$LOG_FILE"
git add --all
git commit -m "父模块自动数据备份" | tee -a "$LOG_FILE"
git push

# 错误日志汇总
if [ -s "$ERR_FILE" ]; then
  echo -e "\n[WARNING] 同步完成, 但存在错误：" | tee -a "$LOG_FILE"
  tee -a "$LOG_FILE" < "$ERR_FILE"
  exit 1
else
  echo "[SUCCESS] 全部同步完成, 且没有错误" | tee -a "$LOG_FILE"
fi
