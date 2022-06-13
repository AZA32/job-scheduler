# 工程简介

#查询所有任务
GET http://localhost:8080/job/getJobs

#添加任务
POST http://localhost:8080/job/addJob

```json
{
    "name": "String",
    "cron": "String",
    "classPath": "String",
    "dataMap": "String",
    "status": 0,
    "describe": "String"
}
```

#启动任务
GET http://localhost:8080/job/start/{jobId}

#执行任务
GET http://localhost:8080/job/execute/{jobId}

#修改任务
POST http://localhost:8080/job/updateJob
```json
{
    "id": 0,
    "name": "String",
    "cron": "String",
    "classPath": "String",
    "dataMap": "String",
    "status": 0,
    "describe": "String"
}
```

#停止任务
GET http://localhost:8080/job/stop/{jobId}

#线程池监控
GET http://localhost:8080/actuator/thread-pool


#构建镜像（配置Mysql数据库）
gradle dockerBuildImage

#获取镜像
docker pull registry.cn-beijing.aliyuncs.com/namespace_docker/job-scheduler:0.0.1-SNAPSHOT

# 延伸阅读

