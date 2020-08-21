package com.wwh.test.memory;

public class VolatileTest2 {

    private xx x;

    public VolatileTest2(xx x){
        this.x = x;
    }

    void shutdown() {
        x.setShutDown(true);
        System.out.println("shutdown!");
    }

    void doWork() {
        System.out.println("start work...");
        while (!x.isShutDown()) {
            // try {
            // Thread.sleep(100);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
        }
        System.out.println("doWork end");
    }

    public static void main(String[] args) throws Exception {
        xx x = new xx();
        x.setShutDown(false);

        VolatileTest2 vt = new VolatileTest2(x);

        new Thread(vt::doWork).start();

        // 这里需要休眠一下，否则无法保证是哪一个线程先执行
        Thread.sleep(100);

        new Thread(vt::shutdown).start();
    }
}

class xx {

    // 加上volatile就能结束doWork 线程
    private Boolean isShutDown;

    public boolean isShutDown() {
        return isShutDown;
    }

    public void setShutDown(boolean isShutDown) {
        this.isShutDown = isShutDown;
    }

}
