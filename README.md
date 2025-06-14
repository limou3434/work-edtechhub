# work-edtechhub

## 1.部署步骤

- 提交域名拓展请求
- 获取免费证书
- 检查 docker-compose.yaml 是否编写正确
- 启动所有需要的基本组件
  - 导入持久数据 work-mysql[可选]
  - 导入热点数据 work-redis[可选]
  - 导入智能模型 work-ollama[可选]
- 添加 .env 环境变量
- 添加 work-caddy 中的 Caddyfile 文件
- 添加对应项目的证书和密钥
- 无需改动，编译后端
- 改动请求，编译前端

## 2.其他事宜

待补充...
