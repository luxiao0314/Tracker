//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TcHandleThreadPool {
    private static final int POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 6;
    private static final int KEEP_ALIVE_TIME = 4;
    private final Executor mExecutor;
    private static final String THREAD_NAME = "paf-stat-thread-pool";

    public TcHandleThreadPool() {
        ThreadFactory factory = new PriorityThreadFactory("paf-stat-thread-pool", 10);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque();
        this.mExecutor = new ThreadPoolExecutor(4, 6, 4L, TimeUnit.SECONDS, workQueue, factory);
    }

    public void execute(Runnable command) {
        this.mExecutor.execute(command);
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }
}
