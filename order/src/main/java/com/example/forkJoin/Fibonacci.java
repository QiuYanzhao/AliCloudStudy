package com.example.forkJoin;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @description: forkjoin使用示例，计算斐波那契数列 斐波那契数列：1,1,2,3,5,8,13,21,34,55,89,144...， 从第三项开始，每一项都等于前两项之和。
 * 注意： 如果i太大，导致递归层次太深，会导致栈溢出
 */
public class Fibonacci extends RecursiveTask<Integer> {

  final int n;

  Fibonacci(int n) {
    this.n = n;
  }

  protected Integer compute() {
    if (n <= 1) {
      return n;
    }
    Fibonacci f1 = new Fibonacci(n - 1);
    //提交任务
    f1.fork();
    Fibonacci f2 = new Fibonacci(n - 2);
    //合并结果
    return f2.compute() + f1.join();
  }

  private static volatile int count = 0;

  public static void main(String[] args) {
    //构建forkjoin线程池
    ForkJoinPool pool = new ForkJoinPool();
    Fibonacci task = new Fibonacci(10);
    //提交任务并一直阻塞直到任务 执行完成返回合并结果。
    int result = pool.invoke(task);
    System.out.println(result);
  }
}