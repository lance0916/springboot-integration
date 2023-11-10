package com.example;

/**
 * @author WuQinglong
 * @since 2023/11/5 8:25 AM
 */
public class DemoCheckStyle {



    /**
     * @deprecated 推荐使用 xxx 替代
     */
    @Deprecated
    public void f1() {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
