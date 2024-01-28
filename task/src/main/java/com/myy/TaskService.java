package com.myy;

import java.util.List;

public interface TaskService {

    // 查询任务日志
    List<TaskLog> queryTaskLogs(TaskLogParam param);

    // 执行任务
    TaskLog exec(TaskInfo info);


}
