package com.liuxinwen.rocket.core;

public final class Constant {
    /**
     * 线程池配置参数
     */
    public static final int CORE_POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors() + 1;;
    public static final int MAX_POOL_SIZE = 20;
    public static final int QUEUE_CAPACITY = 60;
    public static final int KEEP_ALIVE_SECOND = 300;

    private Constant() {
    }
}
