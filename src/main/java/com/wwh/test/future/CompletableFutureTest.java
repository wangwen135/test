package com.wwh.test.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {

            @Override
            public Integer get() {
                System.out.println("开始第一个任务");
                // int i = 10 / 0;
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                int ret = new Random().nextInt(10);

                System.out.println("第一个任务返回：" + ret);

                return ret;
            }
        }).thenCompose(new Function<Integer, CompletionStage<Integer>>() {

            @Override
            public CompletionStage<Integer> apply(Integer param) {
                System.out.println("开始第二个任务，参数：" + param);
                return CompletableFuture.supplyAsync(new Supplier<Integer>() {

                    @Override
                    public Integer get() {
                        int t = param * 2;
                        System.out.println("第二个任务执行结果：" + t);
                        return t;
                    }
                });
            }
        });

        System.out.println("最终结果是：" + future.get());

    }
}
