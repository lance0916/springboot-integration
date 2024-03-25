package com.snailwu.example.delay;

/**
 * @author WuQinglong
 * @date 2023/12/7 4:32 PM
 */
public class DelayTask {

    private final String name;

    /**
     * 任务创建时间
     */
    private final long createTime = System.currentTimeMillis();

    public DelayTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "DelayTask{" +
            "name='" + name + '\'' +
            ", createTime=" + createTime +
            '}';
    }
}
