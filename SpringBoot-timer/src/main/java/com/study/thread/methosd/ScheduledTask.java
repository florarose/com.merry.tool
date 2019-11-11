package com.study.thread.methosd;

import java.util.concurrent.ScheduledFuture;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/6 15:06
 */
public class ScheduledTask {

    public volatile ScheduledFuture<?> future;
    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
