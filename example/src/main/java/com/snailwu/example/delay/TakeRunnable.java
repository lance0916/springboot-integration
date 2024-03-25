package com.snailwu.example.delay;

/**
 * @author WuQinglong
 * @date 2023/12/7 5:07 PM
 */
public class TakeRunnable implements Runnable {

    private final TaskManager taskManager;

    public TakeRunnable(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DelayTask task = taskManager.take();
                taskManager.executeTask(task);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
